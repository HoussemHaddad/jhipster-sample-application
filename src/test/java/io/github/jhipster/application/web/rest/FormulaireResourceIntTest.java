package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.Formulaire;
import io.github.jhipster.application.repository.FormulaireRepository;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FormulaireResource REST controller.
 *
 * @see FormulaireResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class FormulaireResourceIntTest {

    private static final Long DEFAULT_I_D_FORMULAIRE = 1L;
    private static final Long UPDATED_I_D_FORMULAIRE = 2L;

    @Autowired
    private FormulaireRepository formulaireRepository;

    @Mock
    private FormulaireRepository formulaireRepositoryMock;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFormulaireMockMvc;

    private Formulaire formulaire;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FormulaireResource formulaireResource = new FormulaireResource(formulaireRepository);
        this.restFormulaireMockMvc = MockMvcBuilders.standaloneSetup(formulaireResource)
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
    public static Formulaire createEntity(EntityManager em) {
        Formulaire formulaire = new Formulaire()
            .iDFormulaire(DEFAULT_I_D_FORMULAIRE);
        return formulaire;
    }

    @Before
    public void initTest() {
        formulaire = createEntity(em);
    }

    @Test
    @Transactional
    public void createFormulaire() throws Exception {
        int databaseSizeBeforeCreate = formulaireRepository.findAll().size();

        // Create the Formulaire
        restFormulaireMockMvc.perform(post("/api/formulaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formulaire)))
            .andExpect(status().isCreated());

        // Validate the Formulaire in the database
        List<Formulaire> formulaireList = formulaireRepository.findAll();
        assertThat(formulaireList).hasSize(databaseSizeBeforeCreate + 1);
        Formulaire testFormulaire = formulaireList.get(formulaireList.size() - 1);
        assertThat(testFormulaire.getiDFormulaire()).isEqualTo(DEFAULT_I_D_FORMULAIRE);
    }

    @Test
    @Transactional
    public void createFormulaireWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = formulaireRepository.findAll().size();

        // Create the Formulaire with an existing ID
        formulaire.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormulaireMockMvc.perform(post("/api/formulaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formulaire)))
            .andExpect(status().isBadRequest());

        // Validate the Formulaire in the database
        List<Formulaire> formulaireList = formulaireRepository.findAll();
        assertThat(formulaireList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFormulaires() throws Exception {
        // Initialize the database
        formulaireRepository.saveAndFlush(formulaire);

        // Get all the formulaireList
        restFormulaireMockMvc.perform(get("/api/formulaires?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formulaire.getId().intValue())))
            .andExpect(jsonPath("$.[*].iDFormulaire").value(hasItem(DEFAULT_I_D_FORMULAIRE.intValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllFormulairesWithEagerRelationshipsIsEnabled() throws Exception {
        FormulaireResource formulaireResource = new FormulaireResource(formulaireRepositoryMock);
        when(formulaireRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restFormulaireMockMvc = MockMvcBuilders.standaloneSetup(formulaireResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restFormulaireMockMvc.perform(get("/api/formulaires?eagerload=true"))
        .andExpect(status().isOk());

        verify(formulaireRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllFormulairesWithEagerRelationshipsIsNotEnabled() throws Exception {
        FormulaireResource formulaireResource = new FormulaireResource(formulaireRepositoryMock);
            when(formulaireRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restFormulaireMockMvc = MockMvcBuilders.standaloneSetup(formulaireResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restFormulaireMockMvc.perform(get("/api/formulaires?eagerload=true"))
        .andExpect(status().isOk());

            verify(formulaireRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getFormulaire() throws Exception {
        // Initialize the database
        formulaireRepository.saveAndFlush(formulaire);

        // Get the formulaire
        restFormulaireMockMvc.perform(get("/api/formulaires/{id}", formulaire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(formulaire.getId().intValue()))
            .andExpect(jsonPath("$.iDFormulaire").value(DEFAULT_I_D_FORMULAIRE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFormulaire() throws Exception {
        // Get the formulaire
        restFormulaireMockMvc.perform(get("/api/formulaires/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFormulaire() throws Exception {
        // Initialize the database
        formulaireRepository.saveAndFlush(formulaire);

        int databaseSizeBeforeUpdate = formulaireRepository.findAll().size();

        // Update the formulaire
        Formulaire updatedFormulaire = formulaireRepository.findById(formulaire.getId()).get();
        // Disconnect from session so that the updates on updatedFormulaire are not directly saved in db
        em.detach(updatedFormulaire);
        updatedFormulaire
            .iDFormulaire(UPDATED_I_D_FORMULAIRE);

        restFormulaireMockMvc.perform(put("/api/formulaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFormulaire)))
            .andExpect(status().isOk());

        // Validate the Formulaire in the database
        List<Formulaire> formulaireList = formulaireRepository.findAll();
        assertThat(formulaireList).hasSize(databaseSizeBeforeUpdate);
        Formulaire testFormulaire = formulaireList.get(formulaireList.size() - 1);
        assertThat(testFormulaire.getiDFormulaire()).isEqualTo(UPDATED_I_D_FORMULAIRE);
    }

    @Test
    @Transactional
    public void updateNonExistingFormulaire() throws Exception {
        int databaseSizeBeforeUpdate = formulaireRepository.findAll().size();

        // Create the Formulaire

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormulaireMockMvc.perform(put("/api/formulaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formulaire)))
            .andExpect(status().isBadRequest());

        // Validate the Formulaire in the database
        List<Formulaire> formulaireList = formulaireRepository.findAll();
        assertThat(formulaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFormulaire() throws Exception {
        // Initialize the database
        formulaireRepository.saveAndFlush(formulaire);

        int databaseSizeBeforeDelete = formulaireRepository.findAll().size();

        // Get the formulaire
        restFormulaireMockMvc.perform(delete("/api/formulaires/{id}", formulaire.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Formulaire> formulaireList = formulaireRepository.findAll();
        assertThat(formulaireList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Formulaire.class);
        Formulaire formulaire1 = new Formulaire();
        formulaire1.setId(1L);
        Formulaire formulaire2 = new Formulaire();
        formulaire2.setId(formulaire1.getId());
        assertThat(formulaire1).isEqualTo(formulaire2);
        formulaire2.setId(2L);
        assertThat(formulaire1).isNotEqualTo(formulaire2);
        formulaire1.setId(null);
        assertThat(formulaire1).isNotEqualTo(formulaire2);
    }
}
