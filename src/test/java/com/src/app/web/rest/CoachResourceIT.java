package com.src.app.web.rest;

import com.src.app.TestApp;
import com.src.app.domain.Coach;
import com.src.app.repository.CoachRepository;
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
 * Integration tests for the {@link CoachResource} REST controller.
 */
@SpringBootTest(classes = TestApp.class)
public class CoachResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_JEU = "AAAAAAAAAA";
    private static final String UPDATED_JEU = "BBBBBBBBBB";

    private static final Long DEFAULT_PRIX_JOUR = 1L;
    private static final Long UPDATED_PRIX_JOUR = 2L;

    private static final Long DEFAULT_TELEPHONE = 1L;
    private static final Long UPDATED_TELEPHONE = 2L;

    private static final Boolean DEFAULT_DISPO = false;
    private static final Boolean UPDATED_DISPO = true;

    @Autowired
    private CoachRepository coachRepository;

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

    private MockMvc restCoachMockMvc;

    private Coach coach;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CoachResource coachResource = new CoachResource(coachRepository);
        this.restCoachMockMvc = MockMvcBuilders.standaloneSetup(coachResource)
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
    public static Coach createEntity(EntityManager em) {
        Coach coach = new Coach()
            .nom(DEFAULT_NOM)
            .jeu(DEFAULT_JEU)
            .prixJour(DEFAULT_PRIX_JOUR)
            .telephone(DEFAULT_TELEPHONE)
            .dispo(DEFAULT_DISPO);
        return coach;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Coach createUpdatedEntity(EntityManager em) {
        Coach coach = new Coach()
            .nom(UPDATED_NOM)
            .jeu(UPDATED_JEU)
            .prixJour(UPDATED_PRIX_JOUR)
            .telephone(UPDATED_TELEPHONE)
            .dispo(UPDATED_DISPO);
        return coach;
    }

    @BeforeEach
    public void initTest() {
        coach = createEntity(em);
    }

    @Test
    @Transactional
    public void createCoach() throws Exception {
        int databaseSizeBeforeCreate = coachRepository.findAll().size();

        // Create the Coach
        restCoachMockMvc.perform(post("/api/coaches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coach)))
            .andExpect(status().isCreated());

        // Validate the Coach in the database
        List<Coach> coachList = coachRepository.findAll();
        assertThat(coachList).hasSize(databaseSizeBeforeCreate + 1);
        Coach testCoach = coachList.get(coachList.size() - 1);
        assertThat(testCoach.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testCoach.getJeu()).isEqualTo(DEFAULT_JEU);
        assertThat(testCoach.getPrixJour()).isEqualTo(DEFAULT_PRIX_JOUR);
        assertThat(testCoach.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testCoach.isDispo()).isEqualTo(DEFAULT_DISPO);
    }

    @Test
    @Transactional
    public void createCoachWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = coachRepository.findAll().size();

        // Create the Coach with an existing ID
        coach.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCoachMockMvc.perform(post("/api/coaches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coach)))
            .andExpect(status().isBadRequest());

        // Validate the Coach in the database
        List<Coach> coachList = coachRepository.findAll();
        assertThat(coachList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTelephoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = coachRepository.findAll().size();
        // set the field null
        coach.setTelephone(null);

        // Create the Coach, which fails.

        restCoachMockMvc.perform(post("/api/coaches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coach)))
            .andExpect(status().isBadRequest());

        List<Coach> coachList = coachRepository.findAll();
        assertThat(coachList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCoaches() throws Exception {
        // Initialize the database
        coachRepository.saveAndFlush(coach);

        // Get all the coachList
        restCoachMockMvc.perform(get("/api/coaches?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coach.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].jeu").value(hasItem(DEFAULT_JEU)))
            .andExpect(jsonPath("$.[*].prixJour").value(hasItem(DEFAULT_PRIX_JOUR.intValue())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE.intValue())))
            .andExpect(jsonPath("$.[*].dispo").value(hasItem(DEFAULT_DISPO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCoach() throws Exception {
        // Initialize the database
        coachRepository.saveAndFlush(coach);

        // Get the coach
        restCoachMockMvc.perform(get("/api/coaches/{id}", coach.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(coach.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.jeu").value(DEFAULT_JEU))
            .andExpect(jsonPath("$.prixJour").value(DEFAULT_PRIX_JOUR.intValue()))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE.intValue()))
            .andExpect(jsonPath("$.dispo").value(DEFAULT_DISPO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCoach() throws Exception {
        // Get the coach
        restCoachMockMvc.perform(get("/api/coaches/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCoach() throws Exception {
        // Initialize the database
        coachRepository.saveAndFlush(coach);

        int databaseSizeBeforeUpdate = coachRepository.findAll().size();

        // Update the coach
        Coach updatedCoach = coachRepository.findById(coach.getId()).get();
        // Disconnect from session so that the updates on updatedCoach are not directly saved in db
        em.detach(updatedCoach);
        updatedCoach
            .nom(UPDATED_NOM)
            .jeu(UPDATED_JEU)
            .prixJour(UPDATED_PRIX_JOUR)
            .telephone(UPDATED_TELEPHONE)
            .dispo(UPDATED_DISPO);

        restCoachMockMvc.perform(put("/api/coaches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCoach)))
            .andExpect(status().isOk());

        // Validate the Coach in the database
        List<Coach> coachList = coachRepository.findAll();
        assertThat(coachList).hasSize(databaseSizeBeforeUpdate);
        Coach testCoach = coachList.get(coachList.size() - 1);
        assertThat(testCoach.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testCoach.getJeu()).isEqualTo(UPDATED_JEU);
        assertThat(testCoach.getPrixJour()).isEqualTo(UPDATED_PRIX_JOUR);
        assertThat(testCoach.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testCoach.isDispo()).isEqualTo(UPDATED_DISPO);
    }

    @Test
    @Transactional
    public void updateNonExistingCoach() throws Exception {
        int databaseSizeBeforeUpdate = coachRepository.findAll().size();

        // Create the Coach

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCoachMockMvc.perform(put("/api/coaches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coach)))
            .andExpect(status().isBadRequest());

        // Validate the Coach in the database
        List<Coach> coachList = coachRepository.findAll();
        assertThat(coachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCoach() throws Exception {
        // Initialize the database
        coachRepository.saveAndFlush(coach);

        int databaseSizeBeforeDelete = coachRepository.findAll().size();

        // Delete the coach
        restCoachMockMvc.perform(delete("/api/coaches/{id}", coach.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Coach> coachList = coachRepository.findAll();
        assertThat(coachList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
