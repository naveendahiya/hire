package com.datasolution.hire.repository;

import com.datasolution.hire.domain.EeoEthnicType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the EeoEthnicType entity.
 */
@SuppressWarnings("unused")
public interface EeoEthnicTypeRepository extends JpaRepository<EeoEthnicType,Long> {

}
