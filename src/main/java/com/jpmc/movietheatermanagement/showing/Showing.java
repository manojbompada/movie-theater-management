package com.jpmc.movietheatermanagement.showing;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.jpmc.movietheatermanagement.movie.Movie;
import com.jpmc.movietheatermanagement.theater.Theater;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "showings")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Showing {
	
	@Id
	@GeneratedValue
	@Column(name = "show_id", columnDefinition = "uuid", updatable = false)
	private UUID id;
	
	@OneToOne
	@JoinColumn(name = "movie_id", columnDefinition = "uuid")
	private Movie movie;
	
	@Column(name = "sequence_of_the_day")
    private int sequenceOfTheDay;
    
	@Column(name = "show_start_time")
    private LocalDateTime showStartTime;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "theater_id", columnDefinition = "uuid")
	private Theater theater;
    
    /**
     * method to calculate movie ticket price based on movie showing date/time
     * @return double - final ticket price
     */
	public double calculateTicketPrice() {
        return this.getMovie().getTicketPrice() - this.getDiscount();
    }
	
	/**
	 * method to calculate movie discount based on showing date/time
	 * @return double - discount  
	 */
	private double getDiscount() {
        double specialDiscount = 0;
        if (Movie.MOVIE_CODE_SPECIAL == this.getMovie().getSpecialCode()) {
            specialDiscount = this.getMovie().getTicketPrice() * 0.2;  // 20% discount for special movie
        }
        double sequenceDiscount = 0;
        if (sequenceOfTheDay == 1) {
            sequenceDiscount = 3; // $3 discount for 1st show
        } else if (sequenceOfTheDay == 2) {
            sequenceDiscount = 2; // $2 discount for 2nd show
        }
        double startTimeDiscount = 0;
        if (showStartTime.getHour() >= 11 && showStartTime.getHour() <= 16) {
        	// 25% discount of the movie starts between 11am and 4pm
        	startTimeDiscount = this.getMovie().getTicketPrice() * 0.25;
        }
        double dayDiscount = 0;
        if (showStartTime.getDayOfMonth() == 7) {
        	// $1 discount if the movie is on 7th of the month
        	dayDiscount = 1;
        }
        // return max discount from available discounts
        return Collections.max(Arrays.asList(specialDiscount, sequenceDiscount, startTimeDiscount, dayDiscount));
	}
	
}