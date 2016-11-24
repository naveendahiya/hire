package com.datasolution.hire.repository;

import com.datasolution.hire.domain.ExtensionStatistics;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ExtensionStatistics entity.
 */
@SuppressWarnings("unused")
public interface ExtensionStatisticsRepository extends JpaRepository<ExtensionStatistics,Long> {

}
