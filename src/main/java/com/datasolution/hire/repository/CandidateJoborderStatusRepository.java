package com.datasolution.hire.repository;

import com.datasolution.hire.domain.CandidateJoborderStatus;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CandidateJoborderStatus entity.
 */
@SuppressWarnings("unused")
public interface CandidateJoborderStatusRepository extends JpaRepository<CandidateJoborderStatus,Long> {

}
