package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.CentreDeFormation;
import io.github.jhipster.application.repository.CentreDeFormationRepository;
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
 * REST controller for managing CentreDeFormation.
 */
@RestController
@RequestMapping("/api")
public class CentreDeFormationResource {

    private final Logger log = LoggerFactory.getLogger(CentreDeFormationResource.class);

    private static final String ENTITY_NAME = "jhipsterSampleApplicationCentreDeFormation";

    private final CentreDeFormationRepository centreDeFormationRepository;

    public CentreDeFormationResource(CentreDeFormationRepository centreDeFormationRepository) {
        this.centreDeFormationRepository = centreDeFormationRepository;
    }

    /**
     * POST  /centre-de-formations : Create a new centreDeFormation.
     *
     * @param centreDeFormation the centreDeFormation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new centreDeFormation, or with status 400 (Bad Request) if the centreDeFormation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/centre-de-formations")
    @Timed
    public ResponseEntity<CentreDeFormation> createCentreDeFormation(@RequestBody CentreDeFormation centreDeFormation) throws URISyntaxException {
        log.debug("REST request to save CentreDeFormation : {}", centreDeFormation);
        if (centreDeFormation.getId() != null) {
            throw new BadRequestAlertException("A new centreDeFormation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CentreDeFormation result = centreDeFormationRepository.save(centreDeFormation);
        return ResponseEntity.created(new URI("/api/centre-de-formations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /centre-de-formations : Updates an existing centreDeFormation.
     *
     * @param centreDeFormation the centreDeFormation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated centreDeFormation,
     * or with status 400 (Bad Request) if the centreDeFormation is not valid,
     * or with status 500 (Internal Server Error) if the centreDeFormation couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/centre-de-formations")
    @Timed
    public ResponseEntity<CentreDeFormation> updateCentreDeFormation(@RequestBody CentreDeFormation centreDeFormation) throws URISyntaxException {
        log.debug("REST request to update CentreDeFormation : {}", centreDeFormation);
        if (centreDeFormation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CentreDeFormation result = centreDeFormationRepository.save(centreDeFormation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, centreDeFormation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /centre-de-formations : get all the centreDeFormations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of centreDeFormations in body
     */
    @GetMapping("/centre-de-formations")
    @Timed
    public List<CentreDeFormation> getAllCentreDeFormations() {
        log.debug("REST request to get all CentreDeFormations");
        return centreDeFormationRepository.findAll();
    }

    /**
     * GET  /centre-de-formations/:id : get the "id" centreDeFormation.
     *
     * @param id the id of the centreDeFormation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the centreDeFormation, or with status 404 (Not Found)
     */
    @GetMapping("/centre-de-formations/{id}")
    @Timed
    public ResponseEntity<CentreDeFormation> getCentreDeFormation(@PathVariable Long id) {
        log.debug("REST request to get CentreDeFormation : {}", id);
        Optional<CentreDeFormation> centreDeFormation = centreDeFormationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(centreDeFormation);
    }

    /**
     * DELETE  /centre-de-formations/:id : delete the "id" centreDeFormation.
     *
     * @param id the id of the centreDeFormation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/centre-de-formations/{id}")
    @Timed
    public ResponseEntity<Void> deleteCentreDeFormation(@PathVariable Long id) {
        log.debug("REST request to delete CentreDeFormation : {}", id);

        centreDeFormationRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
