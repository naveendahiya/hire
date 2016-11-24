package com.datasolution.hire.repository;

import com.datasolution.hire.domain.Imported;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Imported entity.
 */
@SuppressWarnings("unused")
public interface ImportedRepository extends JpaRepository<Imported,Long> {

}
