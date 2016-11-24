package com.datasolution.hire.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.datasolution.hire.domain.SavedSearch;

import com.datasolution.hire.repository.SavedSearchRepository;
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
 * REST controller for managing SavedSearch.
 */
@RestController
@RequestMapping("/api")
public class SavedSearchResource {

    private final Logger log = LoggerFactory.getLogger(SavedSearchResource.class);
        
    @Inject
    private SavedSearchRepository savedSearchRepository;

    /**
     * POST  /saved-searches : Create a new savedSearch.
     *
     * @param savedSearch the savedSearch to create
     * @return the ResponseEntity with status 201 (Created) and with body the new savedSearch, or with status 400 (Bad Request) if the savedSearch has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/saved-searches")
    @Timed
    public ResponseEntity<SavedSearch> createSavedSearch(@RequestBody SavedSearch savedSearch) throws URISyntaxException {
        log.debug("REST request to save SavedSearch : {}", savedSearch);
        if (savedSearch.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("savedSearch", "idexists", "A new savedSearch cannot already have an ID")).body(null);
        }
        SavedSearch result = savedSearchRepository.save(savedSearch);
        return ResponseEntity.created(new URI("/api/saved-searches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("savedSearch", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /saved-searches : Updates an existing savedSearch.
     *
     * @param savedSearch the savedSearch to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated savedSearch,
     * or with status 400 (Bad Request) if the savedSearch is not valid,
     * or with status 500 (Internal Server Error) if the savedSearch couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/saved-searches")
    @Timed
    public ResponseEntity<SavedSearch> updateSavedSearch(@RequestBody SavedSearch savedSearch) throws URISyntaxException {
        log.debug("REST request to update SavedSearch : {}", savedSearch);
        if (savedSearch.getId() == null) {
            return createSavedSearch(savedSearch);
        }
        SavedSearch result = savedSearchRepository.save(savedSearch);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("savedSearch", savedSearch.getId().toString()))
            .body(result);
    }

    /**
     * GET  /saved-searches : get all the savedSearches.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of savedSearches in body
     */
    @GetMapping("/saved-searches")
    @Timed
    public List<SavedSearch> getAllSavedSearches() {
        log.debug("REST request to get all SavedSearches");
        List<SavedSearch> savedSearches = savedSearchRepository.findAll();
        return savedSearches;
    }

    /**
     * GET  /saved-searches/:id : get the "id" savedSearch.
     *
     * @param id the id of the savedSearch to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the savedSearch, or with status 404 (Not Found)
     */
    @GetMapping("/saved-searches/{id}")
    @Timed
    public ResponseEntity<SavedSearch> getSavedSearch(@PathVariable Long id) {
        log.debug("REST request to get SavedSearch : {}", id);
        SavedSearch savedSearch = savedSearchRepository.findOne(id);
        return Optional.ofNullable(savedSearch)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /saved-searches/:id : delete the "id" savedSearch.
     *
     * @param id the id of the savedSearch to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/saved-searches/{id}")
    @Timed
    public ResponseEntity<Void> deleteSavedSearch(@PathVariable Long id) {
        log.debug("REST request to delete SavedSearch : {}", id);
        savedSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("savedSearch", id.toString())).build();
    }

}
