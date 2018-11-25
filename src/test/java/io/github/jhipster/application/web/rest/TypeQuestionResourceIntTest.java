package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.TypeQuestion;
import io.github.jhipster.application.repository.TypeQuestionRepository;
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
 * Test class for the TypeQuestionResource REST controller.
 *
 * @see TypeQuestionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class TypeQuestionResourceIntTest {

    private static final Long DEFAULT_I_D_TYPE = 1L;
    private static final Long UPDATED_I_D_TYPE = 2L;

    private static final String DEFAULT_NOM_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_TYPE = "BBBBBBBBBB";

    @Autowired
    private TypeQuestionRepository typeQuestionRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTypeQuestionMockMvc;

    private TypeQuestion typeQuestion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeQuestionResource typeQuestionResource = new TypeQuestionResource(typeQuestionRepository);
        this.restTypeQuestionMockMvc = MockMvcBuilders.standaloneSetup(typeQuestionResource)
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
    public static TypeQuestion createEntity(EntityManager em) {
        TypeQuestion typeQuestion = new TypeQuestion()
            .iDType(DEFAULT_I_D_TYPE)
            .nomType(DEFAULT_NOM_TYPE);
        return typeQuestion;
    }

    @Before
    public void initTest() {
        typeQuestion = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeQuestion() throws Exception {
        int databaseSizeBeforeCreate = typeQuestionRepository.findAll().size();

        // Create the TypeQuestion
        restTypeQuestionMockMvc.perform(post("/api/type-questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeQuestion)))
            .andExpect(status().isCreated());

        // Validate the TypeQuestion in the database
        List<TypeQuestion> typeQuestionList = typeQuestionRepository.findAll();
        assertThat(typeQuestionList).hasSize(databaseSizeBeforeCreate + 1);
        TypeQuestion testTypeQuestion = typeQuestionList.get(typeQuestionList.size() - 1);
        assertThat(testTypeQuestion.getiDType()).isEqualTo(DEFAULT_I_D_TYPE);
        assertThat(testTypeQuestion.getNomType()).isEqualTo(DEFAULT_NOM_TYPE);
    }

    @Test
    @Transactional
    public void createTypeQuestionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeQuestionRepository.findAll().size();

        // Create the TypeQuestion with an existing ID
        typeQuestion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeQuestionMockMvc.perform(post("/api/type-questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeQuestion)))
            .andExpect(status().isBadRequest());

        // Validate the TypeQuestion in the database
        List<TypeQuestion> typeQuestionList = typeQuestionRepository.findAll();
        assertThat(typeQuestionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkiDTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeQuestionRepository.findAll().size();
        // set the field null
        typeQuestion.setiDType(null);

        // Create the TypeQuestion, which fails.

        restTypeQuestionMockMvc.perform(post("/api/type-questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeQuestion)))
            .andExpect(status().isBadRequest());

        List<TypeQuestion> typeQuestionList = typeQuestionRepository.findAll();
        assertThat(typeQuestionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTypeQuestions() throws Exception {
        // Initialize the database
        typeQuestionRepository.saveAndFlush(typeQuestion);

        // Get all the typeQuestionList
        restTypeQuestionMockMvc.perform(get("/api/type-questions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeQuestion.getId().intValue())))
            .andExpect(jsonPath("$.[*].iDType").value(hasItem(DEFAULT_I_D_TYPE.intValue())))
            .andExpect(jsonPath("$.[*].nomType").value(hasItem(DEFAULT_NOM_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getTypeQuestion() throws Exception {
        // Initialize the database
        typeQuestionRepository.saveAndFlush(typeQuestion);

        // Get the typeQuestion
        restTypeQuestionMockMvc.perform(get("/api/type-questions/{id}", typeQuestion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(typeQuestion.getId().intValue()))
            .andExpect(jsonPath("$.iDType").value(DEFAULT_I_D_TYPE.intValue()))
            .andExpect(jsonPath("$.nomType").value(DEFAULT_NOM_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeQuestion() throws Exception {
        // Get the typeQuestion
        restTypeQuestionMockMvc.perform(get("/api/type-questions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeQuestion() throws Exception {
        // Initialize the database
        typeQuestionRepository.saveAndFlush(typeQuestion);

        int databaseSizeBeforeUpdate = typeQuestionRepository.findAll().size();

        // Update the typeQuestion
        TypeQuestion updatedTypeQuestion = typeQuestionRepository.findById(typeQuestion.getId()).get();
        // Disconnect from session so that the updates on updatedTypeQuestion are not directly saved in db
        em.detach(updatedTypeQuestion);
        updatedTypeQuestion
            .iDType(UPDATED_I_D_TYPE)
            .nomType(UPDATED_NOM_TYPE);

        restTypeQuestionMockMvc.perform(put("/api/type-questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeQuestion)))
            .andExpect(status().isOk());

        // Validate the TypeQuestion in the database
        List<TypeQuestion> typeQuestionList = typeQuestionRepository.findAll();
        assertThat(typeQuestionList).hasSize(databaseSizeBeforeUpdate);
        TypeQuestion testTypeQuestion = typeQuestionList.get(typeQuestionList.size() - 1);
        assertThat(testTypeQuestion.getiDType()).isEqualTo(UPDATED_I_D_TYPE);
        assertThat(testTypeQuestion.getNomType()).isEqualTo(UPDATED_NOM_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeQuestion() throws Exception {
        int databaseSizeBeforeUpdate = typeQuestionRepository.findAll().size();

        // Create the TypeQuestion

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeQuestionMockMvc.perform(put("/api/type-questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeQuestion)))
            .andExpect(status().isBadRequest());

        // Validate the TypeQuestion in the database
        List<TypeQuestion> typeQuestionList = typeQuestionRepository.findAll();
        assertThat(typeQuestionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeQuestion() throws Exception {
        // Initialize the database
        typeQuestionRepository.saveAndFlush(typeQuestion);

        int databaseSizeBeforeDelete = typeQuestionRepository.findAll().size();

        // Get the typeQuestion
        restTypeQuestionMockMvc.perform(delete("/api/type-questions/{id}", typeQuestion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TypeQuestion> typeQuestionList = typeQuestionRepository.findAll();
        assertThat(typeQuestionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeQuestion.class);
        TypeQuestion typeQuestion1 = new TypeQuestion();
        typeQuestion1.setId(1L);
        TypeQuestion typeQuestion2 = new TypeQuestion();
        typeQuestion2.setId(typeQuestion1.getId());
        assertThat(typeQuestion1).isEqualTo(typeQuestion2);
        typeQuestion2.setId(2L);
        assertThat(typeQuestion1).isNotEqualTo(typeQuestion2);
        typeQuestion1.setId(null);
        assertThat(typeQuestion1).isNotEqualTo(typeQuestion2);
    }
}
