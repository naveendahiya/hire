package com.datasolution.hire.repository;

import com.datasolution.hire.domain.Mru;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Mru entity.
 */
@SuppressWarnings("unused")
public interface MruRepository extends JpaRepository<Mru,Long> {

}
