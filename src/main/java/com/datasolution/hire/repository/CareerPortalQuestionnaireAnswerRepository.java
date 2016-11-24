package com.datasolution.hire.repository;

import com.datasolution.hire.domain.CareerPortalQuestionnaireAnswer;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CareerPortalQuestionnaireAnswer entity.
 */
@SuppressWarnings("unused")
public interface CareerPortalQuestionnaireAnswerRepository extends JpaRepository<CareerPortalQuestionnaireAnswer,Long> {

}
