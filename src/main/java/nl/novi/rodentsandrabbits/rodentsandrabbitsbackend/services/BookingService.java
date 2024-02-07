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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;

    @Autowired
    private PetRepository petRepository; // Ensure you have this injected into your service

    @Autowired
    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    //@Transactional
    public long createBooking(BookingDto bookingDto) {
        Booking booking = transferToBooking(bookingDto);
        bookingRepository.save(booking);
        return booking.getId();
    }



    public boolean isDateAvailable(Date startDate, Date endDate) {
        List<Booking> overlappingBookings = bookingRepository.findOverlappingBookings(startDate, endDate);
        return overlappingBookings.isEmpty();
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
}
