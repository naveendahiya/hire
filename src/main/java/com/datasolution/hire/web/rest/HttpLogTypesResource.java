package com.datasolution.hire.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.datasolution.hire.domain.HttpLogTypes;

import com.datasolution.hire.repository.HttpLogTypesRepository;
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
 * REST controller for managing HttpLogTypes.
 */
@RestController
@RequestMapping("/api")
public class HttpLogTypesResource {

    private final Logger log = LoggerFactory.getLogger(HttpLogTypesResource.class);
        
    @Inject
    private HttpLogTypesRepository httpLogTypesRepository;

    /**
     * POST  /http-log-types : Create a new httpLogTypes.
     *
     * @param httpLogTypes the httpLogTypes to create
     * @return the ResponseEntity with status 201 (Created) and with body the new httpLogTypes, or with status 400 (Bad Request) if the httpLogTypes has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/http-log-types")
    @Timed
    public ResponseEntity<HttpLogTypes> createHttpLogTypes(@RequestBody HttpLogTypes httpLogTypes) throws URISyntaxException {
        log.debug("REST request to save HttpLogTypes : {}", httpLogTypes);
        if (httpLogTypes.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("httpLogTypes", "idexists", "A new httpLogTypes cannot already have an ID")).body(null);
        }
        HttpLogTypes result = httpLogTypesRepository.save(httpLogTypes);
        return ResponseEntity.created(new URI("/api/http-log-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("httpLogTypes", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /http-log-types : Updates an existing httpLogTypes.
     *
     * @param httpLogTypes the httpLogTypes to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated httpLogTypes,
     * or with status 400 (Bad Request) if the httpLogTypes is not valid,
     * or with status 500 (Internal Server Error) if the httpLogTypes couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/http-log-types")
    @Timed
    public ResponseEntity<HttpLogTypes> updateHttpLogTypes(@RequestBody HttpLogTypes httpLogTypes) throws URISyntaxException {
        log.debug("REST request to update HttpLogTypes : {}", httpLogTypes);
        if (httpLogTypes.getId() == null) {
            return createHttpLogTypes(httpLogTypes);
        }
        HttpLogTypes result = httpLogTypesRepository.save(httpLogTypes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("httpLogTypes", httpLogTypes.getId().toString()))
            .body(result);
    }

    /**
     * GET  /http-log-types : get all the httpLogTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of httpLogTypes in body
     */
    @GetMapping("/http-log-types")
    @Timed
    public List<HttpLogTypes> getAllHttpLogTypes() {
        log.debug("REST request to get all HttpLogTypes");
        List<HttpLogTypes> httpLogTypes = httpLogTypesRepository.findAll();
        return httpLogTypes;
    }

    /**
     * GET  /http-log-types/:id : get the "id" httpLogTypes.
     *
     * @param id the id of the httpLogTypes to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the httpLogTypes, or with status 404 (Not Found)
     */
    @GetMapping("/http-log-types/{id}")
    @Timed
    public ResponseEntity<HttpLogTypes> getHttpLogTypes(@PathVariable Long id) {
        log.debug("REST request to get HttpLogTypes : {}", id);
        HttpLogTypes httpLogTypes = httpLogTypesRepository.findOne(id);
        return Optional.ofNullable(httpLogTypes)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /http-log-types/:id : delete the "id" httpLogTypes.
     *
     * @param id the id of the httpLogTypes to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/http-log-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteHttpLogTypes(@PathVariable Long id) {
        log.debug("REST request to delete HttpLogTypes : {}", id);
        httpLogTypesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("httpLogTypes", id.toString())).build();
    }

}
