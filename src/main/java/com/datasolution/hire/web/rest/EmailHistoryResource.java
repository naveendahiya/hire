package com.datasolution.hire.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.datasolution.hire.domain.EmailHistory;

import com.datasolution.hire.repository.EmailHistoryRepository;
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
 * REST controller for managing EmailHistory.
 */
@RestController
@RequestMapping("/api")
public class EmailHistoryResource {

    private final Logger log = LoggerFactory.getLogger(EmailHistoryResource.class);
        
    @Inject
    private EmailHistoryRepository emailHistoryRepository;

    /**
     * POST  /email-histories : Create a new emailHistory.
     *
     * @param emailHistory the emailHistory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new emailHistory, or with status 400 (Bad Request) if the emailHistory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/email-histories")
    @Timed
    public ResponseEntity<EmailHistory> createEmailHistory(@RequestBody EmailHistory emailHistory) throws URISyntaxException {
        log.debug("REST request to save EmailHistory : {}", emailHistory);
        if (emailHistory.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("emailHistory", "idexists", "A new emailHistory cannot already have an ID")).body(null);
        }
        EmailHistory result = emailHistoryRepository.save(emailHistory);
        return ResponseEntity.created(new URI("/api/email-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("emailHistory", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /email-histories : Updates an existing emailHistory.
     *
     * @param emailHistory the emailHistory to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated emailHistory,
     * or with status 400 (Bad Request) if the emailHistory is not valid,
     * or with status 500 (Internal Server Error) if the emailHistory couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/email-histories")
    @Timed
    public ResponseEntity<EmailHistory> updateEmailHistory(@RequestBody EmailHistory emailHistory) throws URISyntaxException {
        log.debug("REST request to update EmailHistory : {}", emailHistory);
        if (emailHistory.getId() == null) {
            return createEmailHistory(emailHistory);
        }
        EmailHistory result = emailHistoryRepository.save(emailHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("emailHistory", emailHistory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /email-histories : get all the emailHistories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of emailHistories in body
     */
    @GetMapping("/email-histories")
    @Timed
    public List<EmailHistory> getAllEmailHistories() {
        log.debug("REST request to get all EmailHistories");
        List<EmailHistory> emailHistories = emailHistoryRepository.findAll();
        return emailHistories;
    }

    /**
     * GET  /email-histories/:id : get the "id" emailHistory.
     *
     * @param id the id of the emailHistory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the emailHistory, or with status 404 (Not Found)
     */
    @GetMapping("/email-histories/{id}")
    @Timed
    public ResponseEntity<EmailHistory> getEmailHistory(@PathVariable Long id) {
        log.debug("REST request to get EmailHistory : {}", id);
        EmailHistory emailHistory = emailHistoryRepository.findOne(id);
        return Optional.ofNullable(emailHistory)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /email-histories/:id : delete the "id" emailHistory.
     *
     * @param id the id of the emailHistory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/email-histories/{id}")
    @Timed
    public ResponseEntity<Void> deleteEmailHistory(@PathVariable Long id) {
        log.debug("REST request to delete EmailHistory : {}", id);
        emailHistoryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("emailHistory", id.toString())).build();
    }

}
