package com.datasolution.hire.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.datasolution.hire.domain.HttpLog;

import com.datasolution.hire.repository.HttpLogRepository;
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
 * REST controller for managing HttpLog.
 */
@RestController
@RequestMapping("/api")
public class HttpLogResource {

    private final Logger log = LoggerFactory.getLogger(HttpLogResource.class);
        
    @Inject
    private HttpLogRepository httpLogRepository;

    /**
     * POST  /http-logs : Create a new httpLog.
     *
     * @param httpLog the httpLog to create
     * @return the ResponseEntity with status 201 (Created) and with body the new httpLog, or with status 400 (Bad Request) if the httpLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/http-logs")
    @Timed
    public ResponseEntity<HttpLog> createHttpLog(@RequestBody HttpLog httpLog) throws URISyntaxException {
        log.debug("REST request to save HttpLog : {}", httpLog);
        if (httpLog.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("httpLog", "idexists", "A new httpLog cannot already have an ID")).body(null);
        }
        HttpLog result = httpLogRepository.save(httpLog);
        return ResponseEntity.created(new URI("/api/http-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("httpLog", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /http-logs : Updates an existing httpLog.
     *
     * @param httpLog the httpLog to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated httpLog,
     * or with status 400 (Bad Request) if the httpLog is not valid,
     * or with status 500 (Internal Server Error) if the httpLog couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/http-logs")
    @Timed
    public ResponseEntity<HttpLog> updateHttpLog(@RequestBody HttpLog httpLog) throws URISyntaxException {
        log.debug("REST request to update HttpLog : {}", httpLog);
        if (httpLog.getId() == null) {
            return createHttpLog(httpLog);
        }
        HttpLog result = httpLogRepository.save(httpLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("httpLog", httpLog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /http-logs : get all the httpLogs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of httpLogs in body
     */
    @GetMapping("/http-logs")
    @Timed
    public List<HttpLog> getAllHttpLogs() {
        log.debug("REST request to get all HttpLogs");
        List<HttpLog> httpLogs = httpLogRepository.findAll();
        return httpLogs;
    }

    /**
     * GET  /http-logs/:id : get the "id" httpLog.
     *
     * @param id the id of the httpLog to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the httpLog, or with status 404 (Not Found)
     */
    @GetMapping("/http-logs/{id}")
    @Timed
    public ResponseEntity<HttpLog> getHttpLog(@PathVariable Long id) {
        log.debug("REST request to get HttpLog : {}", id);
        HttpLog httpLog = httpLogRepository.findOne(id);
        return Optional.ofNullable(httpLog)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /http-logs/:id : delete the "id" httpLog.
     *
     * @param id the id of the httpLog to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/http-logs/{id}")
    @Timed
    public ResponseEntity<Void> deleteHttpLog(@PathVariable Long id) {
        log.debug("REST request to delete HttpLog : {}", id);
        httpLogRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("httpLog", id.toString())).build();
    }

}
