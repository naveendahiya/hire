package com.datasolution.hire.repository;

import com.datasolution.hire.domain.History;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the History entity.
 */
@SuppressWarnings("unused")
public interface HistoryRepository extends JpaRepository<History,Long> {

}
