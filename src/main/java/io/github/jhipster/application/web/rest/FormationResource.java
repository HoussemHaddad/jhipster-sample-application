package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.Formation;
import io.github.jhipster.application.repository.FormationRepository;
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
 * REST controller for managing Formation.
 */
@RestController
@RequestMapping("/api")
public class FormationResource {

    private final Logger log = LoggerFactory.getLogger(FormationResource.class);

    private static final String ENTITY_NAME = "jhipsterSampleApplicationFormation";

    private final FormationRepository formationRepository;

    public FormationResource(FormationRepository formationRepository) {
        this.formationRepository = formationRepository;
    }

    /**
     * POST  /formations : Create a new formation.
     *
     * @param formation the formation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new formation, or with status 400 (Bad Request) if the formation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/formations")
    @Timed
    public ResponseEntity<Formation> createFormation(@RequestBody Formation formation) throws URISyntaxException {
        log.debug("REST request to save Formation : {}", formation);
        if (formation.getId() != null) {
            throw new BadRequestAlertException("A new formation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Formation result = formationRepository.save(formation);
        return ResponseEntity.created(new URI("/api/formations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /formations : Updates an existing formation.
     *
     * @param formation the formation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated formation,
     * or with status 400 (Bad Request) if the formation is not valid,
     * or with status 500 (Internal Server Error) if the formation couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/formations")
    @Timed
    public ResponseEntity<Formation> updateFormation(@RequestBody Formation formation) throws URISyntaxException {
        log.debug("REST request to update Formation : {}", formation);
        if (formation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Formation result = formationRepository.save(formation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, formation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /formations : get all the formations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of formations in body
     */
    @GetMapping("/formations")
    @Timed
    public List<Formation> getAllFormations() {
        log.debug("REST request to get all Formations");
        return formationRepository.findAll();
    }

    /**
     * GET  /formations/:id : get the "id" formation.
     *
     * @param id the id of the formation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the formation, or with status 404 (Not Found)
     */
    @GetMapping("/formations/{id}")
    @Timed
    public ResponseEntity<Formation> getFormation(@PathVariable Long id) {
        log.debug("REST request to get Formation : {}", id);
        Optional<Formation> formation = formationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(formation);
    }

    /**
     * DELETE  /formations/:id : delete the "id" formation.
     *
     * @param id the id of the formation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/formations/{id}")
    @Timed
    public ResponseEntity<Void> deleteFormation(@PathVariable Long id) {
        log.debug("REST request to delete Formation : {}", id);

        formationRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
