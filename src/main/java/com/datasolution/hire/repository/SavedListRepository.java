package com.datasolution.hire.repository;

import com.datasolution.hire.domain.SavedList;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SavedList entity.
 */
@SuppressWarnings("unused")
public interface SavedListRepository extends JpaRepository<SavedList,Long> {

}
