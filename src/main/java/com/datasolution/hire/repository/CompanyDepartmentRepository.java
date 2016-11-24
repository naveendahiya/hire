package com.datasolution.hire.repository;

import com.datasolution.hire.domain.CompanyDepartment;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CompanyDepartment entity.
 */
@SuppressWarnings("unused")
public interface CompanyDepartmentRepository extends JpaRepository<CompanyDepartment,Long> {

}
