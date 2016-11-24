package com.datasolution.hire.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.datasolution.hire.domain.EeoEthnicType;

import com.datasolution.hire.repository.EeoEthnicTypeRepository;
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
 * REST controller for managing EeoEthnicType.
 */
@RestController
@RequestMapping("/api")
public class EeoEthnicTypeResource {

    private final Logger log = LoggerFactory.getLogger(EeoEthnicTypeResource.class);
        
    @Inject
    private EeoEthnicTypeRepository eeoEthnicTypeRepository;

    /**
     * POST  /eeo-ethnic-types : Create a new eeoEthnicType.
     *
     * @param eeoEthnicType the eeoEthnicType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new eeoEthnicType, or with status 400 (Bad Request) if the eeoEthnicType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/eeo-ethnic-types")
    @Timed
    public ResponseEntity<EeoEthnicType> createEeoEthnicType(@RequestBody EeoEthnicType eeoEthnicType) throws URISyntaxException {
        log.debug("REST request to save EeoEthnicType : {}", eeoEthnicType);
        if (eeoEthnicType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("eeoEthnicType", "idexists", "A new eeoEthnicType cannot already have an ID")).body(null);
        }
        EeoEthnicType result = eeoEthnicTypeRepository.save(eeoEthnicType);
        return ResponseEntity.created(new URI("/api/eeo-ethnic-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("eeoEthnicType", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /eeo-ethnic-types : Updates an existing eeoEthnicType.
     *
     * @param eeoEthnicType the eeoEthnicType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated eeoEthnicType,
     * or with status 400 (Bad Request) if the eeoEthnicType is not valid,
     * or with status 500 (Internal Server Error) if the eeoEthnicType couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/eeo-ethnic-types")
    @Timed
    public ResponseEntity<EeoEthnicType> updateEeoEthnicType(@RequestBody EeoEthnicType eeoEthnicType) throws URISyntaxException {
        log.debug("REST request to update EeoEthnicType : {}", eeoEthnicType);
        if (eeoEthnicType.getId() == null) {
            return createEeoEthnicType(eeoEthnicType);
        }
        EeoEthnicType result = eeoEthnicTypeRepository.save(eeoEthnicType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("eeoEthnicType", eeoEthnicType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /eeo-ethnic-types : get all the eeoEthnicTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of eeoEthnicTypes in body
     */
    @GetMapping("/eeo-ethnic-types")
    @Timed
    public List<EeoEthnicType> getAllEeoEthnicTypes() {
        log.debug("REST request to get all EeoEthnicTypes");
        List<EeoEthnicType> eeoEthnicTypes = eeoEthnicTypeRepository.findAll();
        return eeoEthnicTypes;
    }

    /**
     * GET  /eeo-ethnic-types/:id : get the "id" eeoEthnicType.
     *
     * @param id the id of the eeoEthnicType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the eeoEthnicType, or with status 404 (Not Found)
     */
    @GetMapping("/eeo-ethnic-types/{id}")
    @Timed
    public ResponseEntity<EeoEthnicType> getEeoEthnicType(@PathVariable Long id) {
        log.debug("REST request to get EeoEthnicType : {}", id);
        EeoEthnicType eeoEthnicType = eeoEthnicTypeRepository.findOne(id);
        return Optional.ofNullable(eeoEthnicType)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /eeo-ethnic-types/:id : delete the "id" eeoEthnicType.
     *
     * @param id the id of the eeoEthnicType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/eeo-ethnic-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteEeoEthnicType(@PathVariable Long id) {
        log.debug("REST request to delete EeoEthnicType : {}", id);
        eeoEthnicTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("eeoEthnicType", id.toString())).build();
    }

}
