package com.src.app.web.rest;

import com.src.app.TestApp;
import com.src.app.domain.Reservation;
import com.src.app.repository.ReservationRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.src.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ReservationResource} REST controller.
 */
@SpringBootTest(classes = TestApp.class)
public class ReservationResourceIT {

    private static final Long DEFAULT_NUMERO_ID = 1L;
    private static final Long UPDATED_NUMERO_ID = 2L;

    private static final String DEFAULT_EQUIPE = "AAAAAAAAAA";
    private static final String UPDATED_EQUIPE = "BBBBBBBBBB";

    private static final String DEFAULT_COACH = "AAAAAAAAAA";
    private static final String UPDATED_COACH = "BBBBBBBBBB";

    private static final String DEFAULT_LOCAL = "AAAAAAAAAA";
    private static final String UPDATED_LOCAL = "BBBBBBBBBB";

    private static final Long DEFAULT_DUREE = 1L;
    private static final Long UPDATED_DUREE = 2L;

    private static final Long DEFAULT_PRIX_TOTAL = 1L;
    private static final Long UPDATED_PRIX_TOTAL = 2L;

    private static final LocalDate DEFAULT_DATE_DEBUT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEBUT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ReservationRepository reservationRepository;

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

    private MockMvc restReservationMockMvc;

    private Reservation reservation;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReservationResource reservationResource = new ReservationResource(reservationRepository);
        this.restReservationMockMvc = MockMvcBuilders.standaloneSetup(reservationResource)
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
    public static Reservation createEntity(EntityManager em) {
        Reservation reservation = new Reservation()
            .numeroID(DEFAULT_NUMERO_ID)
            .equipe(DEFAULT_EQUIPE)
            .coach(DEFAULT_COACH)
            .local(DEFAULT_LOCAL)
            .duree(DEFAULT_DUREE)
            .prixTotal(DEFAULT_PRIX_TOTAL)
            .dateDebut(DEFAULT_DATE_DEBUT);
        return reservation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reservation createUpdatedEntity(EntityManager em) {
        Reservation reservation = new Reservation()
            .numeroID(UPDATED_NUMERO_ID)
            .equipe(UPDATED_EQUIPE)
            .coach(UPDATED_COACH)
            .local(UPDATED_LOCAL)
            .duree(UPDATED_DUREE)
            .prixTotal(UPDATED_PRIX_TOTAL)
            .dateDebut(UPDATED_DATE_DEBUT);
        return reservation;
    }

    @BeforeEach
    public void initTest() {
        reservation = createEntity(em);
    }

    @Test
    @Transactional
    public void createReservation() throws Exception {
        int databaseSizeBeforeCreate = reservationRepository.findAll().size();

        // Create the Reservation
        restReservationMockMvc.perform(post("/api/reservations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reservation)))
            .andExpect(status().isCreated());

        // Validate the Reservation in the database
        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(databaseSizeBeforeCreate + 1);
        Reservation testReservation = reservationList.get(reservationList.size() - 1);
        assertThat(testReservation.getNumeroID()).isEqualTo(DEFAULT_NUMERO_ID);
        assertThat(testReservation.getEquipe()).isEqualTo(DEFAULT_EQUIPE);
        assertThat(testReservation.getCoach()).isEqualTo(DEFAULT_COACH);
        assertThat(testReservation.getLocal()).isEqualTo(DEFAULT_LOCAL);
        assertThat(testReservation.getDuree()).isEqualTo(DEFAULT_DUREE);
        assertThat(testReservation.getPrixTotal()).isEqualTo(DEFAULT_PRIX_TOTAL);
        assertThat(testReservation.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
    }

    @Test
    @Transactional
    public void createReservationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reservationRepository.findAll().size();

        // Create the Reservation with an existing ID
        reservation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReservationMockMvc.perform(post("/api/reservations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reservation)))
            .andExpect(status().isBadRequest());

        // Validate the Reservation in the database
        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNumeroIDIsRequired() throws Exception {
        int databaseSizeBeforeTest = reservationRepository.findAll().size();
        // set the field null
        reservation.setNumeroID(null);

        // Create the Reservation, which fails.

        restReservationMockMvc.perform(post("/api/reservations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reservation)))
            .andExpect(status().isBadRequest());

        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReservations() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);

        // Get all the reservationList
        restReservationMockMvc.perform(get("/api/reservations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reservation.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroID").value(hasItem(DEFAULT_NUMERO_ID.intValue())))
            .andExpect(jsonPath("$.[*].equipe").value(hasItem(DEFAULT_EQUIPE)))
            .andExpect(jsonPath("$.[*].coach").value(hasItem(DEFAULT_COACH)))
            .andExpect(jsonPath("$.[*].local").value(hasItem(DEFAULT_LOCAL)))
            .andExpect(jsonPath("$.[*].duree").value(hasItem(DEFAULT_DUREE.intValue())))
            .andExpect(jsonPath("$.[*].prixTotal").value(hasItem(DEFAULT_PRIX_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT.toString())));
    }
    
    @Test
    @Transactional
    public void getReservation() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);

        // Get the reservation
        restReservationMockMvc.perform(get("/api/reservations/{id}", reservation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(reservation.getId().intValue()))
            .andExpect(jsonPath("$.numeroID").value(DEFAULT_NUMERO_ID.intValue()))
            .andExpect(jsonPath("$.equipe").value(DEFAULT_EQUIPE))
            .andExpect(jsonPath("$.coach").value(DEFAULT_COACH))
            .andExpect(jsonPath("$.local").value(DEFAULT_LOCAL))
            .andExpect(jsonPath("$.duree").value(DEFAULT_DUREE.intValue()))
            .andExpect(jsonPath("$.prixTotal").value(DEFAULT_PRIX_TOTAL.intValue()))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReservation() throws Exception {
        // Get the reservation
        restReservationMockMvc.perform(get("/api/reservations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReservation() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);

        int databaseSizeBeforeUpdate = reservationRepository.findAll().size();

        // Update the reservation
        Reservation updatedReservation = reservationRepository.findById(reservation.getId()).get();
        // Disconnect from session so that the updates on updatedReservation are not directly saved in db
        em.detach(updatedReservation);
        updatedReservation
            .numeroID(UPDATED_NUMERO_ID)
            .equipe(UPDATED_EQUIPE)
            .coach(UPDATED_COACH)
            .local(UPDATED_LOCAL)
            .duree(UPDATED_DUREE)
            .prixTotal(UPDATED_PRIX_TOTAL)
            .dateDebut(UPDATED_DATE_DEBUT);

        restReservationMockMvc.perform(put("/api/reservations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedReservation)))
            .andExpect(status().isOk());

        // Validate the Reservation in the database
        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(databaseSizeBeforeUpdate);
        Reservation testReservation = reservationList.get(reservationList.size() - 1);
        assertThat(testReservation.getNumeroID()).isEqualTo(UPDATED_NUMERO_ID);
        assertThat(testReservation.getEquipe()).isEqualTo(UPDATED_EQUIPE);
        assertThat(testReservation.getCoach()).isEqualTo(UPDATED_COACH);
        assertThat(testReservation.getLocal()).isEqualTo(UPDATED_LOCAL);
        assertThat(testReservation.getDuree()).isEqualTo(UPDATED_DUREE);
        assertThat(testReservation.getPrixTotal()).isEqualTo(UPDATED_PRIX_TOTAL);
        assertThat(testReservation.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
    }

    @Test
    @Transactional
    public void updateNonExistingReservation() throws Exception {
        int databaseSizeBeforeUpdate = reservationRepository.findAll().size();

        // Create the Reservation

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReservationMockMvc.perform(put("/api/reservations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reservation)))
            .andExpect(status().isBadRequest());

        // Validate the Reservation in the database
        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReservation() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);

        int databaseSizeBeforeDelete = reservationRepository.findAll().size();

        // Delete the reservation
        restReservationMockMvc.perform(delete("/api/reservations/{id}", reservation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
