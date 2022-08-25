package com.jpmc.movietheatermanagement.movie;

import java.time.Duration;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "movies")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {

	public static int MOVIE_CODE_SPECIAL = 1;
	
	@Id
	@GeneratedValue
	@Column(name = "movie_id", columnDefinition = "uuid", updatable = false)
	private UUID id;
	
	@Column(name = "movie_title")
	private String title;
	
	private String description;
	
	@Column(name = "running_time")
    private Duration runningTime;
    
	@Column(name = "ticket_price")
    private double ticketPrice;
    
	@Column(name = "special_code")
    private int specialCode;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((runningTime == null) ? 0 : runningTime.hashCode());
		result = prime * result + specialCode;
		long temp;
		temp = Double.doubleToLongBits(ticketPrice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Movie other = (Movie) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (runningTime == null) {
			if (other.runningTime != null)
				return false;
		} else if (!runningTime.equals(other.runningTime))
			return false;
		if (specialCode != other.specialCode)
			return false;
		if (Double.doubleToLongBits(ticketPrice) != Double.doubleToLongBits(other.ticketPrice))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

}