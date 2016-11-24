package com.datasolution.hire.repository;

import com.datasolution.hire.domain.CandidateJoborder;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CandidateJoborder entity.
 */
@SuppressWarnings("unused")
public interface CandidateJoborderRepository extends JpaRepository<CandidateJoborder,Long> {

}
