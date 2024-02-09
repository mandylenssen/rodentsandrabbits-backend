package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.dtos.BookingDto;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.Booking;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.Pet;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.repositories.BookingRepository;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.repositories.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
        Booking booking = transferToBooking(bookingDto);
        bookingRepository.save(booking);
        return booking.getId();
    }



public boolean isDateAvailable(Date startDate, Date endDate) {
    List<Date> datesToCheck = getDatesBetween(startDate, endDate);
    List<Date> unavailableDates = getUnavailableDates();

    for (Date date : datesToCheck) {
        if (unavailableDates.contains(date)) {
            return false;
        }
    }

    return true;
}

    public List<BookingDto> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        List<BookingDto> bookingDtos = new ArrayList<>();

        for (Booking booking : bookings) {
            bookingDtos.add(transferToBookingDto(booking));
        }

        return bookingDtos;
    }

    public List<BookingDto> getAllBookingsForUser(String username) {
        List<Booking> bookings = bookingRepository.findByPetsOwnerUsername(username);

        List<BookingDto> bookingDtos = new ArrayList<>();

        for (Booking booking : bookings) {
            bookingDtos.add(transferToBookingDto(booking));
        }

        return bookingDtos;
    }

        private Booking transferToBooking(BookingDto bookingDto) {
        List<Pet> pets = new ArrayList<>();
        for (Long petId : bookingDto.getPetIds()) {
            Pet pet = petRepository.findById(petId).orElseThrow(EntityNotFoundException::new);
            pets.add(pet);
        }

            return new Booking(
                bookingDto.getId(),
                bookingDto.getStartDate(),
                bookingDto.getEndDate(),
                bookingDto.getAdditionalInfo(),
                pets
        );
    }




    private BookingDto transferToBookingDto(Booking booking) {
        return new BookingDto(
                booking.getId(),
                booking.getStartDate(),
                booking.getEndDate(),
                booking.getAdditionalInfo(),
                booking.getPets().stream().map(Pet::getId).toList()
        );
    }



    public List<Date> getUnavailableDates() {
        List<Booking> bookings = bookingRepository.findAll();
        Map<Date, Integer> bookingCounts = new HashMap<>();

        // Populate bookingCounts with the number of bookings for each date
        for (Booking booking : bookings) {
            List<Date> dates = getDatesBetween(booking.getStartDate(), booking.getEndDate());
            for (Date date : dates) {
                bookingCounts.put(date, bookingCounts.getOrDefault(date, 0) + 1);
            }
        }

        List<Date> unavailableDates = new ArrayList<>();
        for (Map.Entry<Date, Integer> entry : bookingCounts.entrySet()) {
            if (entry.getValue() >= 2) {
                unavailableDates.add(entry.getKey());
            }
        }

        return unavailableDates.stream().distinct().collect(Collectors.toList());
    }

    private List<Date> getDatesBetween(Date startDate, Date endDate) {
        List<Date> dates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        while (calendar.getTime().before(endDate) || calendar.getTime().equals(endDate)) {
            dates.add(calendar.getTime());
            calendar.add(Calendar.DATE, 1);
        }

        return dates;
    }

}
