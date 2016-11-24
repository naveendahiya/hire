package com.datasolution.hire.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.datasolution.hire.domain.CandidateSource;

import com.datasolution.hire.repository.CandidateSourceRepository;
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
 * REST controller for managing CandidateSource.
 */
@RestController
@RequestMapping("/api")
public class CandidateSourceResource {

    private final Logger log = LoggerFactory.getLogger(CandidateSourceResource.class);
        
    @Inject
    private CandidateSourceRepository candidateSourceRepository;

    /**
     * POST  /candidate-sources : Create a new candidateSource.
     *
     * @param candidateSource the candidateSource to create
     * @return the ResponseEntity with status 201 (Created) and with body the new candidateSource, or with status 400 (Bad Request) if the candidateSource has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/candidate-sources")
    @Timed
    public ResponseEntity<CandidateSource> createCandidateSource(@RequestBody CandidateSource candidateSource) throws URISyntaxException {
        log.debug("REST request to save CandidateSource : {}", candidateSource);
        if (candidateSource.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("candidateSource", "idexists", "A new candidateSource cannot already have an ID")).body(null);
        }
        CandidateSource result = candidateSourceRepository.save(candidateSource);
        return ResponseEntity.created(new URI("/api/candidate-sources/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("candidateSource", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /candidate-sources : Updates an existing candidateSource.
     *
     * @param candidateSource the candidateSource to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated candidateSource,
     * or with status 400 (Bad Request) if the candidateSource is not valid,
     * or with status 500 (Internal Server Error) if the candidateSource couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/candidate-sources")
    @Timed
    public ResponseEntity<CandidateSource> updateCandidateSource(@RequestBody CandidateSource candidateSource) throws URISyntaxException {
        log.debug("REST request to update CandidateSource : {}", candidateSource);
        if (candidateSource.getId() == null) {
            return createCandidateSource(candidateSource);
        }
        CandidateSource result = candidateSourceRepository.save(candidateSource);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("candidateSource", candidateSource.getId().toString()))
            .body(result);
    }

    /**
     * GET  /candidate-sources : get all the candidateSources.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of candidateSources in body
     */
    @GetMapping("/candidate-sources")
    @Timed
    public List<CandidateSource> getAllCandidateSources() {
        log.debug("REST request to get all CandidateSources");
        List<CandidateSource> candidateSources = candidateSourceRepository.findAll();
        return candidateSources;
    }

    /**
     * GET  /candidate-sources/:id : get the "id" candidateSource.
     *
     * @param id the id of the candidateSource to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the candidateSource, or with status 404 (Not Found)
     */
    @GetMapping("/candidate-sources/{id}")
    @Timed
    public ResponseEntity<CandidateSource> getCandidateSource(@PathVariable Long id) {
        log.debug("REST request to get CandidateSource : {}", id);
        CandidateSource candidateSource = candidateSourceRepository.findOne(id);
        return Optional.ofNullable(candidateSource)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /candidate-sources/:id : delete the "id" candidateSource.
     *
     * @param id the id of the candidateSource to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/candidate-sources/{id}")
    @Timed
    public ResponseEntity<Void> deleteCandidateSource(@PathVariable Long id) {
        log.debug("REST request to delete CandidateSource : {}", id);
        candidateSourceRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("candidateSource", id.toString())).build();
    }

}
