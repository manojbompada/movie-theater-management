package com.jpmc.movietheatermanagement.theater;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.jpmc.movietheatermanagement.showing.Showing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "theaters")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Theater {
	@Id
	@GeneratedValue
	@Column(name = "theater_id", columnDefinition = "uuid", updatable = false)
	private UUID id;
	
	@OneToMany(mappedBy = "theater", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@Builder.Default
	private List<Showing> schedule = new ArrayList<Showing>();
}