package com.datasolution.hire.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.datasolution.hire.domain.ExtensionStatistics;

import com.datasolution.hire.repository.ExtensionStatisticsRepository;
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
 * REST controller for managing ExtensionStatistics.
 */
@RestController
@RequestMapping("/api")
public class ExtensionStatisticsResource {

    private final Logger log = LoggerFactory.getLogger(ExtensionStatisticsResource.class);
        
    @Inject
    private ExtensionStatisticsRepository extensionStatisticsRepository;

    /**
     * POST  /extension-statistics : Create a new extensionStatistics.
     *
     * @param extensionStatistics the extensionStatistics to create
     * @return the ResponseEntity with status 201 (Created) and with body the new extensionStatistics, or with status 400 (Bad Request) if the extensionStatistics has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/extension-statistics")
    @Timed
    public ResponseEntity<ExtensionStatistics> createExtensionStatistics(@RequestBody ExtensionStatistics extensionStatistics) throws URISyntaxException {
        log.debug("REST request to save ExtensionStatistics : {}", extensionStatistics);
        if (extensionStatistics.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("extensionStatistics", "idexists", "A new extensionStatistics cannot already have an ID")).body(null);
        }
        ExtensionStatistics result = extensionStatisticsRepository.save(extensionStatistics);
        return ResponseEntity.created(new URI("/api/extension-statistics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("extensionStatistics", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /extension-statistics : Updates an existing extensionStatistics.
     *
     * @param extensionStatistics the extensionStatistics to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated extensionStatistics,
     * or with status 400 (Bad Request) if the extensionStatistics is not valid,
     * or with status 500 (Internal Server Error) if the extensionStatistics couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/extension-statistics")
    @Timed
    public ResponseEntity<ExtensionStatistics> updateExtensionStatistics(@RequestBody ExtensionStatistics extensionStatistics) throws URISyntaxException {
        log.debug("REST request to update ExtensionStatistics : {}", extensionStatistics);
        if (extensionStatistics.getId() == null) {
            return createExtensionStatistics(extensionStatistics);
        }
        ExtensionStatistics result = extensionStatisticsRepository.save(extensionStatistics);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("extensionStatistics", extensionStatistics.getId().toString()))
            .body(result);
    }

    /**
     * GET  /extension-statistics : get all the extensionStatistics.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of extensionStatistics in body
     */
    @GetMapping("/extension-statistics")
    @Timed
    public List<ExtensionStatistics> getAllExtensionStatistics() {
        log.debug("REST request to get all ExtensionStatistics");
        List<ExtensionStatistics> extensionStatistics = extensionStatisticsRepository.findAll();
        return extensionStatistics;
    }

    /**
     * GET  /extension-statistics/:id : get the "id" extensionStatistics.
     *
     * @param id the id of the extensionStatistics to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the extensionStatistics, or with status 404 (Not Found)
     */
    @GetMapping("/extension-statistics/{id}")
    @Timed
    public ResponseEntity<ExtensionStatistics> getExtensionStatistics(@PathVariable Long id) {
        log.debug("REST request to get ExtensionStatistics : {}", id);
        ExtensionStatistics extensionStatistics = extensionStatisticsRepository.findOne(id);
        return Optional.ofNullable(extensionStatistics)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /extension-statistics/:id : delete the "id" extensionStatistics.
     *
     * @param id the id of the extensionStatistics to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/extension-statistics/{id}")
    @Timed
    public ResponseEntity<Void> deleteExtensionStatistics(@PathVariable Long id) {
        log.debug("REST request to delete ExtensionStatistics : {}", id);
        extensionStatisticsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("extensionStatistics", id.toString())).build();
    }

}
