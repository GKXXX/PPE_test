package com.src.app.web.rest;

import com.src.app.domain.Equipement;
import com.src.app.repository.EquipementRepository;
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
 * REST controller for managing {@link com.src.app.domain.Equipement}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EquipementResource {

    private final Logger log = LoggerFactory.getLogger(EquipementResource.class);

    private static final String ENTITY_NAME = "equipement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EquipementRepository equipementRepository;

    public EquipementResource(EquipementRepository equipementRepository) {
        this.equipementRepository = equipementRepository;
    }

    /**
     * {@code POST  /equipements} : Create a new equipement.
     *
     * @param equipement the equipement to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new equipement, or with status {@code 400 (Bad Request)} if the equipement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/equipements")
    public ResponseEntity<Equipement> createEquipement(@RequestBody Equipement equipement) throws URISyntaxException {
        log.debug("REST request to save Equipement : {}", equipement);
        if (equipement.getId() != null) {
            throw new BadRequestAlertException("A new equipement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Equipement result = equipementRepository.save(equipement);
        return ResponseEntity.created(new URI("/api/equipements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /equipements} : Updates an existing equipement.
     *
     * @param equipement the equipement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated equipement,
     * or with status {@code 400 (Bad Request)} if the equipement is not valid,
     * or with status {@code 500 (Internal Server Error)} if the equipement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/equipements")
    public ResponseEntity<Equipement> updateEquipement(@RequestBody Equipement equipement) throws URISyntaxException {
        log.debug("REST request to update Equipement : {}", equipement);
        if (equipement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Equipement result = equipementRepository.save(equipement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, equipement.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /equipements} : get all the equipements.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of equipements in body.
     */
    @GetMapping("/equipements")
    public List<Equipement> getAllEquipements() {
        log.debug("REST request to get all Equipements");
        return equipementRepository.findAll();
    }

    /**
     * {@code GET  /equipements/:id} : get the "id" equipement.
     *
     * @param id the id of the equipement to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the equipement, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/equipements/{id}")
    public ResponseEntity<Equipement> getEquipement(@PathVariable Long id) {
        log.debug("REST request to get Equipement : {}", id);
        Optional<Equipement> equipement = equipementRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(equipement);
    }

    /**
     * {@code DELETE  /equipements/:id} : delete the "id" equipement.
     *
     * @param id the id of the equipement to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/equipements/{id}")
    public ResponseEntity<Void> deleteEquipement(@PathVariable Long id) {
        log.debug("REST request to delete Equipement : {}", id);
        equipementRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
