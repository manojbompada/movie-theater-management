package com.jpmc.movietheatermanagement.theater;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jpmc.movietheatermanagement.reservation.Reservation;

@Repository
public interface TheaterRepository extends JpaRepository<Reservation, UUID> {

}
