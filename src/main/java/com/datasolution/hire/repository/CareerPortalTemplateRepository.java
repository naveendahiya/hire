package com.datasolution.hire.repository;

import com.datasolution.hire.domain.CareerPortalTemplate;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CareerPortalTemplate entity.
 */
@SuppressWarnings("unused")
public interface CareerPortalTemplateRepository extends JpaRepository<CareerPortalTemplate,Long> {

}
