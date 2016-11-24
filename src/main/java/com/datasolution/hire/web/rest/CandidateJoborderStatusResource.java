package com.datasolution.hire.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.datasolution.hire.domain.CandidateJoborderStatus;

import com.datasolution.hire.repository.CandidateJoborderStatusRepository;
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
 * REST controller for managing CandidateJoborderStatus.
 */
@RestController
@RequestMapping("/api")
public class CandidateJoborderStatusResource {

    private final Logger log = LoggerFactory.getLogger(CandidateJoborderStatusResource.class);
        
    @Inject
    private CandidateJoborderStatusRepository candidateJoborderStatusRepository;

    /**
     * POST  /candidate-joborder-statuses : Create a new candidateJoborderStatus.
     *
     * @param candidateJoborderStatus the candidateJoborderStatus to create
     * @return the ResponseEntity with status 201 (Created) and with body the new candidateJoborderStatus, or with status 400 (Bad Request) if the candidateJoborderStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/candidate-joborder-statuses")
    @Timed
    public ResponseEntity<CandidateJoborderStatus> createCandidateJoborderStatus(@RequestBody CandidateJoborderStatus candidateJoborderStatus) throws URISyntaxException {
        log.debug("REST request to save CandidateJoborderStatus : {}", candidateJoborderStatus);
        if (candidateJoborderStatus.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("candidateJoborderStatus", "idexists", "A new candidateJoborderStatus cannot already have an ID")).body(null);
        }
        CandidateJoborderStatus result = candidateJoborderStatusRepository.save(candidateJoborderStatus);
        return ResponseEntity.created(new URI("/api/candidate-joborder-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("candidateJoborderStatus", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /candidate-joborder-statuses : Updates an existing candidateJoborderStatus.
     *
     * @param candidateJoborderStatus the candidateJoborderStatus to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated candidateJoborderStatus,
     * or with status 400 (Bad Request) if the candidateJoborderStatus is not valid,
     * or with status 500 (Internal Server Error) if the candidateJoborderStatus couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/candidate-joborder-statuses")
    @Timed
    public ResponseEntity<CandidateJoborderStatus> updateCandidateJoborderStatus(@RequestBody CandidateJoborderStatus candidateJoborderStatus) throws URISyntaxException {
        log.debug("REST request to update CandidateJoborderStatus : {}", candidateJoborderStatus);
        if (candidateJoborderStatus.getId() == null) {
            return createCandidateJoborderStatus(candidateJoborderStatus);
        }
        CandidateJoborderStatus result = candidateJoborderStatusRepository.save(candidateJoborderStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("candidateJoborderStatus", candidateJoborderStatus.getId().toString()))
            .body(result);
    }

    /**
     * GET  /candidate-joborder-statuses : get all the candidateJoborderStatuses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of candidateJoborderStatuses in body
     */
    @GetMapping("/candidate-joborder-statuses")
    @Timed
    public List<CandidateJoborderStatus> getAllCandidateJoborderStatuses() {
        log.debug("REST request to get all CandidateJoborderStatuses");
        List<CandidateJoborderStatus> candidateJoborderStatuses = candidateJoborderStatusRepository.findAll();
        return candidateJoborderStatuses;
    }

    /**
     * GET  /candidate-joborder-statuses/:id : get the "id" candidateJoborderStatus.
     *
     * @param id the id of the candidateJoborderStatus to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the candidateJoborderStatus, or with status 404 (Not Found)
     */
    @GetMapping("/candidate-joborder-statuses/{id}")
    @Timed
    public ResponseEntity<CandidateJoborderStatus> getCandidateJoborderStatus(@PathVariable Long id) {
        log.debug("REST request to get CandidateJoborderStatus : {}", id);
        CandidateJoborderStatus candidateJoborderStatus = candidateJoborderStatusRepository.findOne(id);
        return Optional.ofNullable(candidateJoborderStatus)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /candidate-joborder-statuses/:id : delete the "id" candidateJoborderStatus.
     *
     * @param id the id of the candidateJoborderStatus to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/candidate-joborder-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteCandidateJoborderStatus(@PathVariable Long id) {
        log.debug("REST request to delete CandidateJoborderStatus : {}", id);
        candidateJoborderStatusRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("candidateJoborderStatus", id.toString())).build();
    }

}
