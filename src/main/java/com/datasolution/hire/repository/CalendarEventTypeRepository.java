package com.datasolution.hire.repository;

import com.datasolution.hire.domain.CalendarEventType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CalendarEventType entity.
 */
@SuppressWarnings("unused")
public interface CalendarEventTypeRepository extends JpaRepository<CalendarEventType,Long> {

}
