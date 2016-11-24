package com.datasolution.hire.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.datasolution.hire.domain.CalendarEventType;

import com.datasolution.hire.repository.CalendarEventTypeRepository;
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
 * REST controller for managing CalendarEventType.
 */
@RestController
@RequestMapping("/api")
public class CalendarEventTypeResource {

    private final Logger log = LoggerFactory.getLogger(CalendarEventTypeResource.class);
        
    @Inject
    private CalendarEventTypeRepository calendarEventTypeRepository;

    /**
     * POST  /calendar-event-types : Create a new calendarEventType.
     *
     * @param calendarEventType the calendarEventType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new calendarEventType, or with status 400 (Bad Request) if the calendarEventType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/calendar-event-types")
    @Timed
    public ResponseEntity<CalendarEventType> createCalendarEventType(@RequestBody CalendarEventType calendarEventType) throws URISyntaxException {
        log.debug("REST request to save CalendarEventType : {}", calendarEventType);
        if (calendarEventType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("calendarEventType", "idexists", "A new calendarEventType cannot already have an ID")).body(null);
        }
        CalendarEventType result = calendarEventTypeRepository.save(calendarEventType);
        return ResponseEntity.created(new URI("/api/calendar-event-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("calendarEventType", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /calendar-event-types : Updates an existing calendarEventType.
     *
     * @param calendarEventType the calendarEventType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated calendarEventType,
     * or with status 400 (Bad Request) if the calendarEventType is not valid,
     * or with status 500 (Internal Server Error) if the calendarEventType couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/calendar-event-types")
    @Timed
    public ResponseEntity<CalendarEventType> updateCalendarEventType(@RequestBody CalendarEventType calendarEventType) throws URISyntaxException {
        log.debug("REST request to update CalendarEventType : {}", calendarEventType);
        if (calendarEventType.getId() == null) {
            return createCalendarEventType(calendarEventType);
        }
        CalendarEventType result = calendarEventTypeRepository.save(calendarEventType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("calendarEventType", calendarEventType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /calendar-event-types : get all the calendarEventTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of calendarEventTypes in body
     */
    @GetMapping("/calendar-event-types")
    @Timed
    public List<CalendarEventType> getAllCalendarEventTypes() {
        log.debug("REST request to get all CalendarEventTypes");
        List<CalendarEventType> calendarEventTypes = calendarEventTypeRepository.findAll();
        return calendarEventTypes;
    }

    /**
     * GET  /calendar-event-types/:id : get the "id" calendarEventType.
     *
     * @param id the id of the calendarEventType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the calendarEventType, or with status 404 (Not Found)
     */
    @GetMapping("/calendar-event-types/{id}")
    @Timed
    public ResponseEntity<CalendarEventType> getCalendarEventType(@PathVariable Long id) {
        log.debug("REST request to get CalendarEventType : {}", id);
        CalendarEventType calendarEventType = calendarEventTypeRepository.findOne(id);
        return Optional.ofNullable(calendarEventType)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /calendar-event-types/:id : delete the "id" calendarEventType.
     *
     * @param id the id of the calendarEventType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/calendar-event-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteCalendarEventType(@PathVariable Long id) {
        log.debug("REST request to delete CalendarEventType : {}", id);
        calendarEventTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("calendarEventType", id.toString())).build();
    }

}
