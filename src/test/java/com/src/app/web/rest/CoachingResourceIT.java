package com.src.app.web.rest;

import com.src.app.TestApp;
import com.src.app.domain.Coaching;
import com.src.app.repository.CoachingRepository;
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
 * Integration tests for the {@link CoachingResource} REST controller.
 */
@SpringBootTest(classes = TestApp.class)
public class CoachingResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_JEU = "AAAAAAAAAA";
    private static final String UPDATED_JEU = "BBBBBBBBBB";

    private static final Long DEFAULT_PRIX = 1L;
    private static final Long UPDATED_PRIX = 2L;

    @Autowired
    private CoachingRepository coachingRepository;

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

    private MockMvc restCoachingMockMvc;

    private Coaching coaching;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CoachingResource coachingResource = new CoachingResource(coachingRepository);
        this.restCoachingMockMvc = MockMvcBuilders.standaloneSetup(coachingResource)
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
    public static Coaching createEntity(EntityManager em) {
        Coaching coaching = new Coaching()
            .type(DEFAULT_TYPE)
            .jeu(DEFAULT_JEU)
            .prix(DEFAULT_PRIX);
        return coaching;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Coaching createUpdatedEntity(EntityManager em) {
        Coaching coaching = new Coaching()
            .type(UPDATED_TYPE)
            .jeu(UPDATED_JEU)
            .prix(UPDATED_PRIX);
        return coaching;
    }

    @BeforeEach
    public void initTest() {
        coaching = createEntity(em);
    }

    @Test
    @Transactional
    public void createCoaching() throws Exception {
        int databaseSizeBeforeCreate = coachingRepository.findAll().size();

        // Create the Coaching
        restCoachingMockMvc.perform(post("/api/coachings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coaching)))
            .andExpect(status().isCreated());

        // Validate the Coaching in the database
        List<Coaching> coachingList = coachingRepository.findAll();
        assertThat(coachingList).hasSize(databaseSizeBeforeCreate + 1);
        Coaching testCoaching = coachingList.get(coachingList.size() - 1);
        assertThat(testCoaching.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testCoaching.getJeu()).isEqualTo(DEFAULT_JEU);
        assertThat(testCoaching.getPrix()).isEqualTo(DEFAULT_PRIX);
    }

    @Test
    @Transactional
    public void createCoachingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = coachingRepository.findAll().size();

        // Create the Coaching with an existing ID
        coaching.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCoachingMockMvc.perform(post("/api/coachings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coaching)))
            .andExpect(status().isBadRequest());

        // Validate the Coaching in the database
        List<Coaching> coachingList = coachingRepository.findAll();
        assertThat(coachingList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCoachings() throws Exception {
        // Initialize the database
        coachingRepository.saveAndFlush(coaching);

        // Get all the coachingList
        restCoachingMockMvc.perform(get("/api/coachings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coaching.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].jeu").value(hasItem(DEFAULT_JEU)))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX.intValue())));
    }
    
    @Test
    @Transactional
    public void getCoaching() throws Exception {
        // Initialize the database
        coachingRepository.saveAndFlush(coaching);

        // Get the coaching
        restCoachingMockMvc.perform(get("/api/coachings/{id}", coaching.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(coaching.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.jeu").value(DEFAULT_JEU))
            .andExpect(jsonPath("$.prix").value(DEFAULT_PRIX.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCoaching() throws Exception {
        // Get the coaching
        restCoachingMockMvc.perform(get("/api/coachings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCoaching() throws Exception {
        // Initialize the database
        coachingRepository.saveAndFlush(coaching);

        int databaseSizeBeforeUpdate = coachingRepository.findAll().size();

        // Update the coaching
        Coaching updatedCoaching = coachingRepository.findById(coaching.getId()).get();
        // Disconnect from session so that the updates on updatedCoaching are not directly saved in db
        em.detach(updatedCoaching);
        updatedCoaching
            .type(UPDATED_TYPE)
            .jeu(UPDATED_JEU)
            .prix(UPDATED_PRIX);

        restCoachingMockMvc.perform(put("/api/coachings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCoaching)))
            .andExpect(status().isOk());

        // Validate the Coaching in the database
        List<Coaching> coachingList = coachingRepository.findAll();
        assertThat(coachingList).hasSize(databaseSizeBeforeUpdate);
        Coaching testCoaching = coachingList.get(coachingList.size() - 1);
        assertThat(testCoaching.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCoaching.getJeu()).isEqualTo(UPDATED_JEU);
        assertThat(testCoaching.getPrix()).isEqualTo(UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void updateNonExistingCoaching() throws Exception {
        int databaseSizeBeforeUpdate = coachingRepository.findAll().size();

        // Create the Coaching

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCoachingMockMvc.perform(put("/api/coachings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coaching)))
            .andExpect(status().isBadRequest());

        // Validate the Coaching in the database
        List<Coaching> coachingList = coachingRepository.findAll();
        assertThat(coachingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCoaching() throws Exception {
        // Initialize the database
        coachingRepository.saveAndFlush(coaching);

        int databaseSizeBeforeDelete = coachingRepository.findAll().size();

        // Delete the coaching
        restCoachingMockMvc.perform(delete("/api/coachings/{id}", coaching.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Coaching> coachingList = coachingRepository.findAll();
        assertThat(coachingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
