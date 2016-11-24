package com.datasolution.hire.repository;

import com.datasolution.hire.domain.CareerPortalQuestionnaire;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CareerPortalQuestionnaire entity.
 */
@SuppressWarnings("unused")
public interface CareerPortalQuestionnaireRepository extends JpaRepository<CareerPortalQuestionnaire,Long> {

}
