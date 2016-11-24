package com.datasolution.hire.repository;

import com.datasolution.hire.domain.CareerPortalQuestionnaireQuestion;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CareerPortalQuestionnaireQuestion entity.
 */
@SuppressWarnings("unused")
public interface CareerPortalQuestionnaireQuestionRepository extends JpaRepository<CareerPortalQuestionnaireQuestion,Long> {

}
