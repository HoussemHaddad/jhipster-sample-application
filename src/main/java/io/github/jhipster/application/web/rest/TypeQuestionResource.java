package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.TypeQuestion;
import io.github.jhipster.application.repository.TypeQuestionRepository;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TypeQuestion.
 */
@RestController
@RequestMapping("/api")
public class TypeQuestionResource {

    private final Logger log = LoggerFactory.getLogger(TypeQuestionResource.class);

    private static final String ENTITY_NAME = "jhipsterSampleApplicationTypeQuestion";

    private final TypeQuestionRepository typeQuestionRepository;

    public TypeQuestionResource(TypeQuestionRepository typeQuestionRepository) {
        this.typeQuestionRepository = typeQuestionRepository;
    }

    /**
     * POST  /type-questions : Create a new typeQuestion.
     *
     * @param typeQuestion the typeQuestion to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeQuestion, or with status 400 (Bad Request) if the typeQuestion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-questions")
    @Timed
    public ResponseEntity<TypeQuestion> createTypeQuestion(@Valid @RequestBody TypeQuestion typeQuestion) throws URISyntaxException {
        log.debug("REST request to save TypeQuestion : {}", typeQuestion);
        if (typeQuestion.getId() != null) {
            throw new BadRequestAlertException("A new typeQuestion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeQuestion result = typeQuestionRepository.save(typeQuestion);
        return ResponseEntity.created(new URI("/api/type-questions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-questions : Updates an existing typeQuestion.
     *
     * @param typeQuestion the typeQuestion to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeQuestion,
     * or with status 400 (Bad Request) if the typeQuestion is not valid,
     * or with status 500 (Internal Server Error) if the typeQuestion couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-questions")
    @Timed
    public ResponseEntity<TypeQuestion> updateTypeQuestion(@Valid @RequestBody TypeQuestion typeQuestion) throws URISyntaxException {
        log.debug("REST request to update TypeQuestion : {}", typeQuestion);
        if (typeQuestion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeQuestion result = typeQuestionRepository.save(typeQuestion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, typeQuestion.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-questions : get all the typeQuestions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of typeQuestions in body
     */
    @GetMapping("/type-questions")
    @Timed
    public List<TypeQuestion> getAllTypeQuestions() {
        log.debug("REST request to get all TypeQuestions");
        return typeQuestionRepository.findAll();
    }

    /**
     * GET  /type-questions/:id : get the "id" typeQuestion.
     *
     * @param id the id of the typeQuestion to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeQuestion, or with status 404 (Not Found)
     */
    @GetMapping("/type-questions/{id}")
    @Timed
    public ResponseEntity<TypeQuestion> getTypeQuestion(@PathVariable Long id) {
        log.debug("REST request to get TypeQuestion : {}", id);
        Optional<TypeQuestion> typeQuestion = typeQuestionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(typeQuestion);
    }

    /**
     * DELETE  /type-questions/:id : delete the "id" typeQuestion.
     *
     * @param id the id of the typeQuestion to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-questions/{id}")
    @Timed
    public ResponseEntity<Void> deleteTypeQuestion(@PathVariable Long id) {
        log.debug("REST request to delete TypeQuestion : {}", id);

        typeQuestionRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
