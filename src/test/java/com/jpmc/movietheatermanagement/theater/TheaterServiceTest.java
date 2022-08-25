package com.jpmc.movietheatermanagement.theater;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jpmc.movietheatermanagement.common.LocalDateProvider;
import com.jpmc.movietheatermanagement.customer.Customer;
import com.jpmc.movietheatermanagement.reservation.Reservation;

@ExtendWith(MockitoExtension.class)
public class TheaterServiceTest {
	
	private TheaterService theaterService;
	
	@Spy
	private LocalDateProvider provider;
	
	@BeforeEach
	public void setup() {
		theaterService = new TheaterService(provider);
		theaterService = Mockito.spy(theaterService);
	}
	
	@Test
    public void totalFeeForCustomer() {
        Customer john = Customer.builder().name("John").build();
        
        Reservation reservation = theaterService.reserve(john, 2, 4);
        assertEquals(37.5, reservation.totalFee());
        
        reservation = theaterService.reserve(john, 8, 5);
        assertEquals(50.0, reservation.totalFee());
    }
	
	@Test
    public void printMovieScheduleSimpleText() {
        theaterService.printSchedule();
    }
	
	@Test
    public void printMovieScheduleJsonFormat() {
        theaterService.printScheduleJsonFormat();
    }

}