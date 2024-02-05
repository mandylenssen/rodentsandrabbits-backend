package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.services;

import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.dtos.BookingDto;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.exceptions.BookingDateUnavailableException;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.exceptions.DatabaseException;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.Booking;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.Pet;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public long createBooking(BookingDto bookingDto) {
        Booking booking = transferToBooking(bookingDto);
        bookingRepository.save(booking);
        return booking.getId();
    }

//
//    public long createBooking(BookingDto bookingDto) {
//        if (bookingDto.getEndDate().before(bookingDto.getStartDate())) {
//            throw new IllegalArgumentException("End date must be after start date.");
//        }
//
//        if (!isDateAvailable(bookingDto.getStartDate(), bookingDto.getEndDate())) {
//            throw new BookingDateUnavailableException("The requested date range is not available.");
//        }
//
//        Booking booking = transferToBooking(bookingDto);
//        try {
//            bookingRepository.save(booking);
//        } catch (DataAccessException e) {
//            throw new DatabaseException("Failed to save booking.", e);
//        }
//        return booking.getId();
//    }


    public boolean isDateAvailable(Date startDate, Date endDate) {
        // Aanname: een methode in je repository die overlappende boekingen zoekt
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
            Pet pet = new Pet();
            pet.setId(petId);
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
