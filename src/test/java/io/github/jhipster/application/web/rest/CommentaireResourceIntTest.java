package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.Commentaire;
import io.github.jhipster.application.repository.CommentaireRepository;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CommentaireResource REST controller.
 *
 * @see CommentaireResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class CommentaireResourceIntTest {

    private static final Long DEFAULT_I_DCOMM = 1L;
    private static final Long UPDATED_I_DCOMM = 2L;

    private static final String DEFAULT_CONTENU = "AAAAAAAAAA";
    private static final String UPDATED_CONTENU = "BBBBBBBBBB";

    @Autowired
    private CommentaireRepository commentaireRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCommentaireMockMvc;

    private Commentaire commentaire;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommentaireResource commentaireResource = new CommentaireResource(commentaireRepository);
        this.restCommentaireMockMvc = MockMvcBuilders.standaloneSetup(commentaireResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Commentaire createEntity(EntityManager em) {
        Commentaire commentaire = new Commentaire()
            .iDcomm(DEFAULT_I_DCOMM)
            .contenu(DEFAULT_CONTENU);
        return commentaire;
    }

    @Before
    public void initTest() {
        commentaire = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommentaire() throws Exception {
        int databaseSizeBeforeCreate = commentaireRepository.findAll().size();

        // Create the Commentaire
        restCommentaireMockMvc.perform(post("/api/commentaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commentaire)))
            .andExpect(status().isCreated());

        // Validate the Commentaire in the database
        List<Commentaire> commentaireList = commentaireRepository.findAll();
        assertThat(commentaireList).hasSize(databaseSizeBeforeCreate + 1);
        Commentaire testCommentaire = commentaireList.get(commentaireList.size() - 1);
        assertThat(testCommentaire.getiDcomm()).isEqualTo(DEFAULT_I_DCOMM);
        assertThat(testCommentaire.getContenu()).isEqualTo(DEFAULT_CONTENU);
    }

    @Test
    @Transactional
    public void createCommentaireWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commentaireRepository.findAll().size();

        // Create the Commentaire with an existing ID
        commentaire.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommentaireMockMvc.perform(post("/api/commentaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commentaire)))
            .andExpect(status().isBadRequest());

        // Validate the Commentaire in the database
        List<Commentaire> commentaireList = commentaireRepository.findAll();
        assertThat(commentaireList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCommentaires() throws Exception {
        // Initialize the database
        commentaireRepository.saveAndFlush(commentaire);

        // Get all the commentaireList
        restCommentaireMockMvc.perform(get("/api/commentaires?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commentaire.getId().intValue())))
            .andExpect(jsonPath("$.[*].iDcomm").value(hasItem(DEFAULT_I_DCOMM.intValue())))
            .andExpect(jsonPath("$.[*].contenu").value(hasItem(DEFAULT_CONTENU.toString())));
    }
    
    @Test
    @Transactional
    public void getCommentaire() throws Exception {
        // Initialize the database
        commentaireRepository.saveAndFlush(commentaire);

        // Get the commentaire
        restCommentaireMockMvc.perform(get("/api/commentaires/{id}", commentaire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commentaire.getId().intValue()))
            .andExpect(jsonPath("$.iDcomm").value(DEFAULT_I_DCOMM.intValue()))
            .andExpect(jsonPath("$.contenu").value(DEFAULT_CONTENU.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCommentaire() throws Exception {
        // Get the commentaire
        restCommentaireMockMvc.perform(get("/api/commentaires/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommentaire() throws Exception {
        // Initialize the database
        commentaireRepository.saveAndFlush(commentaire);

        int databaseSizeBeforeUpdate = commentaireRepository.findAll().size();

        // Update the commentaire
        Commentaire updatedCommentaire = commentaireRepository.findById(commentaire.getId()).get();
        // Disconnect from session so that the updates on updatedCommentaire are not directly saved in db
        em.detach(updatedCommentaire);
        updatedCommentaire
            .iDcomm(UPDATED_I_DCOMM)
            .contenu(UPDATED_CONTENU);

        restCommentaireMockMvc.perform(put("/api/commentaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCommentaire)))
            .andExpect(status().isOk());

        // Validate the Commentaire in the database
        List<Commentaire> commentaireList = commentaireRepository.findAll();
        assertThat(commentaireList).hasSize(databaseSizeBeforeUpdate);
        Commentaire testCommentaire = commentaireList.get(commentaireList.size() - 1);
        assertThat(testCommentaire.getiDcomm()).isEqualTo(UPDATED_I_DCOMM);
        assertThat(testCommentaire.getContenu()).isEqualTo(UPDATED_CONTENU);
    }

    @Test
    @Transactional
    public void updateNonExistingCommentaire() throws Exception {
        int databaseSizeBeforeUpdate = commentaireRepository.findAll().size();

        // Create the Commentaire

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommentaireMockMvc.perform(put("/api/commentaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commentaire)))
            .andExpect(status().isBadRequest());

        // Validate the Commentaire in the database
        List<Commentaire> commentaireList = commentaireRepository.findAll();
        assertThat(commentaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCommentaire() throws Exception {
        // Initialize the database
        commentaireRepository.saveAndFlush(commentaire);

        int databaseSizeBeforeDelete = commentaireRepository.findAll().size();

        // Get the commentaire
        restCommentaireMockMvc.perform(delete("/api/commentaires/{id}", commentaire.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Commentaire> commentaireList = commentaireRepository.findAll();
        assertThat(commentaireList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Commentaire.class);
        Commentaire commentaire1 = new Commentaire();
        commentaire1.setId(1L);
        Commentaire commentaire2 = new Commentaire();
        commentaire2.setId(commentaire1.getId());
        assertThat(commentaire1).isEqualTo(commentaire2);
        commentaire2.setId(2L);
        assertThat(commentaire1).isNotEqualTo(commentaire2);
        commentaire1.setId(null);
        assertThat(commentaire1).isNotEqualTo(commentaire2);
    }
}
