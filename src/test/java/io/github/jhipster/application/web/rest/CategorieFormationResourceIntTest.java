package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.CategorieFormation;
import io.github.jhipster.application.repository.CategorieFormationRepository;
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
 * Test class for the CategorieFormationResource REST controller.
 *
 * @see CategorieFormationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class CategorieFormationResourceIntTest {

    private static final Long DEFAULT_I_D_CATEGORIE = 1L;
    private static final Long UPDATED_I_D_CATEGORIE = 2L;

    private static final String DEFAULT_NOM_CATEGORIE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_CATEGORIE = "BBBBBBBBBB";

    @Autowired
    private CategorieFormationRepository categorieFormationRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCategorieFormationMockMvc;

    private CategorieFormation categorieFormation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CategorieFormationResource categorieFormationResource = new CategorieFormationResource(categorieFormationRepository);
        this.restCategorieFormationMockMvc = MockMvcBuilders.standaloneSetup(categorieFormationResource)
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
    public static CategorieFormation createEntity(EntityManager em) {
        CategorieFormation categorieFormation = new CategorieFormation()
            .iDCategorie(DEFAULT_I_D_CATEGORIE)
            .nomCategorie(DEFAULT_NOM_CATEGORIE);
        return categorieFormation;
    }

    @Before
    public void initTest() {
        categorieFormation = createEntity(em);
    }

    @Test
    @Transactional
    public void createCategorieFormation() throws Exception {
        int databaseSizeBeforeCreate = categorieFormationRepository.findAll().size();

        // Create the CategorieFormation
        restCategorieFormationMockMvc.perform(post("/api/categorie-formations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categorieFormation)))
            .andExpect(status().isCreated());

        // Validate the CategorieFormation in the database
        List<CategorieFormation> categorieFormationList = categorieFormationRepository.findAll();
        assertThat(categorieFormationList).hasSize(databaseSizeBeforeCreate + 1);
        CategorieFormation testCategorieFormation = categorieFormationList.get(categorieFormationList.size() - 1);
        assertThat(testCategorieFormation.getiDCategorie()).isEqualTo(DEFAULT_I_D_CATEGORIE);
        assertThat(testCategorieFormation.getNomCategorie()).isEqualTo(DEFAULT_NOM_CATEGORIE);
    }

    @Test
    @Transactional
    public void createCategorieFormationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = categorieFormationRepository.findAll().size();

        // Create the CategorieFormation with an existing ID
        categorieFormation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategorieFormationMockMvc.perform(post("/api/categorie-formations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categorieFormation)))
            .andExpect(status().isBadRequest());

        // Validate the CategorieFormation in the database
        List<CategorieFormation> categorieFormationList = categorieFormationRepository.findAll();
        assertThat(categorieFormationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCategorieFormations() throws Exception {
        // Initialize the database
        categorieFormationRepository.saveAndFlush(categorieFormation);

        // Get all the categorieFormationList
        restCategorieFormationMockMvc.perform(get("/api/categorie-formations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categorieFormation.getId().intValue())))
            .andExpect(jsonPath("$.[*].iDCategorie").value(hasItem(DEFAULT_I_D_CATEGORIE.intValue())))
            .andExpect(jsonPath("$.[*].nomCategorie").value(hasItem(DEFAULT_NOM_CATEGORIE.toString())));
    }
    
    @Test
    @Transactional
    public void getCategorieFormation() throws Exception {
        // Initialize the database
        categorieFormationRepository.saveAndFlush(categorieFormation);

        // Get the categorieFormation
        restCategorieFormationMockMvc.perform(get("/api/categorie-formations/{id}", categorieFormation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(categorieFormation.getId().intValue()))
            .andExpect(jsonPath("$.iDCategorie").value(DEFAULT_I_D_CATEGORIE.intValue()))
            .andExpect(jsonPath("$.nomCategorie").value(DEFAULT_NOM_CATEGORIE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCategorieFormation() throws Exception {
        // Get the categorieFormation
        restCategorieFormationMockMvc.perform(get("/api/categorie-formations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCategorieFormation() throws Exception {
        // Initialize the database
        categorieFormationRepository.saveAndFlush(categorieFormation);

        int databaseSizeBeforeUpdate = categorieFormationRepository.findAll().size();

        // Update the categorieFormation
        CategorieFormation updatedCategorieFormation = categorieFormationRepository.findById(categorieFormation.getId()).get();
        // Disconnect from session so that the updates on updatedCategorieFormation are not directly saved in db
        em.detach(updatedCategorieFormation);
        updatedCategorieFormation
            .iDCategorie(UPDATED_I_D_CATEGORIE)
            .nomCategorie(UPDATED_NOM_CATEGORIE);

        restCategorieFormationMockMvc.perform(put("/api/categorie-formations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCategorieFormation)))
            .andExpect(status().isOk());

        // Validate the CategorieFormation in the database
        List<CategorieFormation> categorieFormationList = categorieFormationRepository.findAll();
        assertThat(categorieFormationList).hasSize(databaseSizeBeforeUpdate);
        CategorieFormation testCategorieFormation = categorieFormationList.get(categorieFormationList.size() - 1);
        assertThat(testCategorieFormation.getiDCategorie()).isEqualTo(UPDATED_I_D_CATEGORIE);
        assertThat(testCategorieFormation.getNomCategorie()).isEqualTo(UPDATED_NOM_CATEGORIE);
    }

    @Test
    @Transactional
    public void updateNonExistingCategorieFormation() throws Exception {
        int databaseSizeBeforeUpdate = categorieFormationRepository.findAll().size();

        // Create the CategorieFormation

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategorieFormationMockMvc.perform(put("/api/categorie-formations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categorieFormation)))
            .andExpect(status().isBadRequest());

        // Validate the CategorieFormation in the database
        List<CategorieFormation> categorieFormationList = categorieFormationRepository.findAll();
        assertThat(categorieFormationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCategorieFormation() throws Exception {
        // Initialize the database
        categorieFormationRepository.saveAndFlush(categorieFormation);

        int databaseSizeBeforeDelete = categorieFormationRepository.findAll().size();

        // Get the categorieFormation
        restCategorieFormationMockMvc.perform(delete("/api/categorie-formations/{id}", categorieFormation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CategorieFormation> categorieFormationList = categorieFormationRepository.findAll();
        assertThat(categorieFormationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategorieFormation.class);
        CategorieFormation categorieFormation1 = new CategorieFormation();
        categorieFormation1.setId(1L);
        CategorieFormation categorieFormation2 = new CategorieFormation();
        categorieFormation2.setId(categorieFormation1.getId());
        assertThat(categorieFormation1).isEqualTo(categorieFormation2);
        categorieFormation2.setId(2L);
        assertThat(categorieFormation1).isNotEqualTo(categorieFormation2);
        categorieFormation1.setId(null);
        assertThat(categorieFormation1).isNotEqualTo(categorieFormation2);
    }
}
