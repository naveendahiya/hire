package com.datasolution.hire.repository;

import com.datasolution.hire.domain.SavedSearch;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SavedSearch entity.
 */
@SuppressWarnings("unused")
public interface SavedSearchRepository extends JpaRepository<SavedSearch,Long> {

}
