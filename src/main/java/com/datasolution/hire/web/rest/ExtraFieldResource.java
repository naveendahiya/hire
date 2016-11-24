package com.datasolution.hire.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.datasolution.hire.domain.ExtraField;

import com.datasolution.hire.repository.ExtraFieldRepository;
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
 * REST controller for managing ExtraField.
 */
@RestController
@RequestMapping("/api")
public class ExtraFieldResource {

    private final Logger log = LoggerFactory.getLogger(ExtraFieldResource.class);
        
    @Inject
    private ExtraFieldRepository extraFieldRepository;

    /**
     * POST  /extra-fields : Create a new extraField.
     *
     * @param extraField the extraField to create
     * @return the ResponseEntity with status 201 (Created) and with body the new extraField, or with status 400 (Bad Request) if the extraField has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/extra-fields")
    @Timed
    public ResponseEntity<ExtraField> createExtraField(@RequestBody ExtraField extraField) throws URISyntaxException {
        log.debug("REST request to save ExtraField : {}", extraField);
        if (extraField.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("extraField", "idexists", "A new extraField cannot already have an ID")).body(null);
        }
        ExtraField result = extraFieldRepository.save(extraField);
        return ResponseEntity.created(new URI("/api/extra-fields/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("extraField", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /extra-fields : Updates an existing extraField.
     *
     * @param extraField the extraField to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated extraField,
     * or with status 400 (Bad Request) if the extraField is not valid,
     * or with status 500 (Internal Server Error) if the extraField couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/extra-fields")
    @Timed
    public ResponseEntity<ExtraField> updateExtraField(@RequestBody ExtraField extraField) throws URISyntaxException {
        log.debug("REST request to update ExtraField : {}", extraField);
        if (extraField.getId() == null) {
            return createExtraField(extraField);
        }
        ExtraField result = extraFieldRepository.save(extraField);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("extraField", extraField.getId().toString()))
            .body(result);
    }

    /**
     * GET  /extra-fields : get all the extraFields.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of extraFields in body
     */
    @GetMapping("/extra-fields")
    @Timed
    public List<ExtraField> getAllExtraFields() {
        log.debug("REST request to get all ExtraFields");
        List<ExtraField> extraFields = extraFieldRepository.findAll();
        return extraFields;
    }

    /**
     * GET  /extra-fields/:id : get the "id" extraField.
     *
     * @param id the id of the extraField to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the extraField, or with status 404 (Not Found)
     */
    @GetMapping("/extra-fields/{id}")
    @Timed
    public ResponseEntity<ExtraField> getExtraField(@PathVariable Long id) {
        log.debug("REST request to get ExtraField : {}", id);
        ExtraField extraField = extraFieldRepository.findOne(id);
        return Optional.ofNullable(extraField)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /extra-fields/:id : delete the "id" extraField.
     *
     * @param id the id of the extraField to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/extra-fields/{id}")
    @Timed
    public ResponseEntity<Void> deleteExtraField(@PathVariable Long id) {
        log.debug("REST request to delete ExtraField : {}", id);
        extraFieldRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("extraField", id.toString())).build();
    }

}
