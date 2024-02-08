package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.repositories;

import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.dtos.BookingDto;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("SELECT b FROM Booking b WHERE NOT (b.endDate < :startDate OR b.startDate > :endDate)")
    List<Booking> findOverlappingBookings(Date startDate, Date endDate);

    @Query("SELECT COUNT(b) FROM Booking b WHERE NOT (b.endDate < :startDate OR b.startDate > :endDate)")
    long countOverlappingBookings(Date startDate, Date endDate);

    List<Booking> findByPetsOwnerUsername(String username);
}
