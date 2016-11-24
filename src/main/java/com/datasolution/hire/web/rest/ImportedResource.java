package com.datasolution.hire.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.datasolution.hire.domain.Imported;

import com.datasolution.hire.repository.ImportedRepository;
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
 * REST controller for managing Imported.
 */
@RestController
@RequestMapping("/api")
public class ImportedResource {

    private final Logger log = LoggerFactory.getLogger(ImportedResource.class);
        
    @Inject
    private ImportedRepository importedRepository;

    /**
     * POST  /importeds : Create a new imported.
     *
     * @param imported the imported to create
     * @return the ResponseEntity with status 201 (Created) and with body the new imported, or with status 400 (Bad Request) if the imported has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/importeds")
    @Timed
    public ResponseEntity<Imported> createImported(@RequestBody Imported imported) throws URISyntaxException {
        log.debug("REST request to save Imported : {}", imported);
        if (imported.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("imported", "idexists", "A new imported cannot already have an ID")).body(null);
        }
        Imported result = importedRepository.save(imported);
        return ResponseEntity.created(new URI("/api/importeds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("imported", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /importeds : Updates an existing imported.
     *
     * @param imported the imported to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated imported,
     * or with status 400 (Bad Request) if the imported is not valid,
     * or with status 500 (Internal Server Error) if the imported couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/importeds")
    @Timed
    public ResponseEntity<Imported> updateImported(@RequestBody Imported imported) throws URISyntaxException {
        log.debug("REST request to update Imported : {}", imported);
        if (imported.getId() == null) {
            return createImported(imported);
        }
        Imported result = importedRepository.save(imported);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("imported", imported.getId().toString()))
            .body(result);
    }

    /**
     * GET  /importeds : get all the importeds.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of importeds in body
     */
    @GetMapping("/importeds")
    @Timed
    public List<Imported> getAllImporteds() {
        log.debug("REST request to get all Importeds");
        List<Imported> importeds = importedRepository.findAll();
        return importeds;
    }

    /**
     * GET  /importeds/:id : get the "id" imported.
     *
     * @param id the id of the imported to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the imported, or with status 404 (Not Found)
     */
    @GetMapping("/importeds/{id}")
    @Timed
    public ResponseEntity<Imported> getImported(@PathVariable Long id) {
        log.debug("REST request to get Imported : {}", id);
        Imported imported = importedRepository.findOne(id);
        return Optional.ofNullable(imported)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /importeds/:id : delete the "id" imported.
     *
     * @param id the id of the imported to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/importeds/{id}")
    @Timed
    public ResponseEntity<Void> deleteImported(@PathVariable Long id) {
        log.debug("REST request to delete Imported : {}", id);
        importedRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("imported", id.toString())).build();
    }

}
