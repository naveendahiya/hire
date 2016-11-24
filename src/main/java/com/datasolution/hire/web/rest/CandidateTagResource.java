package com.datasolution.hire.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.datasolution.hire.domain.CandidateTag;

import com.datasolution.hire.repository.CandidateTagRepository;
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
 * REST controller for managing CandidateTag.
 */
@RestController
@RequestMapping("/api")
public class CandidateTagResource {

    private final Logger log = LoggerFactory.getLogger(CandidateTagResource.class);
        
    @Inject
    private CandidateTagRepository candidateTagRepository;

    /**
     * POST  /candidate-tags : Create a new candidateTag.
     *
     * @param candidateTag the candidateTag to create
     * @return the ResponseEntity with status 201 (Created) and with body the new candidateTag, or with status 400 (Bad Request) if the candidateTag has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/candidate-tags")
    @Timed
    public ResponseEntity<CandidateTag> createCandidateTag(@RequestBody CandidateTag candidateTag) throws URISyntaxException {
        log.debug("REST request to save CandidateTag : {}", candidateTag);
        if (candidateTag.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("candidateTag", "idexists", "A new candidateTag cannot already have an ID")).body(null);
        }
        CandidateTag result = candidateTagRepository.save(candidateTag);
        return ResponseEntity.created(new URI("/api/candidate-tags/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("candidateTag", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /candidate-tags : Updates an existing candidateTag.
     *
     * @param candidateTag the candidateTag to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated candidateTag,
     * or with status 400 (Bad Request) if the candidateTag is not valid,
     * or with status 500 (Internal Server Error) if the candidateTag couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/candidate-tags")
    @Timed
    public ResponseEntity<CandidateTag> updateCandidateTag(@RequestBody CandidateTag candidateTag) throws URISyntaxException {
        log.debug("REST request to update CandidateTag : {}", candidateTag);
        if (candidateTag.getId() == null) {
            return createCandidateTag(candidateTag);
        }
        CandidateTag result = candidateTagRepository.save(candidateTag);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("candidateTag", candidateTag.getId().toString()))
            .body(result);
    }

    /**
     * GET  /candidate-tags : get all the candidateTags.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of candidateTags in body
     */
    @GetMapping("/candidate-tags")
    @Timed
    public List<CandidateTag> getAllCandidateTags() {
        log.debug("REST request to get all CandidateTags");
        List<CandidateTag> candidateTags = candidateTagRepository.findAll();
        return candidateTags;
    }

    /**
     * GET  /candidate-tags/:id : get the "id" candidateTag.
     *
     * @param id the id of the candidateTag to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the candidateTag, or with status 404 (Not Found)
     */
    @GetMapping("/candidate-tags/{id}")
    @Timed
    public ResponseEntity<CandidateTag> getCandidateTag(@PathVariable Long id) {
        log.debug("REST request to get CandidateTag : {}", id);
        CandidateTag candidateTag = candidateTagRepository.findOne(id);
        return Optional.ofNullable(candidateTag)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /candidate-tags/:id : delete the "id" candidateTag.
     *
     * @param id the id of the candidateTag to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/candidate-tags/{id}")
    @Timed
    public ResponseEntity<Void> deleteCandidateTag(@PathVariable Long id) {
        log.debug("REST request to delete CandidateTag : {}", id);
        candidateTagRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("candidateTag", id.toString())).build();
    }

}
