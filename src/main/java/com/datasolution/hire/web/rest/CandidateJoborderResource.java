package com.datasolution.hire.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.datasolution.hire.domain.CandidateJoborder;

import com.datasolution.hire.repository.CandidateJoborderRepository;
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
 * REST controller for managing CandidateJoborder.
 */
@RestController
@RequestMapping("/api")
public class CandidateJoborderResource {

    private final Logger log = LoggerFactory.getLogger(CandidateJoborderResource.class);
        
    @Inject
    private CandidateJoborderRepository candidateJoborderRepository;

    /**
     * POST  /candidate-joborders : Create a new candidateJoborder.
     *
     * @param candidateJoborder the candidateJoborder to create
     * @return the ResponseEntity with status 201 (Created) and with body the new candidateJoborder, or with status 400 (Bad Request) if the candidateJoborder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/candidate-joborders")
    @Timed
    public ResponseEntity<CandidateJoborder> createCandidateJoborder(@RequestBody CandidateJoborder candidateJoborder) throws URISyntaxException {
        log.debug("REST request to save CandidateJoborder : {}", candidateJoborder);
        if (candidateJoborder.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("candidateJoborder", "idexists", "A new candidateJoborder cannot already have an ID")).body(null);
        }
        CandidateJoborder result = candidateJoborderRepository.save(candidateJoborder);
        return ResponseEntity.created(new URI("/api/candidate-joborders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("candidateJoborder", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /candidate-joborders : Updates an existing candidateJoborder.
     *
     * @param candidateJoborder the candidateJoborder to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated candidateJoborder,
     * or with status 400 (Bad Request) if the candidateJoborder is not valid,
     * or with status 500 (Internal Server Error) if the candidateJoborder couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/candidate-joborders")
    @Timed
    public ResponseEntity<CandidateJoborder> updateCandidateJoborder(@RequestBody CandidateJoborder candidateJoborder) throws URISyntaxException {
        log.debug("REST request to update CandidateJoborder : {}", candidateJoborder);
        if (candidateJoborder.getId() == null) {
            return createCandidateJoborder(candidateJoborder);
        }
        CandidateJoborder result = candidateJoborderRepository.save(candidateJoborder);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("candidateJoborder", candidateJoborder.getId().toString()))
            .body(result);
    }

    /**
     * GET  /candidate-joborders : get all the candidateJoborders.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of candidateJoborders in body
     */
    @GetMapping("/candidate-joborders")
    @Timed
    public List<CandidateJoborder> getAllCandidateJoborders() {
        log.debug("REST request to get all CandidateJoborders");
        List<CandidateJoborder> candidateJoborders = candidateJoborderRepository.findAll();
        return candidateJoborders;
    }

    /**
     * GET  /candidate-joborders/:id : get the "id" candidateJoborder.
     *
     * @param id the id of the candidateJoborder to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the candidateJoborder, or with status 404 (Not Found)
     */
    @GetMapping("/candidate-joborders/{id}")
    @Timed
    public ResponseEntity<CandidateJoborder> getCandidateJoborder(@PathVariable Long id) {
        log.debug("REST request to get CandidateJoborder : {}", id);
        CandidateJoborder candidateJoborder = candidateJoborderRepository.findOne(id);
        return Optional.ofNullable(candidateJoborder)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /candidate-joborders/:id : delete the "id" candidateJoborder.
     *
     * @param id the id of the candidateJoborder to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/candidate-joborders/{id}")
    @Timed
    public ResponseEntity<Void> deleteCandidateJoborder(@PathVariable Long id) {
        log.debug("REST request to delete CandidateJoborder : {}", id);
        candidateJoborderRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("candidateJoborder", id.toString())).build();
    }

}
