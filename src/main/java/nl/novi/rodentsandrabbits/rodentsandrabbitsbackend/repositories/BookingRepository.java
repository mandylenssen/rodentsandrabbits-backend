package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.repositories;

import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.dtos.BookingDto;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByPetsOwnerUsername(String username);

    List<Booking> findByStartDateBeforeAndEndDateAfter(Date start, Date end);
}
