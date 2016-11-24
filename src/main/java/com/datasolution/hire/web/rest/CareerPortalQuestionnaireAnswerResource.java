package com.datasolution.hire.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.datasolution.hire.domain.CareerPortalQuestionnaireAnswer;

import com.datasolution.hire.repository.CareerPortalQuestionnaireAnswerRepository;
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
 * REST controller for managing CareerPortalQuestionnaireAnswer.
 */
@RestController
@RequestMapping("/api")
public class CareerPortalQuestionnaireAnswerResource {

    private final Logger log = LoggerFactory.getLogger(CareerPortalQuestionnaireAnswerResource.class);
        
    @Inject
    private CareerPortalQuestionnaireAnswerRepository careerPortalQuestionnaireAnswerRepository;

    /**
     * POST  /career-portal-questionnaire-answers : Create a new careerPortalQuestionnaireAnswer.
     *
     * @param careerPortalQuestionnaireAnswer the careerPortalQuestionnaireAnswer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new careerPortalQuestionnaireAnswer, or with status 400 (Bad Request) if the careerPortalQuestionnaireAnswer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/career-portal-questionnaire-answers")
    @Timed
    public ResponseEntity<CareerPortalQuestionnaireAnswer> createCareerPortalQuestionnaireAnswer(@RequestBody CareerPortalQuestionnaireAnswer careerPortalQuestionnaireAnswer) throws URISyntaxException {
        log.debug("REST request to save CareerPortalQuestionnaireAnswer : {}", careerPortalQuestionnaireAnswer);
        if (careerPortalQuestionnaireAnswer.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("careerPortalQuestionnaireAnswer", "idexists", "A new careerPortalQuestionnaireAnswer cannot already have an ID")).body(null);
        }
        CareerPortalQuestionnaireAnswer result = careerPortalQuestionnaireAnswerRepository.save(careerPortalQuestionnaireAnswer);
        return ResponseEntity.created(new URI("/api/career-portal-questionnaire-answers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("careerPortalQuestionnaireAnswer", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /career-portal-questionnaire-answers : Updates an existing careerPortalQuestionnaireAnswer.
     *
     * @param careerPortalQuestionnaireAnswer the careerPortalQuestionnaireAnswer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated careerPortalQuestionnaireAnswer,
     * or with status 400 (Bad Request) if the careerPortalQuestionnaireAnswer is not valid,
     * or with status 500 (Internal Server Error) if the careerPortalQuestionnaireAnswer couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/career-portal-questionnaire-answers")
    @Timed
    public ResponseEntity<CareerPortalQuestionnaireAnswer> updateCareerPortalQuestionnaireAnswer(@RequestBody CareerPortalQuestionnaireAnswer careerPortalQuestionnaireAnswer) throws URISyntaxException {
        log.debug("REST request to update CareerPortalQuestionnaireAnswer : {}", careerPortalQuestionnaireAnswer);
        if (careerPortalQuestionnaireAnswer.getId() == null) {
            return createCareerPortalQuestionnaireAnswer(careerPortalQuestionnaireAnswer);
        }
        CareerPortalQuestionnaireAnswer result = careerPortalQuestionnaireAnswerRepository.save(careerPortalQuestionnaireAnswer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("careerPortalQuestionnaireAnswer", careerPortalQuestionnaireAnswer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /career-portal-questionnaire-answers : get all the careerPortalQuestionnaireAnswers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of careerPortalQuestionnaireAnswers in body
     */
    @GetMapping("/career-portal-questionnaire-answers")
    @Timed
    public List<CareerPortalQuestionnaireAnswer> getAllCareerPortalQuestionnaireAnswers() {
        log.debug("REST request to get all CareerPortalQuestionnaireAnswers");
        List<CareerPortalQuestionnaireAnswer> careerPortalQuestionnaireAnswers = careerPortalQuestionnaireAnswerRepository.findAll();
        return careerPortalQuestionnaireAnswers;
    }

    /**
     * GET  /career-portal-questionnaire-answers/:id : get the "id" careerPortalQuestionnaireAnswer.
     *
     * @param id the id of the careerPortalQuestionnaireAnswer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the careerPortalQuestionnaireAnswer, or with status 404 (Not Found)
     */
    @GetMapping("/career-portal-questionnaire-answers/{id}")
    @Timed
    public ResponseEntity<CareerPortalQuestionnaireAnswer> getCareerPortalQuestionnaireAnswer(@PathVariable Long id) {
        log.debug("REST request to get CareerPortalQuestionnaireAnswer : {}", id);
        CareerPortalQuestionnaireAnswer careerPortalQuestionnaireAnswer = careerPortalQuestionnaireAnswerRepository.findOne(id);
        return Optional.ofNullable(careerPortalQuestionnaireAnswer)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /career-portal-questionnaire-answers/:id : delete the "id" careerPortalQuestionnaireAnswer.
     *
     * @param id the id of the careerPortalQuestionnaireAnswer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/career-portal-questionnaire-answers/{id}")
    @Timed
    public ResponseEntity<Void> deleteCareerPortalQuestionnaireAnswer(@PathVariable Long id) {
        log.debug("REST request to delete CareerPortalQuestionnaireAnswer : {}", id);
        careerPortalQuestionnaireAnswerRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("careerPortalQuestionnaireAnswer", id.toString())).build();
    }

}
