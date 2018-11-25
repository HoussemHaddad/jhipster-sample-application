package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.TypeDeNotification;
import io.github.jhipster.application.repository.TypeDeNotificationRepository;
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
 * Test class for the TypeDeNotificationResource REST controller.
 *
 * @see TypeDeNotificationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class TypeDeNotificationResourceIntTest {

    private static final Long DEFAULT_I_D_TYPE = 1L;
    private static final Long UPDATED_I_D_TYPE = 2L;

    private static final String DEFAULT_NOM_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_TYPE = "BBBBBBBBBB";

    @Autowired
    private TypeDeNotificationRepository typeDeNotificationRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTypeDeNotificationMockMvc;

    private TypeDeNotification typeDeNotification;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeDeNotificationResource typeDeNotificationResource = new TypeDeNotificationResource(typeDeNotificationRepository);
        this.restTypeDeNotificationMockMvc = MockMvcBuilders.standaloneSetup(typeDeNotificationResource)
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
    public static TypeDeNotification createEntity(EntityManager em) {
        TypeDeNotification typeDeNotification = new TypeDeNotification()
            .iDType(DEFAULT_I_D_TYPE)
            .nomType(DEFAULT_NOM_TYPE);
        return typeDeNotification;
    }

    @Before
    public void initTest() {
        typeDeNotification = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeDeNotification() throws Exception {
        int databaseSizeBeforeCreate = typeDeNotificationRepository.findAll().size();

        // Create the TypeDeNotification
        restTypeDeNotificationMockMvc.perform(post("/api/type-de-notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeDeNotification)))
            .andExpect(status().isCreated());

        // Validate the TypeDeNotification in the database
        List<TypeDeNotification> typeDeNotificationList = typeDeNotificationRepository.findAll();
        assertThat(typeDeNotificationList).hasSize(databaseSizeBeforeCreate + 1);
        TypeDeNotification testTypeDeNotification = typeDeNotificationList.get(typeDeNotificationList.size() - 1);
        assertThat(testTypeDeNotification.getiDType()).isEqualTo(DEFAULT_I_D_TYPE);
        assertThat(testTypeDeNotification.getNomType()).isEqualTo(DEFAULT_NOM_TYPE);
    }

    @Test
    @Transactional
    public void createTypeDeNotificationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeDeNotificationRepository.findAll().size();

        // Create the TypeDeNotification with an existing ID
        typeDeNotification.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeDeNotificationMockMvc.perform(post("/api/type-de-notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeDeNotification)))
            .andExpect(status().isBadRequest());

        // Validate the TypeDeNotification in the database
        List<TypeDeNotification> typeDeNotificationList = typeDeNotificationRepository.findAll();
        assertThat(typeDeNotificationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTypeDeNotifications() throws Exception {
        // Initialize the database
        typeDeNotificationRepository.saveAndFlush(typeDeNotification);

        // Get all the typeDeNotificationList
        restTypeDeNotificationMockMvc.perform(get("/api/type-de-notifications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeDeNotification.getId().intValue())))
            .andExpect(jsonPath("$.[*].iDType").value(hasItem(DEFAULT_I_D_TYPE.intValue())))
            .andExpect(jsonPath("$.[*].nomType").value(hasItem(DEFAULT_NOM_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getTypeDeNotification() throws Exception {
        // Initialize the database
        typeDeNotificationRepository.saveAndFlush(typeDeNotification);

        // Get the typeDeNotification
        restTypeDeNotificationMockMvc.perform(get("/api/type-de-notifications/{id}", typeDeNotification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(typeDeNotification.getId().intValue()))
            .andExpect(jsonPath("$.iDType").value(DEFAULT_I_D_TYPE.intValue()))
            .andExpect(jsonPath("$.nomType").value(DEFAULT_NOM_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeDeNotification() throws Exception {
        // Get the typeDeNotification
        restTypeDeNotificationMockMvc.perform(get("/api/type-de-notifications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeDeNotification() throws Exception {
        // Initialize the database
        typeDeNotificationRepository.saveAndFlush(typeDeNotification);

        int databaseSizeBeforeUpdate = typeDeNotificationRepository.findAll().size();

        // Update the typeDeNotification
        TypeDeNotification updatedTypeDeNotification = typeDeNotificationRepository.findById(typeDeNotification.getId()).get();
        // Disconnect from session so that the updates on updatedTypeDeNotification are not directly saved in db
        em.detach(updatedTypeDeNotification);
        updatedTypeDeNotification
            .iDType(UPDATED_I_D_TYPE)
            .nomType(UPDATED_NOM_TYPE);

        restTypeDeNotificationMockMvc.perform(put("/api/type-de-notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeDeNotification)))
            .andExpect(status().isOk());

        // Validate the TypeDeNotification in the database
        List<TypeDeNotification> typeDeNotificationList = typeDeNotificationRepository.findAll();
        assertThat(typeDeNotificationList).hasSize(databaseSizeBeforeUpdate);
        TypeDeNotification testTypeDeNotification = typeDeNotificationList.get(typeDeNotificationList.size() - 1);
        assertThat(testTypeDeNotification.getiDType()).isEqualTo(UPDATED_I_D_TYPE);
        assertThat(testTypeDeNotification.getNomType()).isEqualTo(UPDATED_NOM_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeDeNotification() throws Exception {
        int databaseSizeBeforeUpdate = typeDeNotificationRepository.findAll().size();

        // Create the TypeDeNotification

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeDeNotificationMockMvc.perform(put("/api/type-de-notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeDeNotification)))
            .andExpect(status().isBadRequest());

        // Validate the TypeDeNotification in the database
        List<TypeDeNotification> typeDeNotificationList = typeDeNotificationRepository.findAll();
        assertThat(typeDeNotificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeDeNotification() throws Exception {
        // Initialize the database
        typeDeNotificationRepository.saveAndFlush(typeDeNotification);

        int databaseSizeBeforeDelete = typeDeNotificationRepository.findAll().size();

        // Get the typeDeNotification
        restTypeDeNotificationMockMvc.perform(delete("/api/type-de-notifications/{id}", typeDeNotification.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TypeDeNotification> typeDeNotificationList = typeDeNotificationRepository.findAll();
        assertThat(typeDeNotificationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeDeNotification.class);
        TypeDeNotification typeDeNotification1 = new TypeDeNotification();
        typeDeNotification1.setId(1L);
        TypeDeNotification typeDeNotification2 = new TypeDeNotification();
        typeDeNotification2.setId(typeDeNotification1.getId());
        assertThat(typeDeNotification1).isEqualTo(typeDeNotification2);
        typeDeNotification2.setId(2L);
        assertThat(typeDeNotification1).isNotEqualTo(typeDeNotification2);
        typeDeNotification1.setId(null);
        assertThat(typeDeNotification1).isNotEqualTo(typeDeNotification2);
    }
}
