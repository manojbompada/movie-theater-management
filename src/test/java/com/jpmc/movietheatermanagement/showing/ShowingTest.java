package com.jpmc.movietheatermanagement.showing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jpmc.movietheatermanagement.common.LocalDateProvider;
import com.jpmc.movietheatermanagement.movie.Movie;


@ExtendWith(MockitoExtension.class)
public class ShowingTest {
	
	@Spy
	private LocalDateProvider provider;
	
	private Showing showing;
	
	@Test
	public void testGetDiscount() {
		// specialMovie
		Movie spiderMan = getMovie("Spider-Man: No Way Home", Duration.ofMinutes(90), 100, 1);
		// special show with sequence as 2 and start time as 11
		// 25% is the highest discount here since its as 11 am
		showing = getShowing(spiderMan, 2, LocalDateTime.of(provider.currentDate(), LocalTime.of(11, 0)));
		assertEquals(75, showing.calculateTicketPrice());
		// special show with sequence as 1 and start time as 9
		// 20% special show discount should apply
		showing = getShowing(spiderMan, 1, LocalDateTime.of(provider.currentDate(), LocalTime.of(9, 0)));
		assertEquals(80, showing.calculateTicketPrice());
		// non-special movie
		Movie randomMovie = getMovie("Random", Duration.ofMinutes(90), 100, 0);
		// show with sequence as 1 and start time as 9
		// $3 discount for 1st show
		showing = getShowing(randomMovie, 1, LocalDateTime.of(provider.currentDate(), LocalTime.of(9, 0)));
		assertEquals(97, showing.calculateTicketPrice());
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
