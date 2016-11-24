package com.datasolution.hire.repository;

import com.datasolution.hire.domain.CareerPortalQuestionnaireHistory;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CareerPortalQuestionnaireHistory entity.
 */
@SuppressWarnings("unused")
public interface CareerPortalQuestionnaireHistoryRepository extends JpaRepository<CareerPortalQuestionnaireHistory,Long> {

}
