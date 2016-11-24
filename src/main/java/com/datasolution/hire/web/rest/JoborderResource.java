package com.datasolution.hire.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.datasolution.hire.domain.Joborder;

import com.datasolution.hire.repository.JoborderRepository;
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
 * REST controller for managing Joborder.
 */
@RestController
@RequestMapping("/api")
public class JoborderResource {

    private final Logger log = LoggerFactory.getLogger(JoborderResource.class);
        
    @Inject
    private JoborderRepository joborderRepository;

    /**
     * POST  /joborders : Create a new joborder.
     *
     * @param joborder the joborder to create
     * @return the ResponseEntity with status 201 (Created) and with body the new joborder, or with status 400 (Bad Request) if the joborder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/joborders")
    @Timed
    public ResponseEntity<Joborder> createJoborder(@RequestBody Joborder joborder) throws URISyntaxException {
        log.debug("REST request to save Joborder : {}", joborder);
        if (joborder.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("joborder", "idexists", "A new joborder cannot already have an ID")).body(null);
        }
        Joborder result = joborderRepository.save(joborder);
        return ResponseEntity.created(new URI("/api/joborders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("joborder", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /joborders : Updates an existing joborder.
     *
     * @param joborder the joborder to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated joborder,
     * or with status 400 (Bad Request) if the joborder is not valid,
     * or with status 500 (Internal Server Error) if the joborder couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/joborders")
    @Timed
    public ResponseEntity<Joborder> updateJoborder(@RequestBody Joborder joborder) throws URISyntaxException {
        log.debug("REST request to update Joborder : {}", joborder);
        if (joborder.getId() == null) {
            return createJoborder(joborder);
        }
        Joborder result = joborderRepository.save(joborder);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("joborder", joborder.getId().toString()))
            .body(result);
    }

    /**
     * GET  /joborders : get all the joborders.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of joborders in body
     */
    @GetMapping("/joborders")
    @Timed
    public List<Joborder> getAllJoborders() {
        log.debug("REST request to get all Joborders");
        List<Joborder> joborders = joborderRepository.findAll();
        return joborders;
    }

    /**
     * GET  /joborders/:id : get the "id" joborder.
     *
     * @param id the id of the joborder to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the joborder, or with status 404 (Not Found)
     */
    @GetMapping("/joborders/{id}")
    @Timed
    public ResponseEntity<Joborder> getJoborder(@PathVariable Long id) {
        log.debug("REST request to get Joborder : {}", id);
        Joborder joborder = joborderRepository.findOne(id);
        return Optional.ofNullable(joborder)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /joborders/:id : delete the "id" joborder.
     *
     * @param id the id of the joborder to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/joborders/{id}")
    @Timed
    public ResponseEntity<Void> deleteJoborder(@PathVariable Long id) {
        log.debug("REST request to delete Joborder : {}", id);
        joborderRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("joborder", id.toString())).build();
    }

}
