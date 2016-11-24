package com.datasolution.hire.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.datasolution.hire.domain.CareerPortalQuestionnaireHistory;

import com.datasolution.hire.repository.CareerPortalQuestionnaireHistoryRepository;
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
 * REST controller for managing CareerPortalQuestionnaireHistory.
 */
@RestController
@RequestMapping("/api")
public class CareerPortalQuestionnaireHistoryResource {

    private final Logger log = LoggerFactory.getLogger(CareerPortalQuestionnaireHistoryResource.class);
        
    @Inject
    private CareerPortalQuestionnaireHistoryRepository careerPortalQuestionnaireHistoryRepository;

    /**
     * POST  /career-portal-questionnaire-histories : Create a new careerPortalQuestionnaireHistory.
     *
     * @param careerPortalQuestionnaireHistory the careerPortalQuestionnaireHistory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new careerPortalQuestionnaireHistory, or with status 400 (Bad Request) if the careerPortalQuestionnaireHistory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/career-portal-questionnaire-histories")
    @Timed
    public ResponseEntity<CareerPortalQuestionnaireHistory> createCareerPortalQuestionnaireHistory(@RequestBody CareerPortalQuestionnaireHistory careerPortalQuestionnaireHistory) throws URISyntaxException {
        log.debug("REST request to save CareerPortalQuestionnaireHistory : {}", careerPortalQuestionnaireHistory);
        if (careerPortalQuestionnaireHistory.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("careerPortalQuestionnaireHistory", "idexists", "A new careerPortalQuestionnaireHistory cannot already have an ID")).body(null);
        }
        CareerPortalQuestionnaireHistory result = careerPortalQuestionnaireHistoryRepository.save(careerPortalQuestionnaireHistory);
        return ResponseEntity.created(new URI("/api/career-portal-questionnaire-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("careerPortalQuestionnaireHistory", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /career-portal-questionnaire-histories : Updates an existing careerPortalQuestionnaireHistory.
     *
     * @param careerPortalQuestionnaireHistory the careerPortalQuestionnaireHistory to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated careerPortalQuestionnaireHistory,
     * or with status 400 (Bad Request) if the careerPortalQuestionnaireHistory is not valid,
     * or with status 500 (Internal Server Error) if the careerPortalQuestionnaireHistory couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/career-portal-questionnaire-histories")
    @Timed
    public ResponseEntity<CareerPortalQuestionnaireHistory> updateCareerPortalQuestionnaireHistory(@RequestBody CareerPortalQuestionnaireHistory careerPortalQuestionnaireHistory) throws URISyntaxException {
        log.debug("REST request to update CareerPortalQuestionnaireHistory : {}", careerPortalQuestionnaireHistory);
        if (careerPortalQuestionnaireHistory.getId() == null) {
            return createCareerPortalQuestionnaireHistory(careerPortalQuestionnaireHistory);
        }
        CareerPortalQuestionnaireHistory result = careerPortalQuestionnaireHistoryRepository.save(careerPortalQuestionnaireHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("careerPortalQuestionnaireHistory", careerPortalQuestionnaireHistory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /career-portal-questionnaire-histories : get all the careerPortalQuestionnaireHistories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of careerPortalQuestionnaireHistories in body
     */
    @GetMapping("/career-portal-questionnaire-histories")
    @Timed
    public List<CareerPortalQuestionnaireHistory> getAllCareerPortalQuestionnaireHistories() {
        log.debug("REST request to get all CareerPortalQuestionnaireHistories");
        List<CareerPortalQuestionnaireHistory> careerPortalQuestionnaireHistories = careerPortalQuestionnaireHistoryRepository.findAll();
        return careerPortalQuestionnaireHistories;
    }

    /**
     * GET  /career-portal-questionnaire-histories/:id : get the "id" careerPortalQuestionnaireHistory.
     *
     * @param id the id of the careerPortalQuestionnaireHistory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the careerPortalQuestionnaireHistory, or with status 404 (Not Found)
     */
    @GetMapping("/career-portal-questionnaire-histories/{id}")
    @Timed
    public ResponseEntity<CareerPortalQuestionnaireHistory> getCareerPortalQuestionnaireHistory(@PathVariable Long id) {
        log.debug("REST request to get CareerPortalQuestionnaireHistory : {}", id);
        CareerPortalQuestionnaireHistory careerPortalQuestionnaireHistory = careerPortalQuestionnaireHistoryRepository.findOne(id);
        return Optional.ofNullable(careerPortalQuestionnaireHistory)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /career-portal-questionnaire-histories/:id : delete the "id" careerPortalQuestionnaireHistory.
     *
     * @param id the id of the careerPortalQuestionnaireHistory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/career-portal-questionnaire-histories/{id}")
    @Timed
    public ResponseEntity<Void> deleteCareerPortalQuestionnaireHistory(@PathVariable Long id) {
        log.debug("REST request to delete CareerPortalQuestionnaireHistory : {}", id);
        careerPortalQuestionnaireHistoryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("careerPortalQuestionnaireHistory", id.toString())).build();
    }

}
