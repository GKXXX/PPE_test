package com.src.app.web.rest;

import com.src.app.domain.Locaux;
import com.src.app.repository.LocauxRepository;
import com.src.app.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.src.app.domain.Locaux}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class LocauxResource {

    private final Logger log = LoggerFactory.getLogger(LocauxResource.class);

    private static final String ENTITY_NAME = "locaux";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LocauxRepository locauxRepository;

    public LocauxResource(LocauxRepository locauxRepository) {
        this.locauxRepository = locauxRepository;
    }

    /**
     * {@code POST  /locauxes} : Create a new locaux.
     *
     * @param locaux the locaux to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new locaux, or with status {@code 400 (Bad Request)} if the locaux has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/locauxes")
    public ResponseEntity<Locaux> createLocaux(@Valid @RequestBody Locaux locaux) throws URISyntaxException {
        log.debug("REST request to save Locaux : {}", locaux);
        if (locaux.getId() != null) {
            throw new BadRequestAlertException("A new locaux cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Locaux result = locauxRepository.save(locaux);
        return ResponseEntity.created(new URI("/api/locauxes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /locauxes} : Updates an existing locaux.
     *
     * @param locaux the locaux to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated locaux,
     * or with status {@code 400 (Bad Request)} if the locaux is not valid,
     * or with status {@code 500 (Internal Server Error)} if the locaux couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/locauxes")
    public ResponseEntity<Locaux> updateLocaux(@Valid @RequestBody Locaux locaux) throws URISyntaxException {
        log.debug("REST request to update Locaux : {}", locaux);
        if (locaux.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Locaux result = locauxRepository.save(locaux);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, locaux.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /locauxes} : get all the locauxes.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of locauxes in body.
     */
    @GetMapping("/locauxes")
    public List<Locaux> getAllLocauxes() {
        log.debug("REST request to get all Locauxes");
        return locauxRepository.findAll();
    }

    /**
     * {@code GET  /locauxes/:id} : get the "id" locaux.
     *
     * @param id the id of the locaux to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the locaux, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/locauxes/{id}")
    public ResponseEntity<Locaux> getLocaux(@PathVariable Long id) {
        log.debug("REST request to get Locaux : {}", id);
        Optional<Locaux> locaux = locauxRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(locaux);
    }

    /**
     * {@code DELETE  /locauxes/:id} : delete the "id" locaux.
     *
     * @param id the id of the locaux to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/locauxes/{id}")
    public ResponseEntity<Void> deleteLocaux(@PathVariable Long id) {
        log.debug("REST request to delete Locaux : {}", id);
        locauxRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
