package com.kadasoftware.com.service.impl;

import com.kadasoftware.com.service.DiasLaboralesService;
import com.kadasoftware.com.domain.DiasLaborales;
import com.kadasoftware.com.repository.DiasLaboralesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing DiasLaborales.
 */
@Service
@Transactional
public class DiasLaboralesServiceImpl implements DiasLaboralesService{

    private final Logger log = LoggerFactory.getLogger(DiasLaboralesServiceImpl.class);
    
    private final DiasLaboralesRepository diasLaboralesRepository;

    public DiasLaboralesServiceImpl(DiasLaboralesRepository diasLaboralesRepository) {
        this.diasLaboralesRepository = diasLaboralesRepository;
    }

    /**
     * Save a diasLaborales.
     *
     * @param diasLaborales the entity to save
     * @return the persisted entity
     */
    @Override
    public DiasLaborales save(DiasLaborales diasLaborales) {
        log.debug("Request to save DiasLaborales : {}", diasLaborales);
        DiasLaborales result = diasLaboralesRepository.save(diasLaborales);
        return result;
    }

    /**
     *  Get all the diasLaborales.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DiasLaborales> findAll(Pageable pageable) {
        log.debug("Request to get all DiasLaborales");
        Page<DiasLaborales> result = diasLaboralesRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one diasLaborales by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DiasLaborales findOne(Long id) {
        log.debug("Request to get DiasLaborales : {}", id);
        DiasLaborales diasLaborales = diasLaboralesRepository.findOne(id);
        return diasLaborales;
    }

    /**
     *  Delete the  diasLaborales by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DiasLaborales : {}", id);
        diasLaboralesRepository.delete(id);
    }
}
