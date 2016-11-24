package com.datasolution.hire.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.datasolution.hire.domain.Mru;

import com.datasolution.hire.repository.MruRepository;
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
 * REST controller for managing Mru.
 */
@RestController
@RequestMapping("/api")
public class MruResource {

    private final Logger log = LoggerFactory.getLogger(MruResource.class);
        
    @Inject
    private MruRepository mruRepository;

    /**
     * POST  /mrus : Create a new mru.
     *
     * @param mru the mru to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mru, or with status 400 (Bad Request) if the mru has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mrus")
    @Timed
    public ResponseEntity<Mru> createMru(@RequestBody Mru mru) throws URISyntaxException {
        log.debug("REST request to save Mru : {}", mru);
        if (mru.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("mru", "idexists", "A new mru cannot already have an ID")).body(null);
        }
        Mru result = mruRepository.save(mru);
        return ResponseEntity.created(new URI("/api/mrus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("mru", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mrus : Updates an existing mru.
     *
     * @param mru the mru to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mru,
     * or with status 400 (Bad Request) if the mru is not valid,
     * or with status 500 (Internal Server Error) if the mru couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mrus")
    @Timed
    public ResponseEntity<Mru> updateMru(@RequestBody Mru mru) throws URISyntaxException {
        log.debug("REST request to update Mru : {}", mru);
        if (mru.getId() == null) {
            return createMru(mru);
        }
        Mru result = mruRepository.save(mru);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("mru", mru.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mrus : get all the mrus.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of mrus in body
     */
    @GetMapping("/mrus")
    @Timed
    public List<Mru> getAllMrus() {
        log.debug("REST request to get all Mrus");
        List<Mru> mrus = mruRepository.findAll();
        return mrus;
    }

    /**
     * GET  /mrus/:id : get the "id" mru.
     *
     * @param id the id of the mru to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mru, or with status 404 (Not Found)
     */
    @GetMapping("/mrus/{id}")
    @Timed
    public ResponseEntity<Mru> getMru(@PathVariable Long id) {
        log.debug("REST request to get Mru : {}", id);
        Mru mru = mruRepository.findOne(id);
        return Optional.ofNullable(mru)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /mrus/:id : delete the "id" mru.
     *
     * @param id the id of the mru to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mrus/{id}")
    @Timed
    public ResponseEntity<Void> deleteMru(@PathVariable Long id) {
        log.debug("REST request to delete Mru : {}", id);
        mruRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("mru", id.toString())).build();
    }

}
