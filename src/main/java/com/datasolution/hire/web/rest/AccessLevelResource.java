package com.datasolution.hire.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.datasolution.hire.domain.AccessLevel;

import com.datasolution.hire.repository.AccessLevelRepository;
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
 * REST controller for managing AccessLevel.
 */
@RestController
@RequestMapping("/api")
public class AccessLevelResource {

    private final Logger log = LoggerFactory.getLogger(AccessLevelResource.class);
        
    @Inject
    private AccessLevelRepository accessLevelRepository;

    /**
     * POST  /access-levels : Create a new accessLevel.
     *
     * @param accessLevel the accessLevel to create
     * @return the ResponseEntity with status 201 (Created) and with body the new accessLevel, or with status 400 (Bad Request) if the accessLevel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/access-levels")
    @Timed
    public ResponseEntity<AccessLevel> createAccessLevel(@RequestBody AccessLevel accessLevel) throws URISyntaxException {
        log.debug("REST request to save AccessLevel : {}", accessLevel);
        if (accessLevel.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("accessLevel", "idexists", "A new accessLevel cannot already have an ID")).body(null);
        }
        AccessLevel result = accessLevelRepository.save(accessLevel);
        return ResponseEntity.created(new URI("/api/access-levels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("accessLevel", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /access-levels : Updates an existing accessLevel.
     *
     * @param accessLevel the accessLevel to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated accessLevel,
     * or with status 400 (Bad Request) if the accessLevel is not valid,
     * or with status 500 (Internal Server Error) if the accessLevel couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/access-levels")
    @Timed
    public ResponseEntity<AccessLevel> updateAccessLevel(@RequestBody AccessLevel accessLevel) throws URISyntaxException {
        log.debug("REST request to update AccessLevel : {}", accessLevel);
        if (accessLevel.getId() == null) {
            return createAccessLevel(accessLevel);
        }
        AccessLevel result = accessLevelRepository.save(accessLevel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("accessLevel", accessLevel.getId().toString()))
            .body(result);
    }

    /**
     * GET  /access-levels : get all the accessLevels.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of accessLevels in body
     */
    @GetMapping("/access-levels")
    @Timed
    public List<AccessLevel> getAllAccessLevels() {
        log.debug("REST request to get all AccessLevels");
        List<AccessLevel> accessLevels = accessLevelRepository.findAll();
        return accessLevels;
    }

    /**
     * GET  /access-levels/:id : get the "id" accessLevel.
     *
     * @param id the id of the accessLevel to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the accessLevel, or with status 404 (Not Found)
     */
    @GetMapping("/access-levels/{id}")
    @Timed
    public ResponseEntity<AccessLevel> getAccessLevel(@PathVariable Long id) {
        log.debug("REST request to get AccessLevel : {}", id);
        AccessLevel accessLevel = accessLevelRepository.findOne(id);
        return Optional.ofNullable(accessLevel)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /access-levels/:id : delete the "id" accessLevel.
     *
     * @param id the id of the accessLevel to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/access-levels/{id}")
    @Timed
    public ResponseEntity<Void> deleteAccessLevel(@PathVariable Long id) {
        log.debug("REST request to delete AccessLevel : {}", id);
        accessLevelRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("accessLevel", id.toString())).build();
    }

}
