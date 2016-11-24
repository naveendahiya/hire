package com.datasolution.hire.repository;

import com.datasolution.hire.domain.CandidateSource;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CandidateSource entity.
 */
@SuppressWarnings("unused")
public interface CandidateSourceRepository extends JpaRepository<CandidateSource,Long> {

}
