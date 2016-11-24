package com.datasolution.hire.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.datasolution.hire.domain.History;

import com.datasolution.hire.repository.HistoryRepository;
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
 * REST controller for managing History.
 */
@RestController
@RequestMapping("/api")
public class HistoryResource {

    private final Logger log = LoggerFactory.getLogger(HistoryResource.class);
        
    @Inject
    private HistoryRepository historyRepository;

    /**
     * POST  /histories : Create a new history.
     *
     * @param history the history to create
     * @return the ResponseEntity with status 201 (Created) and with body the new history, or with status 400 (Bad Request) if the history has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/histories")
    @Timed
    public ResponseEntity<History> createHistory(@RequestBody History history) throws URISyntaxException {
        log.debug("REST request to save History : {}", history);
        if (history.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("history", "idexists", "A new history cannot already have an ID")).body(null);
        }
        History result = historyRepository.save(history);
        return ResponseEntity.created(new URI("/api/histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("history", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /histories : Updates an existing history.
     *
     * @param history the history to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated history,
     * or with status 400 (Bad Request) if the history is not valid,
     * or with status 500 (Internal Server Error) if the history couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/histories")
    @Timed
    public ResponseEntity<History> updateHistory(@RequestBody History history) throws URISyntaxException {
        log.debug("REST request to update History : {}", history);
        if (history.getId() == null) {
            return createHistory(history);
        }
        History result = historyRepository.save(history);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("history", history.getId().toString()))
            .body(result);
    }

    /**
     * GET  /histories : get all the histories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of histories in body
     */
    @GetMapping("/histories")
    @Timed
    public List<History> getAllHistories() {
        log.debug("REST request to get all Histories");
        List<History> histories = historyRepository.findAll();
        return histories;
    }

    /**
     * GET  /histories/:id : get the "id" history.
     *
     * @param id the id of the history to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the history, or with status 404 (Not Found)
     */
    @GetMapping("/histories/{id}")
    @Timed
    public ResponseEntity<History> getHistory(@PathVariable Long id) {
        log.debug("REST request to get History : {}", id);
        History history = historyRepository.findOne(id);
        return Optional.ofNullable(history)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /histories/:id : delete the "id" history.
     *
     * @param id the id of the history to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/histories/{id}")
    @Timed
    public ResponseEntity<Void> deleteHistory(@PathVariable Long id) {
        log.debug("REST request to delete History : {}", id);
        historyRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("history", id.toString())).build();
    }

}
