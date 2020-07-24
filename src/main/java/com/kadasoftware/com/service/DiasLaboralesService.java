package com.kadasoftware.com.service;

import com.kadasoftware.com.domain.DiasLaborales;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing DiasLaborales.
 */
public interface DiasLaboralesService {

    /**
     * Save a diasLaborales.
     *
     * @param diasLaborales the entity to save
     * @return the persisted entity
     */
    DiasLaborales save(DiasLaborales diasLaborales);

    /**
     *  Get all the diasLaborales.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<DiasLaborales> findAll(Pageable pageable);

    /**
     *  Get the "id" diasLaborales.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    DiasLaborales findOne(Long id);

    /**
     *  Delete the "id" diasLaborales.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
