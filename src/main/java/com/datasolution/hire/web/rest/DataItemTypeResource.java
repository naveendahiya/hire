package com.datasolution.hire.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.datasolution.hire.domain.DataItemType;

import com.datasolution.hire.repository.DataItemTypeRepository;
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
 * REST controller for managing DataItemType.
 */
@RestController
@RequestMapping("/api")
public class DataItemTypeResource {

    private final Logger log = LoggerFactory.getLogger(DataItemTypeResource.class);
        
    @Inject
    private DataItemTypeRepository dataItemTypeRepository;

    /**
     * POST  /data-item-types : Create a new dataItemType.
     *
     * @param dataItemType the dataItemType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dataItemType, or with status 400 (Bad Request) if the dataItemType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/data-item-types")
    @Timed
    public ResponseEntity<DataItemType> createDataItemType(@RequestBody DataItemType dataItemType) throws URISyntaxException {
        log.debug("REST request to save DataItemType : {}", dataItemType);
        if (dataItemType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("dataItemType", "idexists", "A new dataItemType cannot already have an ID")).body(null);
        }
        DataItemType result = dataItemTypeRepository.save(dataItemType);
        return ResponseEntity.created(new URI("/api/data-item-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("dataItemType", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /data-item-types : Updates an existing dataItemType.
     *
     * @param dataItemType the dataItemType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dataItemType,
     * or with status 400 (Bad Request) if the dataItemType is not valid,
     * or with status 500 (Internal Server Error) if the dataItemType couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/data-item-types")
    @Timed
    public ResponseEntity<DataItemType> updateDataItemType(@RequestBody DataItemType dataItemType) throws URISyntaxException {
        log.debug("REST request to update DataItemType : {}", dataItemType);
        if (dataItemType.getId() == null) {
            return createDataItemType(dataItemType);
        }
        DataItemType result = dataItemTypeRepository.save(dataItemType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("dataItemType", dataItemType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /data-item-types : get all the dataItemTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of dataItemTypes in body
     */
    @GetMapping("/data-item-types")
    @Timed
    public List<DataItemType> getAllDataItemTypes() {
        log.debug("REST request to get all DataItemTypes");
        List<DataItemType> dataItemTypes = dataItemTypeRepository.findAll();
        return dataItemTypes;
    }

    /**
     * GET  /data-item-types/:id : get the "id" dataItemType.
     *
     * @param id the id of the dataItemType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dataItemType, or with status 404 (Not Found)
     */
    @GetMapping("/data-item-types/{id}")
    @Timed
    public ResponseEntity<DataItemType> getDataItemType(@PathVariable Long id) {
        log.debug("REST request to get DataItemType : {}", id);
        DataItemType dataItemType = dataItemTypeRepository.findOne(id);
        return Optional.ofNullable(dataItemType)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /data-item-types/:id : delete the "id" dataItemType.
     *
     * @param id the id of the dataItemType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/data-item-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteDataItemType(@PathVariable Long id) {
        log.debug("REST request to delete DataItemType : {}", id);
        dataItemTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("dataItemType", id.toString())).build();
    }

}
