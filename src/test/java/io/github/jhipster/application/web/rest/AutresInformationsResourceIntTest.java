package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.AutresInformations;
import io.github.jhipster.application.repository.AutresInformationsRepository;
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
 * Test class for the AutresInformationsResource REST controller.
 *
 * @see AutresInformationsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class AutresInformationsResourceIntTest {

    private static final Long DEFAULT_I_D_INFO = 1L;
    private static final Long UPDATED_I_D_INFO = 2L;

    private static final String DEFAULT_NOM_INFO = "AAAAAAAAAA";
    private static final String UPDATED_NOM_INFO = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENU_INFO = "AAAAAAAAAA";
    private static final String UPDATED_CONTENU_INFO = "BBBBBBBBBB";

    @Autowired
    private AutresInformationsRepository autresInformationsRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAutresInformationsMockMvc;

    private AutresInformations autresInformations;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AutresInformationsResource autresInformationsResource = new AutresInformationsResource(autresInformationsRepository);
        this.restAutresInformationsMockMvc = MockMvcBuilders.standaloneSetup(autresInformationsResource)
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
    public static AutresInformations createEntity(EntityManager em) {
        AutresInformations autresInformations = new AutresInformations()
            .iDInfo(DEFAULT_I_D_INFO)
            .nomInfo(DEFAULT_NOM_INFO)
            .contenuInfo(DEFAULT_CONTENU_INFO);
        return autresInformations;
    }

    @Before
    public void initTest() {
        autresInformations = createEntity(em);
    }

    @Test
    @Transactional
    public void createAutresInformations() throws Exception {
        int databaseSizeBeforeCreate = autresInformationsRepository.findAll().size();

        // Create the AutresInformations
        restAutresInformationsMockMvc.perform(post("/api/autres-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autresInformations)))
            .andExpect(status().isCreated());

        // Validate the AutresInformations in the database
        List<AutresInformations> autresInformationsList = autresInformationsRepository.findAll();
        assertThat(autresInformationsList).hasSize(databaseSizeBeforeCreate + 1);
        AutresInformations testAutresInformations = autresInformationsList.get(autresInformationsList.size() - 1);
        assertThat(testAutresInformations.getiDInfo()).isEqualTo(DEFAULT_I_D_INFO);
        assertThat(testAutresInformations.getNomInfo()).isEqualTo(DEFAULT_NOM_INFO);
        assertThat(testAutresInformations.getContenuInfo()).isEqualTo(DEFAULT_CONTENU_INFO);
    }

    @Test
    @Transactional
    public void createAutresInformationsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = autresInformationsRepository.findAll().size();

        // Create the AutresInformations with an existing ID
        autresInformations.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAutresInformationsMockMvc.perform(post("/api/autres-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autresInformations)))
            .andExpect(status().isBadRequest());

        // Validate the AutresInformations in the database
        List<AutresInformations> autresInformationsList = autresInformationsRepository.findAll();
        assertThat(autresInformationsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAutresInformations() throws Exception {
        // Initialize the database
        autresInformationsRepository.saveAndFlush(autresInformations);

        // Get all the autresInformationsList
        restAutresInformationsMockMvc.perform(get("/api/autres-informations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(autresInformations.getId().intValue())))
            .andExpect(jsonPath("$.[*].iDInfo").value(hasItem(DEFAULT_I_D_INFO.intValue())))
            .andExpect(jsonPath("$.[*].nomInfo").value(hasItem(DEFAULT_NOM_INFO.toString())))
            .andExpect(jsonPath("$.[*].contenuInfo").value(hasItem(DEFAULT_CONTENU_INFO.toString())));
    }
    
    @Test
    @Transactional
    public void getAutresInformations() throws Exception {
        // Initialize the database
        autresInformationsRepository.saveAndFlush(autresInformations);

        // Get the autresInformations
        restAutresInformationsMockMvc.perform(get("/api/autres-informations/{id}", autresInformations.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(autresInformations.getId().intValue()))
            .andExpect(jsonPath("$.iDInfo").value(DEFAULT_I_D_INFO.intValue()))
            .andExpect(jsonPath("$.nomInfo").value(DEFAULT_NOM_INFO.toString()))
            .andExpect(jsonPath("$.contenuInfo").value(DEFAULT_CONTENU_INFO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAutresInformations() throws Exception {
        // Get the autresInformations
        restAutresInformationsMockMvc.perform(get("/api/autres-informations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAutresInformations() throws Exception {
        // Initialize the database
        autresInformationsRepository.saveAndFlush(autresInformations);

        int databaseSizeBeforeUpdate = autresInformationsRepository.findAll().size();

        // Update the autresInformations
        AutresInformations updatedAutresInformations = autresInformationsRepository.findById(autresInformations.getId()).get();
        // Disconnect from session so that the updates on updatedAutresInformations are not directly saved in db
        em.detach(updatedAutresInformations);
        updatedAutresInformations
            .iDInfo(UPDATED_I_D_INFO)
            .nomInfo(UPDATED_NOM_INFO)
            .contenuInfo(UPDATED_CONTENU_INFO);

        restAutresInformationsMockMvc.perform(put("/api/autres-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAutresInformations)))
            .andExpect(status().isOk());

        // Validate the AutresInformations in the database
        List<AutresInformations> autresInformationsList = autresInformationsRepository.findAll();
        assertThat(autresInformationsList).hasSize(databaseSizeBeforeUpdate);
        AutresInformations testAutresInformations = autresInformationsList.get(autresInformationsList.size() - 1);
        assertThat(testAutresInformations.getiDInfo()).isEqualTo(UPDATED_I_D_INFO);
        assertThat(testAutresInformations.getNomInfo()).isEqualTo(UPDATED_NOM_INFO);
        assertThat(testAutresInformations.getContenuInfo()).isEqualTo(UPDATED_CONTENU_INFO);
    }

    @Test
    @Transactional
    public void updateNonExistingAutresInformations() throws Exception {
        int databaseSizeBeforeUpdate = autresInformationsRepository.findAll().size();

        // Create the AutresInformations

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAutresInformationsMockMvc.perform(put("/api/autres-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(autresInformations)))
            .andExpect(status().isBadRequest());

        // Validate the AutresInformations in the database
        List<AutresInformations> autresInformationsList = autresInformationsRepository.findAll();
        assertThat(autresInformationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAutresInformations() throws Exception {
        // Initialize the database
        autresInformationsRepository.saveAndFlush(autresInformations);

        int databaseSizeBeforeDelete = autresInformationsRepository.findAll().size();

        // Get the autresInformations
        restAutresInformationsMockMvc.perform(delete("/api/autres-informations/{id}", autresInformations.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AutresInformations> autresInformationsList = autresInformationsRepository.findAll();
        assertThat(autresInformationsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AutresInformations.class);
        AutresInformations autresInformations1 = new AutresInformations();
        autresInformations1.setId(1L);
        AutresInformations autresInformations2 = new AutresInformations();
        autresInformations2.setId(autresInformations1.getId());
        assertThat(autresInformations1).isEqualTo(autresInformations2);
        autresInformations2.setId(2L);
        assertThat(autresInformations1).isNotEqualTo(autresInformations2);
        autresInformations1.setId(null);
        assertThat(autresInformations1).isNotEqualTo(autresInformations2);
    }
}
