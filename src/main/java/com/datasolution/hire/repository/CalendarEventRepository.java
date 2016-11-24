package com.datasolution.hire.repository;

import com.datasolution.hire.domain.CalendarEvent;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CalendarEvent entity.
 */
@SuppressWarnings("unused")
public interface CalendarEventRepository extends JpaRepository<CalendarEvent,Long> {

}
