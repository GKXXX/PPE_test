package com.src.app.web.rest;

import com.src.app.domain.Coaching;
import com.src.app.repository.CoachingRepository;
import com.src.app.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.src.app.domain.Coaching}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CoachingResource {

    private final Logger log = LoggerFactory.getLogger(CoachingResource.class);

    private static final String ENTITY_NAME = "coaching";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CoachingRepository coachingRepository;

    public CoachingResource(CoachingRepository coachingRepository) {
        this.coachingRepository = coachingRepository;
    }

    /**
     * {@code POST  /coachings} : Create a new coaching.
     *
     * @param coaching the coaching to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new coaching, or with status {@code 400 (Bad Request)} if the coaching has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/coachings")
    public ResponseEntity<Coaching> createCoaching(@RequestBody Coaching coaching) throws URISyntaxException {
        log.debug("REST request to save Coaching : {}", coaching);
        if (coaching.getId() != null) {
            throw new BadRequestAlertException("A new coaching cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Coaching result = coachingRepository.save(coaching);
        return ResponseEntity.created(new URI("/api/coachings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /coachings} : Updates an existing coaching.
     *
     * @param coaching the coaching to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated coaching,
     * or with status {@code 400 (Bad Request)} if the coaching is not valid,
     * or with status {@code 500 (Internal Server Error)} if the coaching couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/coachings")
    public ResponseEntity<Coaching> updateCoaching(@RequestBody Coaching coaching) throws URISyntaxException {
        log.debug("REST request to update Coaching : {}", coaching);
        if (coaching.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Coaching result = coachingRepository.save(coaching);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, coaching.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /coachings} : get all the coachings.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of coachings in body.
     */
    @GetMapping("/coachings")
    public List<Coaching> getAllCoachings() {
        log.debug("REST request to get all Coachings");
        return coachingRepository.findAll();
    }

    /**
     * {@code GET  /coachings/:id} : get the "id" coaching.
     *
     * @param id the id of the coaching to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the coaching, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/coachings/{id}")
    public ResponseEntity<Coaching> getCoaching(@PathVariable Long id) {
        log.debug("REST request to get Coaching : {}", id);
        Optional<Coaching> coaching = coachingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(coaching);
    }

    /**
     * {@code DELETE  /coachings/:id} : delete the "id" coaching.
     *
     * @param id the id of the coaching to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/coachings/{id}")
    public ResponseEntity<Void> deleteCoaching(@PathVariable Long id) {
        log.debug("REST request to delete Coaching : {}", id);
        coachingRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
