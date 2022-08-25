package com.jpmc.movietheatermanagement.reservation;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.jpmc.movietheatermanagement.customer.Customer;
import com.jpmc.movietheatermanagement.showing.Showing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reservations")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {
	
	@Id
	@GeneratedValue
	@Column(name = "reservation_id", columnDefinition = "uuid", updatable = false)
	private UUID id;
	
	@OneToOne
	@JoinColumn(name = "customer_id", columnDefinition = "uuid")
	private Customer customer;
	
	@OneToOne
	@JoinColumn(name = "show_id", columnDefinition = "uuid")
    private Showing showing;
    
	@Column(name = "audience_count")
    private int audienceCount;
    
    public double totalFee() {
        return getShowing().calculateTicketPrice() * getAudienceCount();
    }

}