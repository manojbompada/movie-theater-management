package com.jpmc.movietheatermanagement.reservation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jpmc.movietheatermanagement.common.LocalDateProvider;
import com.jpmc.movietheatermanagement.customer.Customer;
import com.jpmc.movietheatermanagement.movie.Movie;
import com.jpmc.movietheatermanagement.showing.Showing;

@ExtendWith(MockitoExtension.class)
public class ReservationTest {
	
	@Spy
	private LocalDateProvider provider;
	
	private Reservation reservation;
	
	@Test
	public void testTotalFee() {
		Customer john = Customer.builder().name("John").build();
		Movie spiderMan = getMovie("Spider-Man: No Way Home", Duration.ofMinutes(90), 100, 1);
		Showing showing = getShowing(spiderMan, 1, LocalDateTime.of(provider.currentDate(), LocalTime.of(9, 0)));
		reservation = Reservation.builder().customer(john)
				.showing(showing)
				.audienceCount(10)
				.build();
		assertEquals(800, reservation.totalFee());
		
	}

	private Movie getMovie(String name, Duration duration, double ticketPrice, int specialCode) {
		return Movie.builder().title(name)
				.runningTime(duration)
				.ticketPrice(ticketPrice)
				.specialCode(specialCode).build();
	}
	
	private Showing getShowing(Movie movie, int sequenceOfTheDay, LocalDateTime showStartTime) {
		return Showing.builder()
				.movie(movie)
				.sequenceOfTheDay(sequenceOfTheDay)
				.showStartTime(showStartTime).build();
	}
}
