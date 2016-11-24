package com.datasolution.hire.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.datasolution.hire.domain.CareerPortalQuestionnaire;

import com.datasolution.hire.repository.CareerPortalQuestionnaireRepository;
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
 * REST controller for managing CareerPortalQuestionnaire.
 */
@RestController
@RequestMapping("/api")
public class CareerPortalQuestionnaireResource {

    private final Logger log = LoggerFactory.getLogger(CareerPortalQuestionnaireResource.class);
        
    @Inject
    private CareerPortalQuestionnaireRepository careerPortalQuestionnaireRepository;

    /**
     * POST  /career-portal-questionnaires : Create a new careerPortalQuestionnaire.
     *
     * @param careerPortalQuestionnaire the careerPortalQuestionnaire to create
     * @return the ResponseEntity with status 201 (Created) and with body the new careerPortalQuestionnaire, or with status 400 (Bad Request) if the careerPortalQuestionnaire has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/career-portal-questionnaires")
    @Timed
    public ResponseEntity<CareerPortalQuestionnaire> createCareerPortalQuestionnaire(@RequestBody CareerPortalQuestionnaire careerPortalQuestionnaire) throws URISyntaxException {
        log.debug("REST request to save CareerPortalQuestionnaire : {}", careerPortalQuestionnaire);
        if (careerPortalQuestionnaire.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("careerPortalQuestionnaire", "idexists", "A new careerPortalQuestionnaire cannot already have an ID")).body(null);
        }
        CareerPortalQuestionnaire result = careerPortalQuestionnaireRepository.save(careerPortalQuestionnaire);
        return ResponseEntity.created(new URI("/api/career-portal-questionnaires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("careerPortalQuestionnaire", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /career-portal-questionnaires : Updates an existing careerPortalQuestionnaire.
     *
     * @param careerPortalQuestionnaire the careerPortalQuestionnaire to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated careerPortalQuestionnaire,
     * or with status 400 (Bad Request) if the careerPortalQuestionnaire is not valid,
     * or with status 500 (Internal Server Error) if the careerPortalQuestionnaire couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/career-portal-questionnaires")
    @Timed
    public ResponseEntity<CareerPortalQuestionnaire> updateCareerPortalQuestionnaire(@RequestBody CareerPortalQuestionnaire careerPortalQuestionnaire) throws URISyntaxException {
        log.debug("REST request to update CareerPortalQuestionnaire : {}", careerPortalQuestionnaire);
        if (careerPortalQuestionnaire.getId() == null) {
            return createCareerPortalQuestionnaire(careerPortalQuestionnaire);
        }
        CareerPortalQuestionnaire result = careerPortalQuestionnaireRepository.save(careerPortalQuestionnaire);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("careerPortalQuestionnaire", careerPortalQuestionnaire.getId().toString()))
            .body(result);
    }

    /**
     * GET  /career-portal-questionnaires : get all the careerPortalQuestionnaires.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of careerPortalQuestionnaires in body
     */
    @GetMapping("/career-portal-questionnaires")
    @Timed
    public List<CareerPortalQuestionnaire> getAllCareerPortalQuestionnaires() {
        log.debug("REST request to get all CareerPortalQuestionnaires");
        List<CareerPortalQuestionnaire> careerPortalQuestionnaires = careerPortalQuestionnaireRepository.findAll();
        return careerPortalQuestionnaires;
    }

    /**
     * GET  /career-portal-questionnaires/:id : get the "id" careerPortalQuestionnaire.
     *
     * @param id the id of the careerPortalQuestionnaire to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the careerPortalQuestionnaire, or with status 404 (Not Found)
     */
    @GetMapping("/career-portal-questionnaires/{id}")
    @Timed
    public ResponseEntity<CareerPortalQuestionnaire> getCareerPortalQuestionnaire(@PathVariable Long id) {
        log.debug("REST request to get CareerPortalQuestionnaire : {}", id);
        CareerPortalQuestionnaire careerPortalQuestionnaire = careerPortalQuestionnaireRepository.findOne(id);
        return Optional.ofNullable(careerPortalQuestionnaire)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /career-portal-questionnaires/:id : delete the "id" careerPortalQuestionnaire.
     *
     * @param id the id of the careerPortalQuestionnaire to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/career-portal-questionnaires/{id}")
    @Timed
    public ResponseEntity<Void> deleteCareerPortalQuestionnaire(@PathVariable Long id) {
        log.debug("REST request to delete CareerPortalQuestionnaire : {}", id);
        careerPortalQuestionnaireRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("careerPortalQuestionnaire", id.toString())).build();
    }

}
