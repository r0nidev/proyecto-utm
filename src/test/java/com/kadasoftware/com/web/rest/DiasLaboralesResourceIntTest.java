package com.kadasoftware.com.web.rest;

import com.kadasoftware.com.UtmApp;

import com.kadasoftware.com.domain.DiasLaborales;
import com.kadasoftware.com.repository.DiasLaboralesRepository;
import com.kadasoftware.com.service.DiasLaboralesService;
import com.kadasoftware.com.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DiasLaboralesResource REST controller.
 *
 * @see DiasLaboralesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UtmApp.class)
public class DiasLaboralesResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private DiasLaboralesRepository diasLaboralesRepository;

    @Autowired
    private DiasLaboralesService diasLaboralesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDiasLaboralesMockMvc;

    private DiasLaborales diasLaborales;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DiasLaboralesResource diasLaboralesResource = new DiasLaboralesResource(diasLaboralesService);
        this.restDiasLaboralesMockMvc = MockMvcBuilders.standaloneSetup(diasLaboralesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DiasLaborales createEntity(EntityManager em) {
        DiasLaborales diasLaborales = new DiasLaborales()
            .nombre(DEFAULT_NOMBRE);
        return diasLaborales;
    }

    @Before
    public void initTest() {
        diasLaborales = createEntity(em);
    }

    @Test
    @Transactional
    public void createDiasLaborales() throws Exception {
        int databaseSizeBeforeCreate = diasLaboralesRepository.findAll().size();

        // Create the DiasLaborales
        restDiasLaboralesMockMvc.perform(post("/api/dias-laborales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diasLaborales)))
            .andExpect(status().isCreated());

        // Validate the DiasLaborales in the database
        List<DiasLaborales> diasLaboralesList = diasLaboralesRepository.findAll();
        assertThat(diasLaboralesList).hasSize(databaseSizeBeforeCreate + 1);
        DiasLaborales testDiasLaborales = diasLaboralesList.get(diasLaboralesList.size() - 1);
        assertThat(testDiasLaborales.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createDiasLaboralesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = diasLaboralesRepository.findAll().size();

        // Create the DiasLaborales with an existing ID
        diasLaborales.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDiasLaboralesMockMvc.perform(post("/api/dias-laborales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diasLaborales)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<DiasLaborales> diasLaboralesList = diasLaboralesRepository.findAll();
        assertThat(diasLaboralesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = diasLaboralesRepository.findAll().size();
        // set the field null
        diasLaborales.setNombre(null);

        // Create the DiasLaborales, which fails.

        restDiasLaboralesMockMvc.perform(post("/api/dias-laborales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diasLaborales)))
            .andExpect(status().isBadRequest());

        List<DiasLaborales> diasLaboralesList = diasLaboralesRepository.findAll();
        assertThat(diasLaboralesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDiasLaborales() throws Exception {
        // Initialize the database
        diasLaboralesRepository.saveAndFlush(diasLaborales);

        // Get all the diasLaboralesList
        restDiasLaboralesMockMvc.perform(get("/api/dias-laborales?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(diasLaborales.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }

    @Test
    @Transactional
    public void getDiasLaborales() throws Exception {
        // Initialize the database
        diasLaboralesRepository.saveAndFlush(diasLaborales);

        // Get the diasLaborales
        restDiasLaboralesMockMvc.perform(get("/api/dias-laborales/{id}", diasLaborales.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(diasLaborales.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDiasLaborales() throws Exception {
        // Get the diasLaborales
        restDiasLaboralesMockMvc.perform(get("/api/dias-laborales/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDiasLaborales() throws Exception {
        // Initialize the database
        diasLaboralesService.save(diasLaborales);

        int databaseSizeBeforeUpdate = diasLaboralesRepository.findAll().size();

        // Update the diasLaborales
        DiasLaborales updatedDiasLaborales = diasLaboralesRepository.findOne(diasLaborales.getId());
        updatedDiasLaborales
            .nombre(UPDATED_NOMBRE);

        restDiasLaboralesMockMvc.perform(put("/api/dias-laborales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDiasLaborales)))
            .andExpect(status().isOk());

        // Validate the DiasLaborales in the database
        List<DiasLaborales> diasLaboralesList = diasLaboralesRepository.findAll();
        assertThat(diasLaboralesList).hasSize(databaseSizeBeforeUpdate);
        DiasLaborales testDiasLaborales = diasLaboralesList.get(diasLaboralesList.size() - 1);
        assertThat(testDiasLaborales.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void updateNonExistingDiasLaborales() throws Exception {
        int databaseSizeBeforeUpdate = diasLaboralesRepository.findAll().size();

        // Create the DiasLaborales

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDiasLaboralesMockMvc.perform(put("/api/dias-laborales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diasLaborales)))
            .andExpect(status().isCreated());

        // Validate the DiasLaborales in the database
        List<DiasLaborales> diasLaboralesList = diasLaboralesRepository.findAll();
        assertThat(diasLaboralesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDiasLaborales() throws Exception {
        // Initialize the database
        diasLaboralesService.save(diasLaborales);

        int databaseSizeBeforeDelete = diasLaboralesRepository.findAll().size();

        // Get the diasLaborales
        restDiasLaboralesMockMvc.perform(delete("/api/dias-laborales/{id}", diasLaborales.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DiasLaborales> diasLaboralesList = diasLaboralesRepository.findAll();
        assertThat(diasLaboralesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DiasLaborales.class);
    }
}
