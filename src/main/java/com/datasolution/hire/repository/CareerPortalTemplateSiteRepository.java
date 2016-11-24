package com.datasolution.hire.repository;

import com.datasolution.hire.domain.CareerPortalTemplateSite;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CareerPortalTemplateSite entity.
 */
@SuppressWarnings("unused")
public interface CareerPortalTemplateSiteRepository extends JpaRepository<CareerPortalTemplateSite,Long> {

}
