package com.datasolution.hire.repository;

import com.datasolution.hire.domain.DataItemType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the DataItemType entity.
 */
@SuppressWarnings("unused")
public interface DataItemTypeRepository extends JpaRepository<DataItemType,Long> {

}
