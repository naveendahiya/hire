package com.datasolution.hire.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.datasolution.hire.domain.CandidateJobordrerStatusType;

import com.datasolution.hire.repository.CandidateJobordrerStatusTypeRepository;
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
 * REST controller for managing CandidateJobordrerStatusType.
 */
@RestController
@RequestMapping("/api")
public class CandidateJobordrerStatusTypeResource {

    private final Logger log = LoggerFactory.getLogger(CandidateJobordrerStatusTypeResource.class);
        
    @Inject
    private CandidateJobordrerStatusTypeRepository candidateJobordrerStatusTypeRepository;

    /**
     * POST  /candidate-jobordrer-status-types : Create a new candidateJobordrerStatusType.
     *
     * @param candidateJobordrerStatusType the candidateJobordrerStatusType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new candidateJobordrerStatusType, or with status 400 (Bad Request) if the candidateJobordrerStatusType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/candidate-jobordrer-status-types")
    @Timed
    public ResponseEntity<CandidateJobordrerStatusType> createCandidateJobordrerStatusType(@RequestBody CandidateJobordrerStatusType candidateJobordrerStatusType) throws URISyntaxException {
        log.debug("REST request to save CandidateJobordrerStatusType : {}", candidateJobordrerStatusType);
        if (candidateJobordrerStatusType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("candidateJobordrerStatusType", "idexists", "A new candidateJobordrerStatusType cannot already have an ID")).body(null);
        }
        CandidateJobordrerStatusType result = candidateJobordrerStatusTypeRepository.save(candidateJobordrerStatusType);
        return ResponseEntity.created(new URI("/api/candidate-jobordrer-status-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("candidateJobordrerStatusType", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /candidate-jobordrer-status-types : Updates an existing candidateJobordrerStatusType.
     *
     * @param candidateJobordrerStatusType the candidateJobordrerStatusType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated candidateJobordrerStatusType,
     * or with status 400 (Bad Request) if the candidateJobordrerStatusType is not valid,
     * or with status 500 (Internal Server Error) if the candidateJobordrerStatusType couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/candidate-jobordrer-status-types")
    @Timed
    public ResponseEntity<CandidateJobordrerStatusType> updateCandidateJobordrerStatusType(@RequestBody CandidateJobordrerStatusType candidateJobordrerStatusType) throws URISyntaxException {
        log.debug("REST request to update CandidateJobordrerStatusType : {}", candidateJobordrerStatusType);
        if (candidateJobordrerStatusType.getId() == null) {
            return createCandidateJobordrerStatusType(candidateJobordrerStatusType);
        }
        CandidateJobordrerStatusType result = candidateJobordrerStatusTypeRepository.save(candidateJobordrerStatusType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("candidateJobordrerStatusType", candidateJobordrerStatusType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /candidate-jobordrer-status-types : get all the candidateJobordrerStatusTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of candidateJobordrerStatusTypes in body
     */
    @GetMapping("/candidate-jobordrer-status-types")
    @Timed
    public List<CandidateJobordrerStatusType> getAllCandidateJobordrerStatusTypes() {
        log.debug("REST request to get all CandidateJobordrerStatusTypes");
        List<CandidateJobordrerStatusType> candidateJobordrerStatusTypes = candidateJobordrerStatusTypeRepository.findAll();
        return candidateJobordrerStatusTypes;
    }

    /**
     * GET  /candidate-jobordrer-status-types/:id : get the "id" candidateJobordrerStatusType.
     *
     * @param id the id of the candidateJobordrerStatusType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the candidateJobordrerStatusType, or with status 404 (Not Found)
     */
    @GetMapping("/candidate-jobordrer-status-types/{id}")
    @Timed
    public ResponseEntity<CandidateJobordrerStatusType> getCandidateJobordrerStatusType(@PathVariable Long id) {
        log.debug("REST request to get CandidateJobordrerStatusType : {}", id);
        CandidateJobordrerStatusType candidateJobordrerStatusType = candidateJobordrerStatusTypeRepository.findOne(id);
        return Optional.ofNullable(candidateJobordrerStatusType)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /candidate-jobordrer-status-types/:id : delete the "id" candidateJobordrerStatusType.
     *
     * @param id the id of the candidateJobordrerStatusType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/candidate-jobordrer-status-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteCandidateJobordrerStatusType(@PathVariable Long id) {
        log.debug("REST request to delete CandidateJobordrerStatusType : {}", id);
        candidateJobordrerStatusTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("candidateJobordrerStatusType", id.toString())).build();
    }

}
