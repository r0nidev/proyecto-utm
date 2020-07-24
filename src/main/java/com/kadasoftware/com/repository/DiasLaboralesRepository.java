package com.kadasoftware.com.repository;

import com.kadasoftware.com.domain.DiasLaborales;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the DiasLaborales entity.
 */
@SuppressWarnings("unused")
public interface DiasLaboralesRepository extends JpaRepository<DiasLaborales,Long> {

}
