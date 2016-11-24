package com.datasolution.hire.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.datasolution.hire.domain.Candidate;

import com.datasolution.hire.repository.CandidateRepository;
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
 * REST controller for managing Candidate.
 */
@RestController
@RequestMapping("/api")
public class CandidateResource {

    private final Logger log = LoggerFactory.getLogger(CandidateResource.class);
        
    @Inject
    private CandidateRepository candidateRepository;

    /**
     * POST  /candidates : Create a new candidate.
     *
     * @param candidate the candidate to create
     * @return the ResponseEntity with status 201 (Created) and with body the new candidate, or with status 400 (Bad Request) if the candidate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/candidates")
    @Timed
    public ResponseEntity<Candidate> createCandidate(@RequestBody Candidate candidate) throws URISyntaxException {
        log.debug("REST request to save Candidate : {}", candidate);
        if (candidate.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("candidate", "idexists", "A new candidate cannot already have an ID")).body(null);
        }
        Candidate result = candidateRepository.save(candidate);
        return ResponseEntity.created(new URI("/api/candidates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("candidate", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /candidates : Updates an existing candidate.
     *
     * @param candidate the candidate to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated candidate,
     * or with status 400 (Bad Request) if the candidate is not valid,
     * or with status 500 (Internal Server Error) if the candidate couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/candidates")
    @Timed
    public ResponseEntity<Candidate> updateCandidate(@RequestBody Candidate candidate) throws URISyntaxException {
        log.debug("REST request to update Candidate : {}", candidate);
        if (candidate.getId() == null) {
            return createCandidate(candidate);
        }
        Candidate result = candidateRepository.save(candidate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("candidate", candidate.getId().toString()))
            .body(result);
    }

    /**
     * GET  /candidates : get all the candidates.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of candidates in body
     */
    @GetMapping("/candidates")
    @Timed
    public List<Candidate> getAllCandidates() {
        log.debug("REST request to get all Candidates");
        List<Candidate> candidates = candidateRepository.findAll();
        return candidates;
    }

    /**
     * GET  /candidates/:id : get the "id" candidate.
     *
     * @param id the id of the candidate to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the candidate, or with status 404 (Not Found)
     */
    @GetMapping("/candidates/{id}")
    @Timed
    public ResponseEntity<Candidate> getCandidate(@PathVariable Long id) {
        log.debug("REST request to get Candidate : {}", id);
        Candidate candidate = candidateRepository.findOne(id);
        return Optional.ofNullable(candidate)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /candidates/:id : delete the "id" candidate.
     *
     * @param id the id of the candidate to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/candidates/{id}")
    @Timed
    public ResponseEntity<Void> deleteCandidate(@PathVariable Long id) {
        log.debug("REST request to delete Candidate : {}", id);
        candidateRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("candidate", id.toString())).build();
    }

}
