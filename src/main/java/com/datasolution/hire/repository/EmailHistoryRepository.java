package com.datasolution.hire.repository;

import com.datasolution.hire.domain.EmailHistory;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the EmailHistory entity.
 */
@SuppressWarnings("unused")
public interface EmailHistoryRepository extends JpaRepository<EmailHistory,Long> {

}
