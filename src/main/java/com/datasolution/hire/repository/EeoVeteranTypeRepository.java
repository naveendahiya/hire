package com.datasolution.hire.repository;

import com.datasolution.hire.domain.EeoVeteranType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the EeoVeteranType entity.
 */
@SuppressWarnings("unused")
public interface EeoVeteranTypeRepository extends JpaRepository<EeoVeteranType,Long> {

}
