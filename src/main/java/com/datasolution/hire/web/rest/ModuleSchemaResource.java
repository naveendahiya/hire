package com.datasolution.hire.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.datasolution.hire.domain.ModuleSchema;

import com.datasolution.hire.repository.ModuleSchemaRepository;
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
 * REST controller for managing ModuleSchema.
 */
@RestController
@RequestMapping("/api")
public class ModuleSchemaResource {

    private final Logger log = LoggerFactory.getLogger(ModuleSchemaResource.class);
        
    @Inject
    private ModuleSchemaRepository moduleSchemaRepository;

    /**
     * POST  /module-schemas : Create a new moduleSchema.
     *
     * @param moduleSchema the moduleSchema to create
     * @return the ResponseEntity with status 201 (Created) and with body the new moduleSchema, or with status 400 (Bad Request) if the moduleSchema has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/module-schemas")
    @Timed
    public ResponseEntity<ModuleSchema> createModuleSchema(@RequestBody ModuleSchema moduleSchema) throws URISyntaxException {
        log.debug("REST request to save ModuleSchema : {}", moduleSchema);
        if (moduleSchema.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("moduleSchema", "idexists", "A new moduleSchema cannot already have an ID")).body(null);
        }
        ModuleSchema result = moduleSchemaRepository.save(moduleSchema);
        return ResponseEntity.created(new URI("/api/module-schemas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("moduleSchema", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /module-schemas : Updates an existing moduleSchema.
     *
     * @param moduleSchema the moduleSchema to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated moduleSchema,
     * or with status 400 (Bad Request) if the moduleSchema is not valid,
     * or with status 500 (Internal Server Error) if the moduleSchema couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/module-schemas")
    @Timed
    public ResponseEntity<ModuleSchema> updateModuleSchema(@RequestBody ModuleSchema moduleSchema) throws URISyntaxException {
        log.debug("REST request to update ModuleSchema : {}", moduleSchema);
        if (moduleSchema.getId() == null) {
            return createModuleSchema(moduleSchema);
        }
        ModuleSchema result = moduleSchemaRepository.save(moduleSchema);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("moduleSchema", moduleSchema.getId().toString()))
            .body(result);
    }

    /**
     * GET  /module-schemas : get all the moduleSchemas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of moduleSchemas in body
     */
    @GetMapping("/module-schemas")
    @Timed
    public List<ModuleSchema> getAllModuleSchemas() {
        log.debug("REST request to get all ModuleSchemas");
        List<ModuleSchema> moduleSchemas = moduleSchemaRepository.findAll();
        return moduleSchemas;
    }

    /**
     * GET  /module-schemas/:id : get the "id" moduleSchema.
     *
     * @param id the id of the moduleSchema to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the moduleSchema, or with status 404 (Not Found)
     */
    @GetMapping("/module-schemas/{id}")
    @Timed
    public ResponseEntity<ModuleSchema> getModuleSchema(@PathVariable Long id) {
        log.debug("REST request to get ModuleSchema : {}", id);
        ModuleSchema moduleSchema = moduleSchemaRepository.findOne(id);
        return Optional.ofNullable(moduleSchema)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /module-schemas/:id : delete the "id" moduleSchema.
     *
     * @param id the id of the moduleSchema to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/module-schemas/{id}")
    @Timed
    public ResponseEntity<Void> deleteModuleSchema(@PathVariable Long id) {
        log.debug("REST request to delete ModuleSchema : {}", id);
        moduleSchemaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("moduleSchema", id.toString())).build();
    }

}
