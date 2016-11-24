package com.datasolution.hire.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.datasolution.hire.domain.ExtraFieldSettings;

import com.datasolution.hire.repository.ExtraFieldSettingsRepository;
import com.datasolution.hire.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ExtraFieldSettings.
 */
@RestController
@RequestMapping("/api")
public class ExtraFieldSettingsResource {

    private final Logger log = LoggerFactory.getLogger(ExtraFieldSettingsResource.class);
        
    @Inject
    private ExtraFieldSettingsRepository extraFieldSettingsRepository;

    /**
     * POST  /extra-field-settings : Create a new extraFieldSettings.
     *
     * @param extraFieldSettings the extraFieldSettings to create
     * @return the ResponseEntity with status 201 (Created) and with body the new extraFieldSettings, or with status 400 (Bad Request) if the extraFieldSettings has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/extra-field-settings")
    @Timed
    public ResponseEntity<ExtraFieldSettings> createExtraFieldSettings(@RequestBody ExtraFieldSettings extraFieldSettings) throws URISyntaxException {
        log.debug("REST request to save ExtraFieldSettings : {}", extraFieldSettings);
        if (extraFieldSettings.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("extraFieldSettings", "idexists", "A new extraFieldSettings cannot already have an ID")).body(null);
        }
        ExtraFieldSettings result = extraFieldSettingsRepository.save(extraFieldSettings);
        return ResponseEntity.created(new URI("/api/extra-field-settings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("extraFieldSettings", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /extra-field-settings : Updates an existing extraFieldSettings.
     *
     * @param extraFieldSettings the extraFieldSettings to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated extraFieldSettings,
     * or with status 400 (Bad Request) if the extraFieldSettings is not valid,
     * or with status 500 (Internal Server Error) if the extraFieldSettings couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/extra-field-settings")
    @Timed
    public ResponseEntity<ExtraFieldSettings> updateExtraFieldSettings(@RequestBody ExtraFieldSettings extraFieldSettings) throws URISyntaxException {
        log.debug("REST request to update ExtraFieldSettings : {}", extraFieldSettings);
        if (extraFieldSettings.getId() == null) {
            return createExtraFieldSettings(extraFieldSettings);
        }
        ExtraFieldSettings result = extraFieldSettingsRepository.save(extraFieldSettings);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("extraFieldSettings", extraFieldSettings.getId().toString()))
            .body(result);
    }

    /**
     * GET  /extra-field-settings : get all the extraFieldSettings.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of extraFieldSettings in body
     */
    @GetMapping("/extra-field-settings")
    @Timed
    public List<ExtraFieldSettings> getAllExtraFieldSettings() {
        log.debug("REST request to get all ExtraFieldSettings");
        List<ExtraFieldSettings> extraFieldSettings = extraFieldSettingsRepository.findAll();
        return extraFieldSettings;
    }

    /**
     * GET  /extra-field-settings/:id : get the "id" extraFieldSettings.
     *
     * @param id the id of the extraFieldSettings to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the extraFieldSettings, or with status 404 (Not Found)
     */
    @GetMapping("/extra-field-settings/{id}")
    @Timed
    public ResponseEntity<ExtraFieldSettings> getExtraFieldSettings(@PathVariable Long id) {
        log.debug("REST request to get ExtraFieldSettings : {}", id);
        ExtraFieldSettings extraFieldSettings = extraFieldSettingsRepository.findOne(id);
        return Optional.ofNullable(extraFieldSettings)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /extra-field-settings/:id : delete the "id" extraFieldSettings.
     *
     * @param id the id of the extraFieldSettings to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/extra-field-settings/{id}")
    @Timed
    public ResponseEntity<Void> deleteExtraFieldSettings(@PathVariable Long id) {
        log.debug("REST request to delete ExtraFieldSettings : {}", id);
        extraFieldSettingsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("extraFieldSettings", id.toString())).build();
    }

}
