package com.datasolution.hire.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.datasolution.hire.domain.CareerPortalQuestionnaireQuestion;

import com.datasolution.hire.repository.CareerPortalQuestionnaireQuestionRepository;
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
 * REST controller for managing CareerPortalQuestionnaireQuestion.
 */
@RestController
@RequestMapping("/api")
public class CareerPortalQuestionnaireQuestionResource {

    private final Logger log = LoggerFactory.getLogger(CareerPortalQuestionnaireQuestionResource.class);
        
    @Inject
    private CareerPortalQuestionnaireQuestionRepository careerPortalQuestionnaireQuestionRepository;

    /**
     * POST  /career-portal-questionnaire-questions : Create a new careerPortalQuestionnaireQuestion.
     *
     * @param careerPortalQuestionnaireQuestion the careerPortalQuestionnaireQuestion to create
     * @return the ResponseEntity with status 201 (Created) and with body the new careerPortalQuestionnaireQuestion, or with status 400 (Bad Request) if the careerPortalQuestionnaireQuestion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/career-portal-questionnaire-questions")
    @Timed
    public ResponseEntity<CareerPortalQuestionnaireQuestion> createCareerPortalQuestionnaireQuestion(@RequestBody CareerPortalQuestionnaireQuestion careerPortalQuestionnaireQuestion) throws URISyntaxException {
        log.debug("REST request to save CareerPortalQuestionnaireQuestion : {}", careerPortalQuestionnaireQuestion);
        if (careerPortalQuestionnaireQuestion.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("careerPortalQuestionnaireQuestion", "idexists", "A new careerPortalQuestionnaireQuestion cannot already have an ID")).body(null);
        }
        CareerPortalQuestionnaireQuestion result = careerPortalQuestionnaireQuestionRepository.save(careerPortalQuestionnaireQuestion);
        return ResponseEntity.created(new URI("/api/career-portal-questionnaire-questions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("careerPortalQuestionnaireQuestion", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /career-portal-questionnaire-questions : Updates an existing careerPortalQuestionnaireQuestion.
     *
     * @param careerPortalQuestionnaireQuestion the careerPortalQuestionnaireQuestion to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated careerPortalQuestionnaireQuestion,
     * or with status 400 (Bad Request) if the careerPortalQuestionnaireQuestion is not valid,
     * or with status 500 (Internal Server Error) if the careerPortalQuestionnaireQuestion couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/career-portal-questionnaire-questions")
    @Timed
    public ResponseEntity<CareerPortalQuestionnaireQuestion> updateCareerPortalQuestionnaireQuestion(@RequestBody CareerPortalQuestionnaireQuestion careerPortalQuestionnaireQuestion) throws URISyntaxException {
        log.debug("REST request to update CareerPortalQuestionnaireQuestion : {}", careerPortalQuestionnaireQuestion);
        if (careerPortalQuestionnaireQuestion.getId() == null) {
            return createCareerPortalQuestionnaireQuestion(careerPortalQuestionnaireQuestion);
        }
        CareerPortalQuestionnaireQuestion result = careerPortalQuestionnaireQuestionRepository.save(careerPortalQuestionnaireQuestion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("careerPortalQuestionnaireQuestion", careerPortalQuestionnaireQuestion.getId().toString()))
            .body(result);
    }

    /**
     * GET  /career-portal-questionnaire-questions : get all the careerPortalQuestionnaireQuestions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of careerPortalQuestionnaireQuestions in body
     */
    @GetMapping("/career-portal-questionnaire-questions")
    @Timed
    public List<CareerPortalQuestionnaireQuestion> getAllCareerPortalQuestionnaireQuestions() {
        log.debug("REST request to get all CareerPortalQuestionnaireQuestions");
        List<CareerPortalQuestionnaireQuestion> careerPortalQuestionnaireQuestions = careerPortalQuestionnaireQuestionRepository.findAll();
        return careerPortalQuestionnaireQuestions;
    }

    /**
     * GET  /career-portal-questionnaire-questions/:id : get the "id" careerPortalQuestionnaireQuestion.
     *
     * @param id the id of the careerPortalQuestionnaireQuestion to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the careerPortalQuestionnaireQuestion, or with status 404 (Not Found)
     */
    @GetMapping("/career-portal-questionnaire-questions/{id}")
    @Timed
    public ResponseEntity<CareerPortalQuestionnaireQuestion> getCareerPortalQuestionnaireQuestion(@PathVariable Long id) {
        log.debug("REST request to get CareerPortalQuestionnaireQuestion : {}", id);
        CareerPortalQuestionnaireQuestion careerPortalQuestionnaireQuestion = careerPortalQuestionnaireQuestionRepository.findOne(id);
        return Optional.ofNullable(careerPortalQuestionnaireQuestion)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /career-portal-questionnaire-questions/:id : delete the "id" careerPortalQuestionnaireQuestion.
     *
     * @param id the id of the careerPortalQuestionnaireQuestion to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/career-portal-questionnaire-questions/{id}")
    @Timed
    public ResponseEntity<Void> deleteCareerPortalQuestionnaireQuestion(@PathVariable Long id) {
        log.debug("REST request to delete CareerPortalQuestionnaireQuestion : {}", id);
        careerPortalQuestionnaireQuestionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("careerPortalQuestionnaireQuestion", id.toString())).build();
    }

}
