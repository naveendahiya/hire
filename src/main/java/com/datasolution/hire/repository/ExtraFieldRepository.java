package com.datasolution.hire.repository;

import com.datasolution.hire.domain.ExtraField;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ExtraField entity.
 */
@SuppressWarnings("unused")
public interface ExtraFieldRepository extends JpaRepository<ExtraField,Long> {

}
