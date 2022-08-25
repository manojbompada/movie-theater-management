package com.jpmc.movietheatermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.jpmc.movietheatermanagement.theater.TheaterService;

import lombok.Data;

@SpringBootApplication
@Data
public class MovieTheaterManagementApplication {
	
	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(MovieTheaterManagementApplication.class, args);
		TheaterService theaterService = applicationContext.getBean(TheaterService.class);
		theaterService.printSchedule();
		theaterService.printScheduleJsonFormat();
	}

}
