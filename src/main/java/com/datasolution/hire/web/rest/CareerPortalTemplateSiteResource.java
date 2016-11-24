package com.datasolution.hire.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.datasolution.hire.domain.CareerPortalTemplateSite;

import com.datasolution.hire.repository.CareerPortalTemplateSiteRepository;
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
 * REST controller for managing CareerPortalTemplateSite.
 */
@RestController
@RequestMapping("/api")
public class CareerPortalTemplateSiteResource {

    private final Logger log = LoggerFactory.getLogger(CareerPortalTemplateSiteResource.class);
        
    @Inject
    private CareerPortalTemplateSiteRepository careerPortalTemplateSiteRepository;

    /**
     * POST  /career-portal-template-sites : Create a new careerPortalTemplateSite.
     *
     * @param careerPortalTemplateSite the careerPortalTemplateSite to create
     * @return the ResponseEntity with status 201 (Created) and with body the new careerPortalTemplateSite, or with status 400 (Bad Request) if the careerPortalTemplateSite has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/career-portal-template-sites")
    @Timed
    public ResponseEntity<CareerPortalTemplateSite> createCareerPortalTemplateSite(@RequestBody CareerPortalTemplateSite careerPortalTemplateSite) throws URISyntaxException {
        log.debug("REST request to save CareerPortalTemplateSite : {}", careerPortalTemplateSite);
        if (careerPortalTemplateSite.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("careerPortalTemplateSite", "idexists", "A new careerPortalTemplateSite cannot already have an ID")).body(null);
        }
        CareerPortalTemplateSite result = careerPortalTemplateSiteRepository.save(careerPortalTemplateSite);
        return ResponseEntity.created(new URI("/api/career-portal-template-sites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("careerPortalTemplateSite", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /career-portal-template-sites : Updates an existing careerPortalTemplateSite.
     *
     * @param careerPortalTemplateSite the careerPortalTemplateSite to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated careerPortalTemplateSite,
     * or with status 400 (Bad Request) if the careerPortalTemplateSite is not valid,
     * or with status 500 (Internal Server Error) if the careerPortalTemplateSite couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/career-portal-template-sites")
    @Timed
    public ResponseEntity<CareerPortalTemplateSite> updateCareerPortalTemplateSite(@RequestBody CareerPortalTemplateSite careerPortalTemplateSite) throws URISyntaxException {
        log.debug("REST request to update CareerPortalTemplateSite : {}", careerPortalTemplateSite);
        if (careerPortalTemplateSite.getId() == null) {
            return createCareerPortalTemplateSite(careerPortalTemplateSite);
        }
        CareerPortalTemplateSite result = careerPortalTemplateSiteRepository.save(careerPortalTemplateSite);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("careerPortalTemplateSite", careerPortalTemplateSite.getId().toString()))
            .body(result);
    }

    /**
     * GET  /career-portal-template-sites : get all the careerPortalTemplateSites.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of careerPortalTemplateSites in body
     */
    @GetMapping("/career-portal-template-sites")
    @Timed
    public List<CareerPortalTemplateSite> getAllCareerPortalTemplateSites() {
        log.debug("REST request to get all CareerPortalTemplateSites");
        List<CareerPortalTemplateSite> careerPortalTemplateSites = careerPortalTemplateSiteRepository.findAll();
        return careerPortalTemplateSites;
    }

    /**
     * GET  /career-portal-template-sites/:id : get the "id" careerPortalTemplateSite.
     *
     * @param id the id of the careerPortalTemplateSite to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the careerPortalTemplateSite, or with status 404 (Not Found)
     */
    @GetMapping("/career-portal-template-sites/{id}")
    @Timed
    public ResponseEntity<CareerPortalTemplateSite> getCareerPortalTemplateSite(@PathVariable Long id) {
        log.debug("REST request to get CareerPortalTemplateSite : {}", id);
        CareerPortalTemplateSite careerPortalTemplateSite = careerPortalTemplateSiteRepository.findOne(id);
        return Optional.ofNullable(careerPortalTemplateSite)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /career-portal-template-sites/:id : delete the "id" careerPortalTemplateSite.
     *
     * @param id the id of the careerPortalTemplateSite to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/career-portal-template-sites/{id}")
    @Timed
    public ResponseEntity<Void> deleteCareerPortalTemplateSite(@PathVariable Long id) {
        log.debug("REST request to delete CareerPortalTemplateSite : {}", id);
        careerPortalTemplateSiteRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("careerPortalTemplateSite", id.toString())).build();
    }

}
