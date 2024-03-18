package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.dtos.BookingDto;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.Booking;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.Pet;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.repositories.BookingRepository;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.repositories.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public long createBooking(BookingDto bookingDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));

        for (Long petId : bookingDto.getPetIds()) {
            Pet pet = petRepository.findById(petId)
                    .orElseThrow(() -> new NoSuchElementException("Pet not found with ID: " + petId));

            if (!pet.getOwner().getUsername().equals(currentUsername) && !isAdmin) {
                throw new AuthorizationServiceException("Not authorized to create booking for pet ID: " + petId);
            }
            boolean isAvailable = isDateAvailable(bookingDto.getStartDate(), bookingDto.getEndDate());
            if (!isAvailable) {
                throw new IllegalStateException("One or more dates in the requested booking period are not available.");
            }
        }

        Booking booking = transferToBooking(bookingDto);
        bookingRepository.save(booking);
        return booking.getId();
    }

    public boolean isDateAvailable(Date startDate, Date endDate) {
        List<LocalDate> datesToCheck = getDatesBetween(startDate, endDate);
        List<LocalDate> unavailableDates = getUnavailableDates().stream()
                .map(date -> date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .collect(Collectors.toList());

        return Collections.disjoint(datesToCheck, unavailableDates);
    }




    public List<BookingDto> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        List<BookingDto> bookingDtos = new ArrayList<>();

        for (Booking booking : bookings) {
            bookingDtos.add(transferToBookingDto(booking));
        }

        return bookingDtos;
    }


    public List<BookingDto> getAllBookingsForUser(String requestedUsername) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));

        if (!currentUsername.equals(requestedUsername) && !isAdmin) {
            throw new AuthorizationServiceException("Users can only access their own bookings or must be an admin.");
        }

        List<Booking> bookings = bookingRepository.findByPetsOwnerUsername(requestedUsername);
        List<BookingDto> bookingDtos = new ArrayList<>();

        for (Booking booking : bookings) {
            bookingDtos.add(transferToBookingDto(booking));
        }

        return bookingDtos;
    }

    private Booking transferToBooking(BookingDto bookingDto) {
        Booking booking;
        if (bookingDto.getId() != null) {
            booking = bookingRepository.findById(bookingDto.getId())
                    .orElseThrow(EntityNotFoundException::new);
        } else {
            booking = new Booking();
        }

        booking.setStartDate(bookingDto.getStartDate());
        booking.setEndDate(bookingDto.getEndDate());
        booking.setAdditionalInfo(bookingDto.getAdditionalInfo());
        booking.setIsConfirmed(bookingDto.getIsConfirmed());

        List<Pet> pets = bookingDto.getPetIds().stream()
                .map(petId -> petRepository.findById(petId).orElseThrow(EntityNotFoundException::new))
                .collect(Collectors.toList());
        booking.setPets(pets);

        return booking;
    }



    private BookingDto transferToBookingDto(Booking booking) {
        return new BookingDto(
                booking.getId(),
                booking.getStartDate(),
                booking.getEndDate(),
                booking.getAdditionalInfo(),
                booking.getPets().stream().map(Pet::getId).toList(),
                booking.getIsConfirmed()
        );
    }



//    public List<Date> getUnavailableDates() {
//        List<Booking> bookings = bookingRepository.findAll();
//        Map<Date, Integer> bookingCounts = new HashMap<>();
//
//        for (Booking booking : bookings) {
//            List<Date> dates = getDatesBetween(booking.getStartDate(), booking.getEndDate());
//            for (Date date : dates) {
//                bookingCounts.put(date, bookingCounts.getOrDefault(date, 0) + 1);
//            }
//        }
//
//        List<Date> unavailableDates = new ArrayList<>();
//        for (Map.Entry<Date, Integer> entry : bookingCounts.entrySet()) {
//            if (entry.getValue() >= 5) {
//                unavailableDates.add(entry.getKey());
//            }
//        }
//
//        return unavailableDates.stream().distinct().collect(Collectors.toList());
//    }

    public List<Date> getUnavailableDates() {
        List<Booking> bookings = bookingRepository.findAll();
        Map<LocalDate, Integer> bookingCounts = new HashMap<>();

        for (Booking booking : bookings) {
            // Generate all dates between startDate and endDate, inclusive
            List<LocalDate> dates = getDatesBetween(booking.getStartDate(), booking.getEndDate());
            for (LocalDate date : dates) {
                bookingCounts.put(date, bookingCounts.getOrDefault(date, 0) + 1);
            }
        }

        // Find dates where the booking count is 5 or more
        List<Date> unavailableDates = bookingCounts.entrySet().stream()
                .filter(entry -> entry.getValue() >= 5)
                .map(entry -> Date.from(entry.getKey().atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .collect(Collectors.toList());

        return unavailableDates;
    }

    private List<LocalDate> getDatesBetween(Date startDate, Date endDate) {
        LocalDate start = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate end = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        List<LocalDate> totalDates = new ArrayList<>();
        while (!start.isAfter(end)) {
            totalDates.add(start);
            start = start.plusDays(1);
        }
        return totalDates;
    }

//    private List<Date> getDatesBetween(Date startDate, Date endDate) {
//        List<Date> dates = new ArrayList<>();
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(startDate);
//
//        while (calendar.getTime().before(endDate) || calendar.getTime().equals(endDate)) {
//            dates.add(calendar.getTime());
//            calendar.add(Calendar.DATE, 1);
//        }
//
//        return dates;
//    }

    public List<BookingDto> getCurrentlyPresentPets() {
        List<Booking> bookings = bookingRepository.findAll();
        List<BookingDto> currentlyPresentPets = new ArrayList<>();
        Date today = new Date();

        for (Booking booking : bookings) {
            if (booking.getStartDate().before(today) && booking.getEndDate().after(today)) {
                BookingDto dto = transferToBookingDto(booking);
                currentlyPresentPets.add(dto);
            }
        }
        return currentlyPresentPets;
    }

    public void updateBooking(BookingDto dto) {
        Booking booking = transferToBooking(dto);
        bookingRepository.save(booking);
    }
}
