package com.datasolution.hire.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.datasolution.hire.domain.EmailTemplate;

import com.datasolution.hire.repository.EmailTemplateRepository;
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
 * REST controller for managing EmailTemplate.
 */
@RestController
@RequestMapping("/api")
public class EmailTemplateResource {

    private final Logger log = LoggerFactory.getLogger(EmailTemplateResource.class);
        
    @Inject
    private EmailTemplateRepository emailTemplateRepository;

    /**
     * POST  /email-templates : Create a new emailTemplate.
     *
     * @param emailTemplate the emailTemplate to create
     * @return the ResponseEntity with status 201 (Created) and with body the new emailTemplate, or with status 400 (Bad Request) if the emailTemplate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/email-templates")
    @Timed
    public ResponseEntity<EmailTemplate> createEmailTemplate(@RequestBody EmailTemplate emailTemplate) throws URISyntaxException {
        log.debug("REST request to save EmailTemplate : {}", emailTemplate);
        if (emailTemplate.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("emailTemplate", "idexists", "A new emailTemplate cannot already have an ID")).body(null);
        }
        EmailTemplate result = emailTemplateRepository.save(emailTemplate);
        return ResponseEntity.created(new URI("/api/email-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("emailTemplate", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /email-templates : Updates an existing emailTemplate.
     *
     * @param emailTemplate the emailTemplate to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated emailTemplate,
     * or with status 400 (Bad Request) if the emailTemplate is not valid,
     * or with status 500 (Internal Server Error) if the emailTemplate couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/email-templates")
    @Timed
    public ResponseEntity<EmailTemplate> updateEmailTemplate(@RequestBody EmailTemplate emailTemplate) throws URISyntaxException {
        log.debug("REST request to update EmailTemplate : {}", emailTemplate);
        if (emailTemplate.getId() == null) {
            return createEmailTemplate(emailTemplate);
        }
        EmailTemplate result = emailTemplateRepository.save(emailTemplate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("emailTemplate", emailTemplate.getId().toString()))
            .body(result);
    }

    /**
     * GET  /email-templates : get all the emailTemplates.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of emailTemplates in body
     */
    @GetMapping("/email-templates")
    @Timed
    public List<EmailTemplate> getAllEmailTemplates() {
        log.debug("REST request to get all EmailTemplates");
        List<EmailTemplate> emailTemplates = emailTemplateRepository.findAll();
        return emailTemplates;
    }

    /**
     * GET  /email-templates/:id : get the "id" emailTemplate.
     *
     * @param id the id of the emailTemplate to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the emailTemplate, or with status 404 (Not Found)
     */
    @GetMapping("/email-templates/{id}")
    @Timed
    public ResponseEntity<EmailTemplate> getEmailTemplate(@PathVariable Long id) {
        log.debug("REST request to get EmailTemplate : {}", id);
        EmailTemplate emailTemplate = emailTemplateRepository.findOne(id);
        return Optional.ofNullable(emailTemplate)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /email-templates/:id : delete the "id" emailTemplate.
     *
     * @param id the id of the emailTemplate to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/email-templates/{id}")
    @Timed
    public ResponseEntity<Void> deleteEmailTemplate(@PathVariable Long id) {
        log.debug("REST request to delete EmailTemplate : {}", id);
        emailTemplateRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("emailTemplate", id.toString())).build();
    }

}
