package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.AutresInformations;
import io.github.jhipster.application.repository.AutresInformationsRepository;
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
 * REST controller for managing AutresInformations.
 */
@RestController
@RequestMapping("/api")
public class AutresInformationsResource {

    private final Logger log = LoggerFactory.getLogger(AutresInformationsResource.class);

    private static final String ENTITY_NAME = "jhipsterSampleApplicationAutresInformations";

    private final AutresInformationsRepository autresInformationsRepository;

    public AutresInformationsResource(AutresInformationsRepository autresInformationsRepository) {
        this.autresInformationsRepository = autresInformationsRepository;
    }

    /**
     * POST  /autres-informations : Create a new autresInformations.
     *
     * @param autresInformations the autresInformations to create
     * @return the ResponseEntity with status 201 (Created) and with body the new autresInformations, or with status 400 (Bad Request) if the autresInformations has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/autres-informations")
    @Timed
    public ResponseEntity<AutresInformations> createAutresInformations(@RequestBody AutresInformations autresInformations) throws URISyntaxException {
        log.debug("REST request to save AutresInformations : {}", autresInformations);
        if (autresInformations.getId() != null) {
            throw new BadRequestAlertException("A new autresInformations cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AutresInformations result = autresInformationsRepository.save(autresInformations);
        return ResponseEntity.created(new URI("/api/autres-informations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /autres-informations : Updates an existing autresInformations.
     *
     * @param autresInformations the autresInformations to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated autresInformations,
     * or with status 400 (Bad Request) if the autresInformations is not valid,
     * or with status 500 (Internal Server Error) if the autresInformations couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/autres-informations")
    @Timed
    public ResponseEntity<AutresInformations> updateAutresInformations(@RequestBody AutresInformations autresInformations) throws URISyntaxException {
        log.debug("REST request to update AutresInformations : {}", autresInformations);
        if (autresInformations.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AutresInformations result = autresInformationsRepository.save(autresInformations);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, autresInformations.getId().toString()))
            .body(result);
    }

    /**
     * GET  /autres-informations : get all the autresInformations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of autresInformations in body
     */
    @GetMapping("/autres-informations")
    @Timed
    public List<AutresInformations> getAllAutresInformations() {
        log.debug("REST request to get all AutresInformations");
        return autresInformationsRepository.findAll();
    }

    /**
     * GET  /autres-informations/:id : get the "id" autresInformations.
     *
     * @param id the id of the autresInformations to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the autresInformations, or with status 404 (Not Found)
     */
    @GetMapping("/autres-informations/{id}")
    @Timed
    public ResponseEntity<AutresInformations> getAutresInformations(@PathVariable Long id) {
        log.debug("REST request to get AutresInformations : {}", id);
        Optional<AutresInformations> autresInformations = autresInformationsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(autresInformations);
    }

    /**
     * DELETE  /autres-informations/:id : delete the "id" autresInformations.
     *
     * @param id the id of the autresInformations to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/autres-informations/{id}")
    @Timed
    public ResponseEntity<Void> deleteAutresInformations(@PathVariable Long id) {
        log.debug("REST request to delete AutresInformations : {}", id);

        autresInformationsRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
