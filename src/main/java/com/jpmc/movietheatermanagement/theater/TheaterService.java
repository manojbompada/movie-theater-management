package com.jpmc.movietheatermanagement.theater;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmc.movietheatermanagement.common.LocalDateProvider;
import com.jpmc.movietheatermanagement.customer.Customer;
import com.jpmc.movietheatermanagement.movie.Movie;
import com.jpmc.movietheatermanagement.reservation.Reservation;
import com.jpmc.movietheatermanagement.showing.Showing;

import lombok.Data;

@Service
@Data
public class TheaterService {
	
	private final LocalDateProvider provider;
	
	private final Theater theater;
	
	public TheaterService(LocalDateProvider provider) {
		this.provider = provider;
		Movie spiderMan = getMovie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 1);
		Movie turningRed = getMovie("Turning Red", Duration.ofMinutes(85), 11, 0);
		Movie theBatMan = getMovie("The Batman", Duration.ofMinutes(95), 9, 0);
		List<Showing> schedule = List.of(
				getShowing(turningRed, 1, LocalDateTime.of(provider.currentDate(), LocalTime.of(9, 0))),
				getShowing(spiderMan, 2, LocalDateTime.of(provider.currentDate(), LocalTime.of(11, 0))),
				getShowing(theBatMan, 3, LocalDateTime.of(provider.currentDate(), LocalTime.of(12, 50))),
				getShowing(turningRed, 4, LocalDateTime.of(provider.currentDate(), LocalTime.of(14, 30))),
				getShowing(spiderMan, 5, LocalDateTime.of(provider.currentDate(), LocalTime.of(16, 10))),
				getShowing(theBatMan, 6, LocalDateTime.of(provider.currentDate(), LocalTime.of(17, 50))),
				getShowing(turningRed, 7, LocalDateTime.of(provider.currentDate(), LocalTime.of(19, 30))),
				getShowing(spiderMan, 8, LocalDateTime.of(provider.currentDate(), LocalTime.of(21, 10))),
				getShowing(theBatMan, 9, LocalDateTime.of(provider.currentDate(), LocalTime.of(23, 0)))
				);
		theater = Theater.builder().schedule(schedule).build();
	}
	
	public Reservation reserve(Customer customer, int sequence, int howManyTickets) {
        Showing showing;
        try {
            showing = getTheater().getSchedule().get(sequence - 1);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new IllegalStateException("not able to find any showing for given sequence " + sequence);
        }
        return getReservation(customer, showing, howManyTickets);
    }

	private Reservation getReservation(Customer customer, Showing showing, int howManyTickets) {
		return Reservation.builder()
				.customer(customer)
				.showing(showing)
				.audienceCount(howManyTickets).build();
	}
	
	/**
	 * prints schedule in simple text format
	 */
	public void printSchedule() {
        System.out.println(getProvider().currentDate());
        System.out.println("===================================================");
        getTheater().getSchedule().forEach(s ->
                System.out.println(s.getSequenceOfTheDay() + ": " + s.getShowStartTime() + " " + s.getMovie().getTitle() + " " + humanReadableFormat(s.getMovie().getRunningTime()) + " $" + s.calculateTicketPrice())
        );
        System.out.println("===================================================");
    }
	
	/**
	 * prints schedule in json format
	 * @throws JsonProcessingException 
	 */
	public void printScheduleJsonFormat() {
		List<TheaterScheduleDto> theaterSchedule = new ArrayList<TheaterScheduleDto>();
		TheaterScheduleDto ts = null;
		for (Showing s : getTheater().getSchedule()) {
			ts = getTheaterScheduleDto(s);
			theaterSchedule.add(ts);
		}
		ObjectMapper mapper = new ObjectMapper();
		String json;
		try {
			json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(theaterSchedule);
			System.out.println(json);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

    private TheaterScheduleDto getTheaterScheduleDto(Showing s) {
		return TheaterScheduleDto.builder()
				.sequenceOfTheDay(s.getSequenceOfTheDay())
				.showStartTime(s.getShowStartTime())
				.movieTitle(s.getMovie().getTitle())
				.movieRunTime(humanReadableFormat(s.getMovie().getRunningTime()))
				.ticketPrice(s.calculateTicketPrice()).build();
	}

	public String humanReadableFormat(Duration duration) {
        long hour = duration.toHours();
        long remainingMin = duration.toMinutes() - TimeUnit.HOURS.toMinutes(duration.toHours());

        return String.format("(%s hour%s %s minute%s)", hour, handlePlural(hour), remainingMin, handlePlural(remainingMin));
    }
    
    // (s) postfix should be added to handle plural correctly
    private String handlePlural(long value) {
        if (value == 1) {
            return "";
        }
        else {
            return "s";
        }
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