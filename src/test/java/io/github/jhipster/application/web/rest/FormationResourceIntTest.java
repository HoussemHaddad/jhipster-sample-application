package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.Formation;
import io.github.jhipster.application.repository.FormationRepository;
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
 * Test class for the FormationResource REST controller.
 *
 * @see FormationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class FormationResourceIntTest {

    private static final Long DEFAULT_I_D_FORMATION = 1L;
    private static final Long UPDATED_I_D_FORMATION = 2L;

    private static final String DEFAULT_NOM_FORMATION = "AAAAAAAAAA";
    private static final String UPDATED_NOM_FORMATION = "BBBBBBBBBB";

    private static final String DEFAULT_INFORMATION = "AAAAAAAAAA";
    private static final String UPDATED_INFORMATION = "BBBBBBBBBB";

    @Autowired
    private FormationRepository formationRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFormationMockMvc;

    private Formation formation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FormationResource formationResource = new FormationResource(formationRepository);
        this.restFormationMockMvc = MockMvcBuilders.standaloneSetup(formationResource)
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
    public static Formation createEntity(EntityManager em) {
        Formation formation = new Formation()
            .iDFormation(DEFAULT_I_D_FORMATION)
            .nomFormation(DEFAULT_NOM_FORMATION)
            .information(DEFAULT_INFORMATION);
        return formation;
    }

    @Before
    public void initTest() {
        formation = createEntity(em);
    }

    @Test
    @Transactional
    public void createFormation() throws Exception {
        int databaseSizeBeforeCreate = formationRepository.findAll().size();

        // Create the Formation
        restFormationMockMvc.perform(post("/api/formations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formation)))
            .andExpect(status().isCreated());

        // Validate the Formation in the database
        List<Formation> formationList = formationRepository.findAll();
        assertThat(formationList).hasSize(databaseSizeBeforeCreate + 1);
        Formation testFormation = formationList.get(formationList.size() - 1);
        assertThat(testFormation.getiDFormation()).isEqualTo(DEFAULT_I_D_FORMATION);
        assertThat(testFormation.getNomFormation()).isEqualTo(DEFAULT_NOM_FORMATION);
        assertThat(testFormation.getInformation()).isEqualTo(DEFAULT_INFORMATION);
    }

    @Test
    @Transactional
    public void createFormationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = formationRepository.findAll().size();

        // Create the Formation with an existing ID
        formation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormationMockMvc.perform(post("/api/formations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formation)))
            .andExpect(status().isBadRequest());

        // Validate the Formation in the database
        List<Formation> formationList = formationRepository.findAll();
        assertThat(formationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFormations() throws Exception {
        // Initialize the database
        formationRepository.saveAndFlush(formation);

        // Get all the formationList
        restFormationMockMvc.perform(get("/api/formations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formation.getId().intValue())))
            .andExpect(jsonPath("$.[*].iDFormation").value(hasItem(DEFAULT_I_D_FORMATION.intValue())))
            .andExpect(jsonPath("$.[*].nomFormation").value(hasItem(DEFAULT_NOM_FORMATION.toString())))
            .andExpect(jsonPath("$.[*].information").value(hasItem(DEFAULT_INFORMATION.toString())));
    }
    
    @Test
    @Transactional
    public void getFormation() throws Exception {
        // Initialize the database
        formationRepository.saveAndFlush(formation);

        // Get the formation
        restFormationMockMvc.perform(get("/api/formations/{id}", formation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(formation.getId().intValue()))
            .andExpect(jsonPath("$.iDFormation").value(DEFAULT_I_D_FORMATION.intValue()))
            .andExpect(jsonPath("$.nomFormation").value(DEFAULT_NOM_FORMATION.toString()))
            .andExpect(jsonPath("$.information").value(DEFAULT_INFORMATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFormation() throws Exception {
        // Get the formation
        restFormationMockMvc.perform(get("/api/formations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFormation() throws Exception {
        // Initialize the database
        formationRepository.saveAndFlush(formation);

        int databaseSizeBeforeUpdate = formationRepository.findAll().size();

        // Update the formation
        Formation updatedFormation = formationRepository.findById(formation.getId()).get();
        // Disconnect from session so that the updates on updatedFormation are not directly saved in db
        em.detach(updatedFormation);
        updatedFormation
            .iDFormation(UPDATED_I_D_FORMATION)
            .nomFormation(UPDATED_NOM_FORMATION)
            .information(UPDATED_INFORMATION);

        restFormationMockMvc.perform(put("/api/formations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFormation)))
            .andExpect(status().isOk());

        // Validate the Formation in the database
        List<Formation> formationList = formationRepository.findAll();
        assertThat(formationList).hasSize(databaseSizeBeforeUpdate);
        Formation testFormation = formationList.get(formationList.size() - 1);
        assertThat(testFormation.getiDFormation()).isEqualTo(UPDATED_I_D_FORMATION);
        assertThat(testFormation.getNomFormation()).isEqualTo(UPDATED_NOM_FORMATION);
        assertThat(testFormation.getInformation()).isEqualTo(UPDATED_INFORMATION);
    }

    @Test
    @Transactional
    public void updateNonExistingFormation() throws Exception {
        int databaseSizeBeforeUpdate = formationRepository.findAll().size();

        // Create the Formation

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormationMockMvc.perform(put("/api/formations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formation)))
            .andExpect(status().isBadRequest());

        // Validate the Formation in the database
        List<Formation> formationList = formationRepository.findAll();
        assertThat(formationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFormation() throws Exception {
        // Initialize the database
        formationRepository.saveAndFlush(formation);

        int databaseSizeBeforeDelete = formationRepository.findAll().size();

        // Get the formation
        restFormationMockMvc.perform(delete("/api/formations/{id}", formation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Formation> formationList = formationRepository.findAll();
        assertThat(formationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Formation.class);
        Formation formation1 = new Formation();
        formation1.setId(1L);
        Formation formation2 = new Formation();
        formation2.setId(formation1.getId());
        assertThat(formation1).isEqualTo(formation2);
        formation2.setId(2L);
        assertThat(formation1).isNotEqualTo(formation2);
        formation1.setId(null);
        assertThat(formation1).isNotEqualTo(formation2);
    }
}
