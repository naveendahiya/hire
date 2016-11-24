package com.datasolution.hire.repository;

import com.datasolution.hire.domain.Queue;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Queue entity.
 */
@SuppressWarnings("unused")
public interface QueueRepository extends JpaRepository<Queue,Long> {

}
