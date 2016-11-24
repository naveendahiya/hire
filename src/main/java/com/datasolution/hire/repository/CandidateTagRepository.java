package com.datasolution.hire.repository;

import com.datasolution.hire.domain.CandidateTag;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CandidateTag entity.
 */
@SuppressWarnings("unused")
public interface CandidateTagRepository extends JpaRepository<CandidateTag,Long> {

}
