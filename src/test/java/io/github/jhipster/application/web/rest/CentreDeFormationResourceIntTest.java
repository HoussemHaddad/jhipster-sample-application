package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.CentreDeFormation;
import io.github.jhipster.application.repository.CentreDeFormationRepository;
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
 * Test class for the CentreDeFormationResource REST controller.
 *
 * @see CentreDeFormationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class CentreDeFormationResourceIntTest {

    private static final Long DEFAULT_I_D_CENTRE = 1L;
    private static final Long UPDATED_I_D_CENTRE = 2L;

    private static final String DEFAULT_NOM_CENTRE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_CENTRE = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    @Autowired
    private CentreDeFormationRepository centreDeFormationRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCentreDeFormationMockMvc;

    private CentreDeFormation centreDeFormation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CentreDeFormationResource centreDeFormationResource = new CentreDeFormationResource(centreDeFormationRepository);
        this.restCentreDeFormationMockMvc = MockMvcBuilders.standaloneSetup(centreDeFormationResource)
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
    public static CentreDeFormation createEntity(EntityManager em) {
        CentreDeFormation centreDeFormation = new CentreDeFormation()
            .iDCentre(DEFAULT_I_D_CENTRE)
            .nomCentre(DEFAULT_NOM_CENTRE)
            .adresse(DEFAULT_ADRESSE);
        return centreDeFormation;
    }

    @Before
    public void initTest() {
        centreDeFormation = createEntity(em);
    }

    @Test
    @Transactional
    public void createCentreDeFormation() throws Exception {
        int databaseSizeBeforeCreate = centreDeFormationRepository.findAll().size();

        // Create the CentreDeFormation
        restCentreDeFormationMockMvc.perform(post("/api/centre-de-formations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(centreDeFormation)))
            .andExpect(status().isCreated());

        // Validate the CentreDeFormation in the database
        List<CentreDeFormation> centreDeFormationList = centreDeFormationRepository.findAll();
        assertThat(centreDeFormationList).hasSize(databaseSizeBeforeCreate + 1);
        CentreDeFormation testCentreDeFormation = centreDeFormationList.get(centreDeFormationList.size() - 1);
        assertThat(testCentreDeFormation.getiDCentre()).isEqualTo(DEFAULT_I_D_CENTRE);
        assertThat(testCentreDeFormation.getNomCentre()).isEqualTo(DEFAULT_NOM_CENTRE);
        assertThat(testCentreDeFormation.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
    }

    @Test
    @Transactional
    public void createCentreDeFormationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = centreDeFormationRepository.findAll().size();

        // Create the CentreDeFormation with an existing ID
        centreDeFormation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCentreDeFormationMockMvc.perform(post("/api/centre-de-formations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(centreDeFormation)))
            .andExpect(status().isBadRequest());

        // Validate the CentreDeFormation in the database
        List<CentreDeFormation> centreDeFormationList = centreDeFormationRepository.findAll();
        assertThat(centreDeFormationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCentreDeFormations() throws Exception {
        // Initialize the database
        centreDeFormationRepository.saveAndFlush(centreDeFormation);

        // Get all the centreDeFormationList
        restCentreDeFormationMockMvc.perform(get("/api/centre-de-formations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(centreDeFormation.getId().intValue())))
            .andExpect(jsonPath("$.[*].iDCentre").value(hasItem(DEFAULT_I_D_CENTRE.intValue())))
            .andExpect(jsonPath("$.[*].nomCentre").value(hasItem(DEFAULT_NOM_CENTRE.toString())))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE.toString())));
    }
    
    @Test
    @Transactional
    public void getCentreDeFormation() throws Exception {
        // Initialize the database
        centreDeFormationRepository.saveAndFlush(centreDeFormation);

        // Get the centreDeFormation
        restCentreDeFormationMockMvc.perform(get("/api/centre-de-formations/{id}", centreDeFormation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(centreDeFormation.getId().intValue()))
            .andExpect(jsonPath("$.iDCentre").value(DEFAULT_I_D_CENTRE.intValue()))
            .andExpect(jsonPath("$.nomCentre").value(DEFAULT_NOM_CENTRE.toString()))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCentreDeFormation() throws Exception {
        // Get the centreDeFormation
        restCentreDeFormationMockMvc.perform(get("/api/centre-de-formations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCentreDeFormation() throws Exception {
        // Initialize the database
        centreDeFormationRepository.saveAndFlush(centreDeFormation);

        int databaseSizeBeforeUpdate = centreDeFormationRepository.findAll().size();

        // Update the centreDeFormation
        CentreDeFormation updatedCentreDeFormation = centreDeFormationRepository.findById(centreDeFormation.getId()).get();
        // Disconnect from session so that the updates on updatedCentreDeFormation are not directly saved in db
        em.detach(updatedCentreDeFormation);
        updatedCentreDeFormation
            .iDCentre(UPDATED_I_D_CENTRE)
            .nomCentre(UPDATED_NOM_CENTRE)
            .adresse(UPDATED_ADRESSE);

        restCentreDeFormationMockMvc.perform(put("/api/centre-de-formations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCentreDeFormation)))
            .andExpect(status().isOk());

        // Validate the CentreDeFormation in the database
        List<CentreDeFormation> centreDeFormationList = centreDeFormationRepository.findAll();
        assertThat(centreDeFormationList).hasSize(databaseSizeBeforeUpdate);
        CentreDeFormation testCentreDeFormation = centreDeFormationList.get(centreDeFormationList.size() - 1);
        assertThat(testCentreDeFormation.getiDCentre()).isEqualTo(UPDATED_I_D_CENTRE);
        assertThat(testCentreDeFormation.getNomCentre()).isEqualTo(UPDATED_NOM_CENTRE);
        assertThat(testCentreDeFormation.getAdresse()).isEqualTo(UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void updateNonExistingCentreDeFormation() throws Exception {
        int databaseSizeBeforeUpdate = centreDeFormationRepository.findAll().size();

        // Create the CentreDeFormation

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCentreDeFormationMockMvc.perform(put("/api/centre-de-formations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(centreDeFormation)))
            .andExpect(status().isBadRequest());

        // Validate the CentreDeFormation in the database
        List<CentreDeFormation> centreDeFormationList = centreDeFormationRepository.findAll();
        assertThat(centreDeFormationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCentreDeFormation() throws Exception {
        // Initialize the database
        centreDeFormationRepository.saveAndFlush(centreDeFormation);

        int databaseSizeBeforeDelete = centreDeFormationRepository.findAll().size();

        // Get the centreDeFormation
        restCentreDeFormationMockMvc.perform(delete("/api/centre-de-formations/{id}", centreDeFormation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CentreDeFormation> centreDeFormationList = centreDeFormationRepository.findAll();
        assertThat(centreDeFormationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CentreDeFormation.class);
        CentreDeFormation centreDeFormation1 = new CentreDeFormation();
        centreDeFormation1.setId(1L);
        CentreDeFormation centreDeFormation2 = new CentreDeFormation();
        centreDeFormation2.setId(centreDeFormation1.getId());
        assertThat(centreDeFormation1).isEqualTo(centreDeFormation2);
        centreDeFormation2.setId(2L);
        assertThat(centreDeFormation1).isNotEqualTo(centreDeFormation2);
        centreDeFormation1.setId(null);
        assertThat(centreDeFormation1).isNotEqualTo(centreDeFormation2);
    }
}
