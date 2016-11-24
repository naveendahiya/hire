package com.datasolution.hire.repository;

import com.datasolution.hire.domain.HttpLog;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the HttpLog entity.
 */
@SuppressWarnings("unused")
public interface HttpLogRepository extends JpaRepository<HttpLog,Long> {

}
