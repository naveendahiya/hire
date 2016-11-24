package com.datasolution.hire.repository;

import com.datasolution.hire.domain.HttpLogTypes;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the HttpLogTypes entity.
 */
@SuppressWarnings("unused")
public interface HttpLogTypesRepository extends JpaRepository<HttpLogTypes,Long> {

}
