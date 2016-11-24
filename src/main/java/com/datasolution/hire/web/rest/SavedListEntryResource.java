package com.datasolution.hire.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.datasolution.hire.domain.SavedListEntry;

import com.datasolution.hire.repository.SavedListEntryRepository;
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
 * REST controller for managing SavedListEntry.
 */
@RestController
@RequestMapping("/api")
public class SavedListEntryResource {

    private final Logger log = LoggerFactory.getLogger(SavedListEntryResource.class);
        
    @Inject
    private SavedListEntryRepository savedListEntryRepository;

    /**
     * POST  /saved-list-entries : Create a new savedListEntry.
     *
     * @param savedListEntry the savedListEntry to create
     * @return the ResponseEntity with status 201 (Created) and with body the new savedListEntry, or with status 400 (Bad Request) if the savedListEntry has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/saved-list-entries")
    @Timed
    public ResponseEntity<SavedListEntry> createSavedListEntry(@RequestBody SavedListEntry savedListEntry) throws URISyntaxException {
        log.debug("REST request to save SavedListEntry : {}", savedListEntry);
        if (savedListEntry.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("savedListEntry", "idexists", "A new savedListEntry cannot already have an ID")).body(null);
        }
        SavedListEntry result = savedListEntryRepository.save(savedListEntry);
        return ResponseEntity.created(new URI("/api/saved-list-entries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("savedListEntry", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /saved-list-entries : Updates an existing savedListEntry.
     *
     * @param savedListEntry the savedListEntry to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated savedListEntry,
     * or with status 400 (Bad Request) if the savedListEntry is not valid,
     * or with status 500 (Internal Server Error) if the savedListEntry couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/saved-list-entries")
    @Timed
    public ResponseEntity<SavedListEntry> updateSavedListEntry(@RequestBody SavedListEntry savedListEntry) throws URISyntaxException {
        log.debug("REST request to update SavedListEntry : {}", savedListEntry);
        if (savedListEntry.getId() == null) {
            return createSavedListEntry(savedListEntry);
        }
        SavedListEntry result = savedListEntryRepository.save(savedListEntry);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("savedListEntry", savedListEntry.getId().toString()))
            .body(result);
    }

    /**
     * GET  /saved-list-entries : get all the savedListEntries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of savedListEntries in body
     */
    @GetMapping("/saved-list-entries")
    @Timed
    public List<SavedListEntry> getAllSavedListEntries() {
        log.debug("REST request to get all SavedListEntries");
        List<SavedListEntry> savedListEntries = savedListEntryRepository.findAll();
        return savedListEntries;
    }

    /**
     * GET  /saved-list-entries/:id : get the "id" savedListEntry.
     *
     * @param id the id of the savedListEntry to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the savedListEntry, or with status 404 (Not Found)
     */
    @GetMapping("/saved-list-entries/{id}")
    @Timed
    public ResponseEntity<SavedListEntry> getSavedListEntry(@PathVariable Long id) {
        log.debug("REST request to get SavedListEntry : {}", id);
        SavedListEntry savedListEntry = savedListEntryRepository.findOne(id);
        return Optional.ofNullable(savedListEntry)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /saved-list-entries/:id : delete the "id" savedListEntry.
     *
     * @param id the id of the savedListEntry to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/saved-list-entries/{id}")
    @Timed
    public ResponseEntity<Void> deleteSavedListEntry(@PathVariable Long id) {
        log.debug("REST request to delete SavedListEntry : {}", id);
        savedListEntryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("savedListEntry", id.toString())).build();
    }

}
