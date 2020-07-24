package com.kadasoftware.com.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.kadasoftware.com.domain.DiasLaborales;
import com.kadasoftware.com.service.DiasLaboralesService;
import com.kadasoftware.com.web.rest.util.HeaderUtil;
import com.kadasoftware.com.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing DiasLaborales.
 */
@RestController
@RequestMapping("/api")
public class DiasLaboralesResource {

    private final Logger log = LoggerFactory.getLogger(DiasLaboralesResource.class);

    private static final String ENTITY_NAME = "diasLaborales";
        
    private final DiasLaboralesService diasLaboralesService;

    public DiasLaboralesResource(DiasLaboralesService diasLaboralesService) {
        this.diasLaboralesService = diasLaboralesService;
    }

    /**
     * POST  /dias-laborales : Create a new diasLaborales.
     *
     * @param diasLaborales the diasLaborales to create
     * @return the ResponseEntity with status 201 (Created) and with body the new diasLaborales, or with status 400 (Bad Request) if the diasLaborales has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dias-laborales")
    @Timed
    public ResponseEntity<DiasLaborales> createDiasLaborales(@Valid @RequestBody DiasLaborales diasLaborales) throws URISyntaxException {
        log.debug("REST request to save DiasLaborales : {}", diasLaborales);
        if (diasLaborales.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new diasLaborales cannot already have an ID")).body(null);
        }
        DiasLaborales result = diasLaboralesService.save(diasLaborales);
        return ResponseEntity.created(new URI("/api/dias-laborales/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dias-laborales : Updates an existing diasLaborales.
     *
     * @param diasLaborales the diasLaborales to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated diasLaborales,
     * or with status 400 (Bad Request) if the diasLaborales is not valid,
     * or with status 500 (Internal Server Error) if the diasLaborales couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dias-laborales")
    @Timed
    public ResponseEntity<DiasLaborales> updateDiasLaborales(@Valid @RequestBody DiasLaborales diasLaborales) throws URISyntaxException {
        log.debug("REST request to update DiasLaborales : {}", diasLaborales);
        if (diasLaborales.getId() == null) {
            return createDiasLaborales(diasLaborales);
        }
        DiasLaborales result = diasLaboralesService.save(diasLaborales);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, diasLaborales.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dias-laborales : get all the diasLaborales.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of diasLaborales in body
     */
    @GetMapping("/dias-laborales")
    @Timed
    public ResponseEntity<List<DiasLaborales>> getAllDiasLaborales(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of DiasLaborales");
        Page<DiasLaborales> page = diasLaboralesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dias-laborales");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /dias-laborales/:id : get the "id" diasLaborales.
     *
     * @param id the id of the diasLaborales to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the diasLaborales, or with status 404 (Not Found)
     */
    @GetMapping("/dias-laborales/{id}")
    @Timed
    public ResponseEntity<DiasLaborales> getDiasLaborales(@PathVariable Long id) {
        log.debug("REST request to get DiasLaborales : {}", id);
        DiasLaborales diasLaborales = diasLaboralesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(diasLaborales));
    }

    /**
     * DELETE  /dias-laborales/:id : delete the "id" diasLaborales.
     *
     * @param id the id of the diasLaborales to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dias-laborales/{id}")
    @Timed
    public ResponseEntity<Void> deleteDiasLaborales(@PathVariable Long id) {
        log.debug("REST request to delete DiasLaborales : {}", id);
        diasLaboralesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
