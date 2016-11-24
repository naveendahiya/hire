package com.datasolution.hire.repository;

import com.datasolution.hire.domain.Activity;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Activity entity.
 */
@SuppressWarnings("unused")
public interface ActivityRepository extends JpaRepository<Activity,Long> {

}
