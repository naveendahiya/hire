package com.datasolution.hire.repository;

import com.datasolution.hire.domain.ExtraFieldSettings;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ExtraFieldSettings entity.
 */
@SuppressWarnings("unused")
public interface ExtraFieldSettingsRepository extends JpaRepository<ExtraFieldSettings,Long> {

}
