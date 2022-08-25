package com.jpmc.movietheatermanagement.showing;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowingRepository extends JpaRepository<Showing, UUID>  {

}
