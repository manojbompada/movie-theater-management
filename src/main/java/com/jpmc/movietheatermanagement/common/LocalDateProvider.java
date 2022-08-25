package com.jpmc.movietheatermanagement.common;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter
@Setter
@NoArgsConstructor
public class LocalDateProvider {
	public LocalDate currentDate() {
        return LocalDate.now();
	}
}
