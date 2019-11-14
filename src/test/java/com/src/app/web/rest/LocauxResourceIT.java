package com.src.app.web.rest;

import com.src.app.TestApp;
import com.src.app.domain.Locaux;
import com.src.app.repository.LocauxRepository;
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
 * Integration tests for the {@link LocauxResource} REST controller.
 */
@SpringBootTest(classes = TestApp.class)
public class LocauxResourceIT {

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_VILLE = "AAAAAAAAAA";
    private static final String UPDATED_VILLE = "BBBBBBBBBB";

    private static final Long DEFAULT_TELEPHONE = 1L;
    private static final Long UPDATED_TELEPHONE = 2L;

    private static final Long DEFAULT_TAILLE = 1L;
    private static final Long UPDATED_TAILLE = 2L;

    private static final Long DEFAULT_PRIX_JOUR = 1L;
    private static final Long UPDATED_PRIX_JOUR = 2L;

    @Autowired
    private LocauxRepository locauxRepository;

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

    private MockMvc restLocauxMockMvc;

    private Locaux locaux;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LocauxResource locauxResource = new LocauxResource(locauxRepository);
        this.restLocauxMockMvc = MockMvcBuilders.standaloneSetup(locauxResource)
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
    public static Locaux createEntity(EntityManager em) {
        Locaux locaux = new Locaux()
            .adresse(DEFAULT_ADRESSE)
            .ville(DEFAULT_VILLE)
            .telephone(DEFAULT_TELEPHONE)
            .taille(DEFAULT_TAILLE)
            .prixJour(DEFAULT_PRIX_JOUR);
        return locaux;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Locaux createUpdatedEntity(EntityManager em) {
        Locaux locaux = new Locaux()
            .adresse(UPDATED_ADRESSE)
            .ville(UPDATED_VILLE)
            .telephone(UPDATED_TELEPHONE)
            .taille(UPDATED_TAILLE)
            .prixJour(UPDATED_PRIX_JOUR);
        return locaux;
    }

    @BeforeEach
    public void initTest() {
        locaux = createEntity(em);
    }

    @Test
    @Transactional
    public void createLocaux() throws Exception {
        int databaseSizeBeforeCreate = locauxRepository.findAll().size();

        // Create the Locaux
        restLocauxMockMvc.perform(post("/api/locauxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locaux)))
            .andExpect(status().isCreated());

        // Validate the Locaux in the database
        List<Locaux> locauxList = locauxRepository.findAll();
        assertThat(locauxList).hasSize(databaseSizeBeforeCreate + 1);
        Locaux testLocaux = locauxList.get(locauxList.size() - 1);
        assertThat(testLocaux.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testLocaux.getVille()).isEqualTo(DEFAULT_VILLE);
        assertThat(testLocaux.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testLocaux.getTaille()).isEqualTo(DEFAULT_TAILLE);
        assertThat(testLocaux.getPrixJour()).isEqualTo(DEFAULT_PRIX_JOUR);
    }

    @Test
    @Transactional
    public void createLocauxWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = locauxRepository.findAll().size();

        // Create the Locaux with an existing ID
        locaux.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocauxMockMvc.perform(post("/api/locauxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locaux)))
            .andExpect(status().isBadRequest());

        // Validate the Locaux in the database
        List<Locaux> locauxList = locauxRepository.findAll();
        assertThat(locauxList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTelephoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = locauxRepository.findAll().size();
        // set the field null
        locaux.setTelephone(null);

        // Create the Locaux, which fails.

        restLocauxMockMvc.perform(post("/api/locauxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locaux)))
            .andExpect(status().isBadRequest());

        List<Locaux> locauxList = locauxRepository.findAll();
        assertThat(locauxList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLocauxes() throws Exception {
        // Initialize the database
        locauxRepository.saveAndFlush(locaux);

        // Get all the locauxList
        restLocauxMockMvc.perform(get("/api/locauxes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(locaux.getId().intValue())))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE.intValue())))
            .andExpect(jsonPath("$.[*].taille").value(hasItem(DEFAULT_TAILLE.intValue())))
            .andExpect(jsonPath("$.[*].prixJour").value(hasItem(DEFAULT_PRIX_JOUR.intValue())));
    }
    
    @Test
    @Transactional
    public void getLocaux() throws Exception {
        // Initialize the database
        locauxRepository.saveAndFlush(locaux);

        // Get the locaux
        restLocauxMockMvc.perform(get("/api/locauxes/{id}", locaux.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(locaux.getId().intValue()))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE))
            .andExpect(jsonPath("$.ville").value(DEFAULT_VILLE))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE.intValue()))
            .andExpect(jsonPath("$.taille").value(DEFAULT_TAILLE.intValue()))
            .andExpect(jsonPath("$.prixJour").value(DEFAULT_PRIX_JOUR.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLocaux() throws Exception {
        // Get the locaux
        restLocauxMockMvc.perform(get("/api/locauxes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLocaux() throws Exception {
        // Initialize the database
        locauxRepository.saveAndFlush(locaux);

        int databaseSizeBeforeUpdate = locauxRepository.findAll().size();

        // Update the locaux
        Locaux updatedLocaux = locauxRepository.findById(locaux.getId()).get();
        // Disconnect from session so that the updates on updatedLocaux are not directly saved in db
        em.detach(updatedLocaux);
        updatedLocaux
            .adresse(UPDATED_ADRESSE)
            .ville(UPDATED_VILLE)
            .telephone(UPDATED_TELEPHONE)
            .taille(UPDATED_TAILLE)
            .prixJour(UPDATED_PRIX_JOUR);

        restLocauxMockMvc.perform(put("/api/locauxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLocaux)))
            .andExpect(status().isOk());

        // Validate the Locaux in the database
        List<Locaux> locauxList = locauxRepository.findAll();
        assertThat(locauxList).hasSize(databaseSizeBeforeUpdate);
        Locaux testLocaux = locauxList.get(locauxList.size() - 1);
        assertThat(testLocaux.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testLocaux.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testLocaux.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testLocaux.getTaille()).isEqualTo(UPDATED_TAILLE);
        assertThat(testLocaux.getPrixJour()).isEqualTo(UPDATED_PRIX_JOUR);
    }

    @Test
    @Transactional
    public void updateNonExistingLocaux() throws Exception {
        int databaseSizeBeforeUpdate = locauxRepository.findAll().size();

        // Create the Locaux

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocauxMockMvc.perform(put("/api/locauxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locaux)))
            .andExpect(status().isBadRequest());

        // Validate the Locaux in the database
        List<Locaux> locauxList = locauxRepository.findAll();
        assertThat(locauxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLocaux() throws Exception {
        // Initialize the database
        locauxRepository.saveAndFlush(locaux);

        int databaseSizeBeforeDelete = locauxRepository.findAll().size();

        // Delete the locaux
        restLocauxMockMvc.perform(delete("/api/locauxes/{id}", locaux.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Locaux> locauxList = locauxRepository.findAll();
        assertThat(locauxList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
