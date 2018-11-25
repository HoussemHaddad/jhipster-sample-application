package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.TypeDeNotification;
import io.github.jhipster.application.repository.TypeDeNotificationRepository;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TypeDeNotification.
 */
@RestController
@RequestMapping("/api")
public class TypeDeNotificationResource {

    private final Logger log = LoggerFactory.getLogger(TypeDeNotificationResource.class);

    private static final String ENTITY_NAME = "jhipsterSampleApplicationTypeDeNotification";

    private final TypeDeNotificationRepository typeDeNotificationRepository;

    public TypeDeNotificationResource(TypeDeNotificationRepository typeDeNotificationRepository) {
        this.typeDeNotificationRepository = typeDeNotificationRepository;
    }

    /**
     * POST  /type-de-notifications : Create a new typeDeNotification.
     *
     * @param typeDeNotification the typeDeNotification to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeDeNotification, or with status 400 (Bad Request) if the typeDeNotification has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-de-notifications")
    @Timed
    public ResponseEntity<TypeDeNotification> createTypeDeNotification(@RequestBody TypeDeNotification typeDeNotification) throws URISyntaxException {
        log.debug("REST request to save TypeDeNotification : {}", typeDeNotification);
        if (typeDeNotification.getId() != null) {
            throw new BadRequestAlertException("A new typeDeNotification cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeDeNotification result = typeDeNotificationRepository.save(typeDeNotification);
        return ResponseEntity.created(new URI("/api/type-de-notifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-de-notifications : Updates an existing typeDeNotification.
     *
     * @param typeDeNotification the typeDeNotification to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeDeNotification,
     * or with status 400 (Bad Request) if the typeDeNotification is not valid,
     * or with status 500 (Internal Server Error) if the typeDeNotification couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-de-notifications")
    @Timed
    public ResponseEntity<TypeDeNotification> updateTypeDeNotification(@RequestBody TypeDeNotification typeDeNotification) throws URISyntaxException {
        log.debug("REST request to update TypeDeNotification : {}", typeDeNotification);
        if (typeDeNotification.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeDeNotification result = typeDeNotificationRepository.save(typeDeNotification);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, typeDeNotification.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-de-notifications : get all the typeDeNotifications.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of typeDeNotifications in body
     */
    @GetMapping("/type-de-notifications")
    @Timed
    public List<TypeDeNotification> getAllTypeDeNotifications() {
        log.debug("REST request to get all TypeDeNotifications");
        return typeDeNotificationRepository.findAll();
    }

    /**
     * GET  /type-de-notifications/:id : get the "id" typeDeNotification.
     *
     * @param id the id of the typeDeNotification to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeDeNotification, or with status 404 (Not Found)
     */
    @GetMapping("/type-de-notifications/{id}")
    @Timed
    public ResponseEntity<TypeDeNotification> getTypeDeNotification(@PathVariable Long id) {
        log.debug("REST request to get TypeDeNotification : {}", id);
        Optional<TypeDeNotification> typeDeNotification = typeDeNotificationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(typeDeNotification);
    }

    /**
     * DELETE  /type-de-notifications/:id : delete the "id" typeDeNotification.
     *
     * @param id the id of the typeDeNotification to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-de-notifications/{id}")
    @Timed
    public ResponseEntity<Void> deleteTypeDeNotification(@PathVariable Long id) {
        log.debug("REST request to delete TypeDeNotification : {}", id);

        typeDeNotificationRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
