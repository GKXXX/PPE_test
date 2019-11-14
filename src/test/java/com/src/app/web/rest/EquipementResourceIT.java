package com.src.app.web.rest;

import com.src.app.TestApp;
import com.src.app.domain.Equipement;
import com.src.app.repository.EquipementRepository;
import com.src.app.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.src.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link EquipementResource} REST controller.
 */
@SpringBootTest(classes = TestApp.class)
public class EquipementResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final Long DEFAULT_PRIX_JOUR = 1L;
    private static final Long UPDATED_PRIX_JOUR = 2L;

    @Autowired
    private EquipementRepository equipementRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restEquipementMockMvc;

    private Equipement equipement;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EquipementResource equipementResource = new EquipementResource(equipementRepository);
        this.restEquipementMockMvc = MockMvcBuilders.standaloneSetup(equipementResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Equipement createEntity(EntityManager em) {
        Equipement equipement = new Equipement()
            .type(DEFAULT_TYPE)
            .nom(DEFAULT_NOM)
            .prixJour(DEFAULT_PRIX_JOUR);
        return equipement;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Equipement createUpdatedEntity(EntityManager em) {
        Equipement equipement = new Equipement()
            .type(UPDATED_TYPE)
            .nom(UPDATED_NOM)
            .prixJour(UPDATED_PRIX_JOUR);
        return equipement;
    }

    @BeforeEach
    public void initTest() {
        equipement = createEntity(em);
    }

    @Test
    @Transactional
    public void createEquipement() throws Exception {
        int databaseSizeBeforeCreate = equipementRepository.findAll().size();

        // Create the Equipement
        restEquipementMockMvc.perform(post("/api/equipements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipement)))
            .andExpect(status().isCreated());

        // Validate the Equipement in the database
        List<Equipement> equipementList = equipementRepository.findAll();
        assertThat(equipementList).hasSize(databaseSizeBeforeCreate + 1);
        Equipement testEquipement = equipementList.get(equipementList.size() - 1);
        assertThat(testEquipement.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testEquipement.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testEquipement.getPrixJour()).isEqualTo(DEFAULT_PRIX_JOUR);
    }

    @Test
    @Transactional
    public void createEquipementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = equipementRepository.findAll().size();

        // Create the Equipement with an existing ID
        equipement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEquipementMockMvc.perform(post("/api/equipements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipement)))
            .andExpect(status().isBadRequest());

        // Validate the Equipement in the database
        List<Equipement> equipementList = equipementRepository.findAll();
        assertThat(equipementList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEquipements() throws Exception {
        // Initialize the database
        equipementRepository.saveAndFlush(equipement);

        // Get all the equipementList
        restEquipementMockMvc.perform(get("/api/equipements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(equipement.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prixJour").value(hasItem(DEFAULT_PRIX_JOUR.intValue())));
    }
    
    @Test
    @Transactional
    public void getEquipement() throws Exception {
        // Initialize the database
        equipementRepository.saveAndFlush(equipement);

        // Get the equipement
        restEquipementMockMvc.perform(get("/api/equipements/{id}", equipement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(equipement.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prixJour").value(DEFAULT_PRIX_JOUR.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEquipement() throws Exception {
        // Get the equipement
        restEquipementMockMvc.perform(get("/api/equipements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEquipement() throws Exception {
        // Initialize the database
        equipementRepository.saveAndFlush(equipement);

        int databaseSizeBeforeUpdate = equipementRepository.findAll().size();

        // Update the equipement
        Equipement updatedEquipement = equipementRepository.findById(equipement.getId()).get();
        // Disconnect from session so that the updates on updatedEquipement are not directly saved in db
        em.detach(updatedEquipement);
        updatedEquipement
            .type(UPDATED_TYPE)
            .nom(UPDATED_NOM)
            .prixJour(UPDATED_PRIX_JOUR);

        restEquipementMockMvc.perform(put("/api/equipements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEquipement)))
            .andExpect(status().isOk());

        // Validate the Equipement in the database
        List<Equipement> equipementList = equipementRepository.findAll();
        assertThat(equipementList).hasSize(databaseSizeBeforeUpdate);
        Equipement testEquipement = equipementList.get(equipementList.size() - 1);
        assertThat(testEquipement.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testEquipement.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testEquipement.getPrixJour()).isEqualTo(UPDATED_PRIX_JOUR);
    }

    @Test
    @Transactional
    public void updateNonExistingEquipement() throws Exception {
        int databaseSizeBeforeUpdate = equipementRepository.findAll().size();

        // Create the Equipement

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEquipementMockMvc.perform(put("/api/equipements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipement)))
            .andExpect(status().isBadRequest());

        // Validate the Equipement in the database
        List<Equipement> equipementList = equipementRepository.findAll();
        assertThat(equipementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEquipement() throws Exception {
        // Initialize the database
        equipementRepository.saveAndFlush(equipement);

        int databaseSizeBeforeDelete = equipementRepository.findAll().size();

        // Delete the equipement
        restEquipementMockMvc.perform(delete("/api/equipements/{id}", equipement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Equipement> equipementList = equipementRepository.findAll();
        assertThat(equipementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
