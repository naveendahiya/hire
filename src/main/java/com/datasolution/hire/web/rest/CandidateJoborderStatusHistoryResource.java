package com.datasolution.hire.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.datasolution.hire.domain.CandidateJoborderStatusHistory;

import com.datasolution.hire.repository.CandidateJoborderStatusHistoryRepository;
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
 * REST controller for managing CandidateJoborderStatusHistory.
 */
@RestController
@RequestMapping("/api")
public class CandidateJoborderStatusHistoryResource {

    private final Logger log = LoggerFactory.getLogger(CandidateJoborderStatusHistoryResource.class);
        
    @Inject
    private CandidateJoborderStatusHistoryRepository candidateJoborderStatusHistoryRepository;

    /**
     * POST  /candidate-joborder-status-histories : Create a new candidateJoborderStatusHistory.
     *
     * @param candidateJoborderStatusHistory the candidateJoborderStatusHistory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new candidateJoborderStatusHistory, or with status 400 (Bad Request) if the candidateJoborderStatusHistory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/candidate-joborder-status-histories")
    @Timed
    public ResponseEntity<CandidateJoborderStatusHistory> createCandidateJoborderStatusHistory(@RequestBody CandidateJoborderStatusHistory candidateJoborderStatusHistory) throws URISyntaxException {
        log.debug("REST request to save CandidateJoborderStatusHistory : {}", candidateJoborderStatusHistory);
        if (candidateJoborderStatusHistory.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("candidateJoborderStatusHistory", "idexists", "A new candidateJoborderStatusHistory cannot already have an ID")).body(null);
        }
        CandidateJoborderStatusHistory result = candidateJoborderStatusHistoryRepository.save(candidateJoborderStatusHistory);
        return ResponseEntity.created(new URI("/api/candidate-joborder-status-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("candidateJoborderStatusHistory", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /candidate-joborder-status-histories : Updates an existing candidateJoborderStatusHistory.
     *
     * @param candidateJoborderStatusHistory the candidateJoborderStatusHistory to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated candidateJoborderStatusHistory,
     * or with status 400 (Bad Request) if the candidateJoborderStatusHistory is not valid,
     * or with status 500 (Internal Server Error) if the candidateJoborderStatusHistory couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/candidate-joborder-status-histories")
    @Timed
    public ResponseEntity<CandidateJoborderStatusHistory> updateCandidateJoborderStatusHistory(@RequestBody CandidateJoborderStatusHistory candidateJoborderStatusHistory) throws URISyntaxException {
        log.debug("REST request to update CandidateJoborderStatusHistory : {}", candidateJoborderStatusHistory);
        if (candidateJoborderStatusHistory.getId() == null) {
            return createCandidateJoborderStatusHistory(candidateJoborderStatusHistory);
        }
        CandidateJoborderStatusHistory result = candidateJoborderStatusHistoryRepository.save(candidateJoborderStatusHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("candidateJoborderStatusHistory", candidateJoborderStatusHistory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /candidate-joborder-status-histories : get all the candidateJoborderStatusHistories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of candidateJoborderStatusHistories in body
     */
    @GetMapping("/candidate-joborder-status-histories")
    @Timed
    public List<CandidateJoborderStatusHistory> getAllCandidateJoborderStatusHistories() {
        log.debug("REST request to get all CandidateJoborderStatusHistories");
        List<CandidateJoborderStatusHistory> candidateJoborderStatusHistories = candidateJoborderStatusHistoryRepository.findAll();
        return candidateJoborderStatusHistories;
    }

    /**
     * GET  /candidate-joborder-status-histories/:id : get the "id" candidateJoborderStatusHistory.
     *
     * @param id the id of the candidateJoborderStatusHistory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the candidateJoborderStatusHistory, or with status 404 (Not Found)
     */
    @GetMapping("/candidate-joborder-status-histories/{id}")
    @Timed
    public ResponseEntity<CandidateJoborderStatusHistory> getCandidateJoborderStatusHistory(@PathVariable Long id) {
        log.debug("REST request to get CandidateJoborderStatusHistory : {}", id);
        CandidateJoborderStatusHistory candidateJoborderStatusHistory = candidateJoborderStatusHistoryRepository.findOne(id);
        return Optional.ofNullable(candidateJoborderStatusHistory)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /candidate-joborder-status-histories/:id : delete the "id" candidateJoborderStatusHistory.
     *
     * @param id the id of the candidateJoborderStatusHistory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/candidate-joborder-status-histories/{id}")
    @Timed
    public ResponseEntity<Void> deleteCandidateJoborderStatusHistory(@PathVariable Long id) {
        log.debug("REST request to delete CandidateJoborderStatusHistory : {}", id);
        candidateJoborderStatusHistoryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("candidateJoborderStatusHistory", id.toString())).build();
    }

}
