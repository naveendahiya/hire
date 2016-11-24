package com.datasolution.hire.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.datasolution.hire.domain.CareerPortalTemplate;

import com.datasolution.hire.repository.CareerPortalTemplateRepository;
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
 * REST controller for managing CareerPortalTemplate.
 */
@RestController
@RequestMapping("/api")
public class CareerPortalTemplateResource {

    private final Logger log = LoggerFactory.getLogger(CareerPortalTemplateResource.class);
        
    @Inject
    private CareerPortalTemplateRepository careerPortalTemplateRepository;

    /**
     * POST  /career-portal-templates : Create a new careerPortalTemplate.
     *
     * @param careerPortalTemplate the careerPortalTemplate to create
     * @return the ResponseEntity with status 201 (Created) and with body the new careerPortalTemplate, or with status 400 (Bad Request) if the careerPortalTemplate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/career-portal-templates")
    @Timed
    public ResponseEntity<CareerPortalTemplate> createCareerPortalTemplate(@RequestBody CareerPortalTemplate careerPortalTemplate) throws URISyntaxException {
        log.debug("REST request to save CareerPortalTemplate : {}", careerPortalTemplate);
        if (careerPortalTemplate.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("careerPortalTemplate", "idexists", "A new careerPortalTemplate cannot already have an ID")).body(null);
        }
        CareerPortalTemplate result = careerPortalTemplateRepository.save(careerPortalTemplate);
        return ResponseEntity.created(new URI("/api/career-portal-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("careerPortalTemplate", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /career-portal-templates : Updates an existing careerPortalTemplate.
     *
     * @param careerPortalTemplate the careerPortalTemplate to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated careerPortalTemplate,
     * or with status 400 (Bad Request) if the careerPortalTemplate is not valid,
     * or with status 500 (Internal Server Error) if the careerPortalTemplate couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/career-portal-templates")
    @Timed
    public ResponseEntity<CareerPortalTemplate> updateCareerPortalTemplate(@RequestBody CareerPortalTemplate careerPortalTemplate) throws URISyntaxException {
        log.debug("REST request to update CareerPortalTemplate : {}", careerPortalTemplate);
        if (careerPortalTemplate.getId() == null) {
            return createCareerPortalTemplate(careerPortalTemplate);
        }
        CareerPortalTemplate result = careerPortalTemplateRepository.save(careerPortalTemplate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("careerPortalTemplate", careerPortalTemplate.getId().toString()))
            .body(result);
    }

    /**
     * GET  /career-portal-templates : get all the careerPortalTemplates.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of careerPortalTemplates in body
     */
    @GetMapping("/career-portal-templates")
    @Timed
    public List<CareerPortalTemplate> getAllCareerPortalTemplates() {
        log.debug("REST request to get all CareerPortalTemplates");
        List<CareerPortalTemplate> careerPortalTemplates = careerPortalTemplateRepository.findAll();
        return careerPortalTemplates;
    }

    /**
     * GET  /career-portal-templates/:id : get the "id" careerPortalTemplate.
     *
     * @param id the id of the careerPortalTemplate to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the careerPortalTemplate, or with status 404 (Not Found)
     */
    @GetMapping("/career-portal-templates/{id}")
    @Timed
    public ResponseEntity<CareerPortalTemplate> getCareerPortalTemplate(@PathVariable Long id) {
        log.debug("REST request to get CareerPortalTemplate : {}", id);
        CareerPortalTemplate careerPortalTemplate = careerPortalTemplateRepository.findOne(id);
        return Optional.ofNullable(careerPortalTemplate)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /career-portal-templates/:id : delete the "id" careerPortalTemplate.
     *
     * @param id the id of the careerPortalTemplate to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/career-portal-templates/{id}")
    @Timed
    public ResponseEntity<Void> deleteCareerPortalTemplate(@PathVariable Long id) {
        log.debug("REST request to delete CareerPortalTemplate : {}", id);
        careerPortalTemplateRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("careerPortalTemplate", id.toString())).build();
    }

}
