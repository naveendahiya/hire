package com.datasolution.hire.repository;

import com.datasolution.hire.domain.Joborder;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Joborder entity.
 */
@SuppressWarnings("unused")
public interface JoborderRepository extends JpaRepository<Joborder,Long> {

}
