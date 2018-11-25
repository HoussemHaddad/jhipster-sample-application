package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.Formulaire;
import io.github.jhipster.application.repository.FormulaireRepository;
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
 * REST controller for managing Formulaire.
 */
@RestController
@RequestMapping("/api")
public class FormulaireResource {

    private final Logger log = LoggerFactory.getLogger(FormulaireResource.class);

    private static final String ENTITY_NAME = "jhipsterSampleApplicationFormulaire";

    private final FormulaireRepository formulaireRepository;

    public FormulaireResource(FormulaireRepository formulaireRepository) {
        this.formulaireRepository = formulaireRepository;
    }

    /**
     * POST  /formulaires : Create a new formulaire.
     *
     * @param formulaire the formulaire to create
     * @return the ResponseEntity with status 201 (Created) and with body the new formulaire, or with status 400 (Bad Request) if the formulaire has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/formulaires")
    @Timed
    public ResponseEntity<Formulaire> createFormulaire(@RequestBody Formulaire formulaire) throws URISyntaxException {
        log.debug("REST request to save Formulaire : {}", formulaire);
        if (formulaire.getId() != null) {
            throw new BadRequestAlertException("A new formulaire cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Formulaire result = formulaireRepository.save(formulaire);
        return ResponseEntity.created(new URI("/api/formulaires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /formulaires : Updates an existing formulaire.
     *
     * @param formulaire the formulaire to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated formulaire,
     * or with status 400 (Bad Request) if the formulaire is not valid,
     * or with status 500 (Internal Server Error) if the formulaire couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/formulaires")
    @Timed
    public ResponseEntity<Formulaire> updateFormulaire(@RequestBody Formulaire formulaire) throws URISyntaxException {
        log.debug("REST request to update Formulaire : {}", formulaire);
        if (formulaire.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Formulaire result = formulaireRepository.save(formulaire);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, formulaire.getId().toString()))
            .body(result);
    }

    /**
     * GET  /formulaires : get all the formulaires.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of formulaires in body
     */
    @GetMapping("/formulaires")
    @Timed
    public List<Formulaire> getAllFormulaires(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Formulaires");
        return formulaireRepository.findAllWithEagerRelationships();
    }

    /**
     * GET  /formulaires/:id : get the "id" formulaire.
     *
     * @param id the id of the formulaire to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the formulaire, or with status 404 (Not Found)
     */
    @GetMapping("/formulaires/{id}")
    @Timed
    public ResponseEntity<Formulaire> getFormulaire(@PathVariable Long id) {
        log.debug("REST request to get Formulaire : {}", id);
        Optional<Formulaire> formulaire = formulaireRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(formulaire);
    }

    /**
     * DELETE  /formulaires/:id : delete the "id" formulaire.
     *
     * @param id the id of the formulaire to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/formulaires/{id}")
    @Timed
    public ResponseEntity<Void> deleteFormulaire(@PathVariable Long id) {
        log.debug("REST request to delete Formulaire : {}", id);

        formulaireRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
