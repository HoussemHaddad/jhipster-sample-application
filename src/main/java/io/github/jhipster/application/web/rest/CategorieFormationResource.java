package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.CategorieFormation;
import io.github.jhipster.application.repository.CategorieFormationRepository;
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
 * REST controller for managing CategorieFormation.
 */
@RestController
@RequestMapping("/api")
public class CategorieFormationResource {

    private final Logger log = LoggerFactory.getLogger(CategorieFormationResource.class);

    private static final String ENTITY_NAME = "jhipsterSampleApplicationCategorieFormation";

    private final CategorieFormationRepository categorieFormationRepository;

    public CategorieFormationResource(CategorieFormationRepository categorieFormationRepository) {
        this.categorieFormationRepository = categorieFormationRepository;
    }

    /**
     * POST  /categorie-formations : Create a new categorieFormation.
     *
     * @param categorieFormation the categorieFormation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new categorieFormation, or with status 400 (Bad Request) if the categorieFormation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/categorie-formations")
    @Timed
    public ResponseEntity<CategorieFormation> createCategorieFormation(@RequestBody CategorieFormation categorieFormation) throws URISyntaxException {
        log.debug("REST request to save CategorieFormation : {}", categorieFormation);
        if (categorieFormation.getId() != null) {
            throw new BadRequestAlertException("A new categorieFormation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategorieFormation result = categorieFormationRepository.save(categorieFormation);
        return ResponseEntity.created(new URI("/api/categorie-formations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /categorie-formations : Updates an existing categorieFormation.
     *
     * @param categorieFormation the categorieFormation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated categorieFormation,
     * or with status 400 (Bad Request) if the categorieFormation is not valid,
     * or with status 500 (Internal Server Error) if the categorieFormation couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/categorie-formations")
    @Timed
    public ResponseEntity<CategorieFormation> updateCategorieFormation(@RequestBody CategorieFormation categorieFormation) throws URISyntaxException {
        log.debug("REST request to update CategorieFormation : {}", categorieFormation);
        if (categorieFormation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CategorieFormation result = categorieFormationRepository.save(categorieFormation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, categorieFormation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /categorie-formations : get all the categorieFormations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of categorieFormations in body
     */
    @GetMapping("/categorie-formations")
    @Timed
    public List<CategorieFormation> getAllCategorieFormations() {
        log.debug("REST request to get all CategorieFormations");
        return categorieFormationRepository.findAll();
    }

    /**
     * GET  /categorie-formations/:id : get the "id" categorieFormation.
     *
     * @param id the id of the categorieFormation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the categorieFormation, or with status 404 (Not Found)
     */
    @GetMapping("/categorie-formations/{id}")
    @Timed
    public ResponseEntity<CategorieFormation> getCategorieFormation(@PathVariable Long id) {
        log.debug("REST request to get CategorieFormation : {}", id);
        Optional<CategorieFormation> categorieFormation = categorieFormationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(categorieFormation);
    }

    /**
     * DELETE  /categorie-formations/:id : delete the "id" categorieFormation.
     *
     * @param id the id of the categorieFormation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/categorie-formations/{id}")
    @Timed
    public ResponseEntity<Void> deleteCategorieFormation(@PathVariable Long id) {
        log.debug("REST request to delete CategorieFormation : {}", id);

        categorieFormationRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
