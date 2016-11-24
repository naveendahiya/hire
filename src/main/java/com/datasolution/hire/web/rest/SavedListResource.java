package com.datasolution.hire.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.datasolution.hire.domain.SavedList;

import com.datasolution.hire.repository.SavedListRepository;
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
 * REST controller for managing SavedList.
 */
@RestController
@RequestMapping("/api")
public class SavedListResource {

    private final Logger log = LoggerFactory.getLogger(SavedListResource.class);
        
    @Inject
    private SavedListRepository savedListRepository;

    /**
     * POST  /saved-lists : Create a new savedList.
     *
     * @param savedList the savedList to create
     * @return the ResponseEntity with status 201 (Created) and with body the new savedList, or with status 400 (Bad Request) if the savedList has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/saved-lists")
    @Timed
    public ResponseEntity<SavedList> createSavedList(@RequestBody SavedList savedList) throws URISyntaxException {
        log.debug("REST request to save SavedList : {}", savedList);
        if (savedList.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("savedList", "idexists", "A new savedList cannot already have an ID")).body(null);
        }
        SavedList result = savedListRepository.save(savedList);
        return ResponseEntity.created(new URI("/api/saved-lists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("savedList", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /saved-lists : Updates an existing savedList.
     *
     * @param savedList the savedList to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated savedList,
     * or with status 400 (Bad Request) if the savedList is not valid,
     * or with status 500 (Internal Server Error) if the savedList couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/saved-lists")
    @Timed
    public ResponseEntity<SavedList> updateSavedList(@RequestBody SavedList savedList) throws URISyntaxException {
        log.debug("REST request to update SavedList : {}", savedList);
        if (savedList.getId() == null) {
            return createSavedList(savedList);
        }
        SavedList result = savedListRepository.save(savedList);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("savedList", savedList.getId().toString()))
            .body(result);
    }

    /**
     * GET  /saved-lists : get all the savedLists.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of savedLists in body
     */
    @GetMapping("/saved-lists")
    @Timed
    public List<SavedList> getAllSavedLists() {
        log.debug("REST request to get all SavedLists");
        List<SavedList> savedLists = savedListRepository.findAll();
        return savedLists;
    }

    /**
     * GET  /saved-lists/:id : get the "id" savedList.
     *
     * @param id the id of the savedList to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the savedList, or with status 404 (Not Found)
     */
    @GetMapping("/saved-lists/{id}")
    @Timed
    public ResponseEntity<SavedList> getSavedList(@PathVariable Long id) {
        log.debug("REST request to get SavedList : {}", id);
        SavedList savedList = savedListRepository.findOne(id);
        return Optional.ofNullable(savedList)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /saved-lists/:id : delete the "id" savedList.
     *
     * @param id the id of the savedList to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/saved-lists/{id}")
    @Timed
    public ResponseEntity<Void> deleteSavedList(@PathVariable Long id) {
        log.debug("REST request to delete SavedList : {}", id);
        savedListRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("savedList", id.toString())).build();
    }

}
