package com.datasolution.hire.repository;

import com.datasolution.hire.domain.SavedListEntry;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SavedListEntry entity.
 */
@SuppressWarnings("unused")
public interface SavedListEntryRepository extends JpaRepository<SavedListEntry,Long> {

}
