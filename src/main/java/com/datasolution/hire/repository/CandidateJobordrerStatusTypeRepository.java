package com.datasolution.hire.repository;

import com.datasolution.hire.domain.CandidateJobordrerStatusType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CandidateJobordrerStatusType entity.
 */
@SuppressWarnings("unused")
public interface CandidateJobordrerStatusTypeRepository extends JpaRepository<CandidateJobordrerStatusType,Long> {

}
