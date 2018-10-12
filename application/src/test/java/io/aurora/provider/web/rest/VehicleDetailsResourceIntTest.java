package io.aurora.provider.web.rest;

import io.aurora.provider.JhipsterApp;

import io.aurora.provider.domain.VehicleDetails;
import io.aurora.provider.repository.VehicleDetailsRepository;
import io.aurora.provider.service.VehicleDetailsService;
import io.aurora.provider.service.dto.VehicleDetailsDTO;
import io.aurora.provider.service.mapper.VehicleDetailsMapper;
import io.aurora.provider.web.rest.errors.ExceptionTranslator;

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

import java.util.List;


import static io.aurora.provider.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.aurora.provider.domain.enumeration.Transmission;
import io.aurora.provider.domain.enumeration.Fuel;
/**
 * Test class for the VehicleDetailsResource REST controller.
 *
 * @see VehicleDetailsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterApp.class)
public class VehicleDetailsResourceIntTest {

    private static final String DEFAULT_MANUFACTURER = "AAAAAAAAAA";
    private static final String UPDATED_MANUFACTURER = "BBBBBBBBBB";

    private static final String DEFAULT_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_COLOR = "BBBBBBBBBB";

    private static final Transmission DEFAULT_TRANSMISSION = Transmission.AUTOMATIC;
    private static final Transmission UPDATED_TRANSMISSION = Transmission.MANUAL;

    private static final Fuel DEFAULT_FUEL = Fuel.PETROL;
    private static final Fuel UPDATED_FUEL = Fuel.DIESIL;

    @Autowired
    private VehicleDetailsRepository vehicleDetailsRepository;

    @Autowired
    private VehicleDetailsMapper vehicleDetailsMapper;
    
    @Autowired
    private VehicleDetailsService vehicleDetailsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restVehicleDetailsMockMvc;

    private VehicleDetails vehicleDetails;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VehicleDetailsResource vehicleDetailsResource = new VehicleDetailsResource(vehicleDetailsService);
        this.restVehicleDetailsMockMvc = MockMvcBuilders.standaloneSetup(vehicleDetailsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VehicleDetails createEntity() {
        VehicleDetails vehicleDetails = new VehicleDetails()
            .manufacturer(DEFAULT_MANUFACTURER)
            .color(DEFAULT_COLOR)
            .transmission(DEFAULT_TRANSMISSION)
            .fuel(DEFAULT_FUEL);
        return vehicleDetails;
    }

    @Before
    public void initTest() {
        vehicleDetailsRepository.deleteAll();
        vehicleDetails = createEntity();
    }

    @Test
    public void createVehicleDetails() throws Exception {
        int databaseSizeBeforeCreate = vehicleDetailsRepository.findAll().size();

        // Create the VehicleDetails
        VehicleDetailsDTO vehicleDetailsDTO = vehicleDetailsMapper.toDto(vehicleDetails);
        restVehicleDetailsMockMvc.perform(post("/api/vehicle-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleDetailsDTO)))
            .andExpect(status().isCreated());

        // Validate the VehicleDetails in the database
        List<VehicleDetails> vehicleDetailsList = vehicleDetailsRepository.findAll();
        assertThat(vehicleDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        VehicleDetails testVehicleDetails = vehicleDetailsList.get(vehicleDetailsList.size() - 1);
        assertThat(testVehicleDetails.getManufacturer()).isEqualTo(DEFAULT_MANUFACTURER);
        assertThat(testVehicleDetails.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testVehicleDetails.getTransmission()).isEqualTo(DEFAULT_TRANSMISSION);
        assertThat(testVehicleDetails.getFuel()).isEqualTo(DEFAULT_FUEL);
    }

    @Test
    public void createVehicleDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vehicleDetailsRepository.findAll().size();

        // Create the VehicleDetails with an existing ID
        vehicleDetails.setId("existing_id");
        VehicleDetailsDTO vehicleDetailsDTO = vehicleDetailsMapper.toDto(vehicleDetails);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehicleDetailsMockMvc.perform(post("/api/vehicle-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VehicleDetails in the database
        List<VehicleDetails> vehicleDetailsList = vehicleDetailsRepository.findAll();
        assertThat(vehicleDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllVehicleDetails() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.save(vehicleDetails);

        // Get all the vehicleDetailsList
        restVehicleDetailsMockMvc.perform(get("/api/vehicle-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicleDetails.getId())))
            .andExpect(jsonPath("$.[*].manufacturer").value(hasItem(DEFAULT_MANUFACTURER.toString())))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR.toString())))
            .andExpect(jsonPath("$.[*].transmission").value(hasItem(DEFAULT_TRANSMISSION.toString())))
            .andExpect(jsonPath("$.[*].fuel").value(hasItem(DEFAULT_FUEL.toString())));
    }
    
    @Test
    public void getVehicleDetails() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.save(vehicleDetails);

        // Get the vehicleDetails
        restVehicleDetailsMockMvc.perform(get("/api/vehicle-details/{id}", vehicleDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vehicleDetails.getId()))
            .andExpect(jsonPath("$.manufacturer").value(DEFAULT_MANUFACTURER.toString()))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR.toString()))
            .andExpect(jsonPath("$.transmission").value(DEFAULT_TRANSMISSION.toString()))
            .andExpect(jsonPath("$.fuel").value(DEFAULT_FUEL.toString()));
    }

    @Test
    public void getNonExistingVehicleDetails() throws Exception {
        // Get the vehicleDetails
        restVehicleDetailsMockMvc.perform(get("/api/vehicle-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateVehicleDetails() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.save(vehicleDetails);

        int databaseSizeBeforeUpdate = vehicleDetailsRepository.findAll().size();

        // Update the vehicleDetails
        VehicleDetails updatedVehicleDetails = vehicleDetailsRepository.findById(vehicleDetails.getId()).get();
        updatedVehicleDetails
            .manufacturer(UPDATED_MANUFACTURER)
            .color(UPDATED_COLOR)
            .transmission(UPDATED_TRANSMISSION)
            .fuel(UPDATED_FUEL);
        VehicleDetailsDTO vehicleDetailsDTO = vehicleDetailsMapper.toDto(updatedVehicleDetails);

        restVehicleDetailsMockMvc.perform(put("/api/vehicle-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleDetailsDTO)))
            .andExpect(status().isOk());

        // Validate the VehicleDetails in the database
        List<VehicleDetails> vehicleDetailsList = vehicleDetailsRepository.findAll();
        assertThat(vehicleDetailsList).hasSize(databaseSizeBeforeUpdate);
        VehicleDetails testVehicleDetails = vehicleDetailsList.get(vehicleDetailsList.size() - 1);
        assertThat(testVehicleDetails.getManufacturer()).isEqualTo(UPDATED_MANUFACTURER);
        assertThat(testVehicleDetails.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testVehicleDetails.getTransmission()).isEqualTo(UPDATED_TRANSMISSION);
        assertThat(testVehicleDetails.getFuel()).isEqualTo(UPDATED_FUEL);
    }

    @Test
    public void updateNonExistingVehicleDetails() throws Exception {
        int databaseSizeBeforeUpdate = vehicleDetailsRepository.findAll().size();

        // Create the VehicleDetails
        VehicleDetailsDTO vehicleDetailsDTO = vehicleDetailsMapper.toDto(vehicleDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehicleDetailsMockMvc.perform(put("/api/vehicle-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VehicleDetails in the database
        List<VehicleDetails> vehicleDetailsList = vehicleDetailsRepository.findAll();
        assertThat(vehicleDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteVehicleDetails() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.save(vehicleDetails);

        int databaseSizeBeforeDelete = vehicleDetailsRepository.findAll().size();

        // Get the vehicleDetails
        restVehicleDetailsMockMvc.perform(delete("/api/vehicle-details/{id}", vehicleDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VehicleDetails> vehicleDetailsList = vehicleDetailsRepository.findAll();
        assertThat(vehicleDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleDetails.class);
        VehicleDetails vehicleDetails1 = new VehicleDetails();
        vehicleDetails1.setId("id1");
        VehicleDetails vehicleDetails2 = new VehicleDetails();
        vehicleDetails2.setId(vehicleDetails1.getId());
        assertThat(vehicleDetails1).isEqualTo(vehicleDetails2);
        vehicleDetails2.setId("id2");
        assertThat(vehicleDetails1).isNotEqualTo(vehicleDetails2);
        vehicleDetails1.setId(null);
        assertThat(vehicleDetails1).isNotEqualTo(vehicleDetails2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleDetailsDTO.class);
        VehicleDetailsDTO vehicleDetailsDTO1 = new VehicleDetailsDTO();
        vehicleDetailsDTO1.setId("id1");
        VehicleDetailsDTO vehicleDetailsDTO2 = new VehicleDetailsDTO();
        assertThat(vehicleDetailsDTO1).isNotEqualTo(vehicleDetailsDTO2);
        vehicleDetailsDTO2.setId(vehicleDetailsDTO1.getId());
        assertThat(vehicleDetailsDTO1).isEqualTo(vehicleDetailsDTO2);
        vehicleDetailsDTO2.setId("id2");
        assertThat(vehicleDetailsDTO1).isNotEqualTo(vehicleDetailsDTO2);
        vehicleDetailsDTO1.setId(null);
        assertThat(vehicleDetailsDTO1).isNotEqualTo(vehicleDetailsDTO2);
    }
}
