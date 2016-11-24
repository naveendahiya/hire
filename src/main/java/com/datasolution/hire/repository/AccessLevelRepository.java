package com.datasolution.hire.repository;

import com.datasolution.hire.domain.AccessLevel;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the AccessLevel entity.
 */
@SuppressWarnings("unused")
public interface AccessLevelRepository extends JpaRepository<AccessLevel,Long> {

}
