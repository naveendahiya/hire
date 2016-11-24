package com.datasolution.hire.repository;

import com.datasolution.hire.domain.CandidateJoborderStatusHistory;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CandidateJoborderStatusHistory entity.
 */
@SuppressWarnings("unused")
public interface CandidateJoborderStatusHistoryRepository extends JpaRepository<CandidateJoborderStatusHistory,Long> {

}
