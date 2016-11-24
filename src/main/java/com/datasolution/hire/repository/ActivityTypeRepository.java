package com.datasolution.hire.repository;

import com.datasolution.hire.domain.ActivityType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ActivityType entity.
 */
@SuppressWarnings("unused")
public interface ActivityTypeRepository extends JpaRepository<ActivityType,Long> {

}
