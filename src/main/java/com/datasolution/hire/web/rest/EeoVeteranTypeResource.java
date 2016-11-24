package com.datasolution.hire.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.datasolution.hire.domain.EeoVeteranType;

import com.datasolution.hire.repository.EeoVeteranTypeRepository;
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
 * REST controller for managing EeoVeteranType.
 */
@RestController
@RequestMapping("/api")
public class EeoVeteranTypeResource {

    private final Logger log = LoggerFactory.getLogger(EeoVeteranTypeResource.class);
        
    @Inject
    private EeoVeteranTypeRepository eeoVeteranTypeRepository;

    /**
     * POST  /eeo-veteran-types : Create a new eeoVeteranType.
     *
     * @param eeoVeteranType the eeoVeteranType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new eeoVeteranType, or with status 400 (Bad Request) if the eeoVeteranType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/eeo-veteran-types")
    @Timed
    public ResponseEntity<EeoVeteranType> createEeoVeteranType(@RequestBody EeoVeteranType eeoVeteranType) throws URISyntaxException {
        log.debug("REST request to save EeoVeteranType : {}", eeoVeteranType);
        if (eeoVeteranType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("eeoVeteranType", "idexists", "A new eeoVeteranType cannot already have an ID")).body(null);
        }
        EeoVeteranType result = eeoVeteranTypeRepository.save(eeoVeteranType);
        return ResponseEntity.created(new URI("/api/eeo-veteran-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("eeoVeteranType", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /eeo-veteran-types : Updates an existing eeoVeteranType.
     *
     * @param eeoVeteranType the eeoVeteranType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated eeoVeteranType,
     * or with status 400 (Bad Request) if the eeoVeteranType is not valid,
     * or with status 500 (Internal Server Error) if the eeoVeteranType couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/eeo-veteran-types")
    @Timed
    public ResponseEntity<EeoVeteranType> updateEeoVeteranType(@RequestBody EeoVeteranType eeoVeteranType) throws URISyntaxException {
        log.debug("REST request to update EeoVeteranType : {}", eeoVeteranType);
        if (eeoVeteranType.getId() == null) {
            return createEeoVeteranType(eeoVeteranType);
        }
        EeoVeteranType result = eeoVeteranTypeRepository.save(eeoVeteranType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("eeoVeteranType", eeoVeteranType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /eeo-veteran-types : get all the eeoVeteranTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of eeoVeteranTypes in body
     */
    @GetMapping("/eeo-veteran-types")
    @Timed
    public List<EeoVeteranType> getAllEeoVeteranTypes() {
        log.debug("REST request to get all EeoVeteranTypes");
        List<EeoVeteranType> eeoVeteranTypes = eeoVeteranTypeRepository.findAll();
        return eeoVeteranTypes;
    }

    /**
     * GET  /eeo-veteran-types/:id : get the "id" eeoVeteranType.
     *
     * @param id the id of the eeoVeteranType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the eeoVeteranType, or with status 404 (Not Found)
     */
    @GetMapping("/eeo-veteran-types/{id}")
    @Timed
    public ResponseEntity<EeoVeteranType> getEeoVeteranType(@PathVariable Long id) {
        log.debug("REST request to get EeoVeteranType : {}", id);
        EeoVeteranType eeoVeteranType = eeoVeteranTypeRepository.findOne(id);
        return Optional.ofNullable(eeoVeteranType)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /eeo-veteran-types/:id : delete the "id" eeoVeteranType.
     *
     * @param id the id of the eeoVeteranType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/eeo-veteran-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteEeoVeteranType(@PathVariable Long id) {
        log.debug("REST request to delete EeoVeteranType : {}", id);
        eeoVeteranTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("eeoVeteranType", id.toString())).build();
    }

}
