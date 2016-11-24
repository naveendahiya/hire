package com.datasolution.hire.repository;

import com.datasolution.hire.domain.ModuleSchema;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ModuleSchema entity.
 */
@SuppressWarnings("unused")
public interface ModuleSchemaRepository extends JpaRepository<ModuleSchema,Long> {

}
