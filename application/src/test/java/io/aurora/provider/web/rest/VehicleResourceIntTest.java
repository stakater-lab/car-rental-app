package io.aurora.provider.web.rest;

import io.aurora.provider.JhipsterApp;

import io.aurora.provider.domain.Vehicle;
import io.aurora.provider.repository.VehicleRepository;
import io.aurora.provider.service.VehicleService;
import io.aurora.provider.service.dto.VehicleDTO;
import io.aurora.provider.service.mapper.VehicleMapper;
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
import org.springframework.util.Base64Utils;

import java.util.List;


import static io.aurora.provider.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.aurora.provider.domain.enumeration.VehicleAvailability;
import io.aurora.provider.domain.enumeration.DriverAvailability;
/**
 * Test class for the VehicleResource REST controller.
 *
 * @see VehicleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterApp.class)
public class VehicleResourceIntTest {

    private static final Integer DEFAULT_OWNER_ID = 1;
    private static final Integer UPDATED_OWNER_ID = 2;

    private static final VehicleAvailability DEFAULT_AVAILABILITY = VehicleAvailability.AVAILABLE;
    private static final VehicleAvailability UPDATED_AVAILABILITY = VehicleAvailability.BOOKED;

    private static final DriverAvailability DEFAULT_DRIVER = DriverAvailability.WITH;
    private static final DriverAvailability UPDATED_DRIVER = DriverAvailability.WITH_OUT;

    private static final byte[] DEFAULT_IMAGES = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGES = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGES_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGES_CONTENT_TYPE = "image/png";

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private VehicleMapper vehicleMapper;
    
    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restVehicleMockMvc;

    private Vehicle vehicle;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VehicleResource vehicleResource = new VehicleResource(vehicleService);
        this.restVehicleMockMvc = MockMvcBuilders.standaloneSetup(vehicleResource)
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
    public static Vehicle createEntity() {
        Vehicle vehicle = new Vehicle()
            .ownerId(DEFAULT_OWNER_ID)
            .availability(DEFAULT_AVAILABILITY)
            .driver(DEFAULT_DRIVER)
            .images(DEFAULT_IMAGES)
            .imagesContentType(DEFAULT_IMAGES_CONTENT_TYPE);
        return vehicle;
    }

    @Before
    public void initTest() {
        vehicleRepository.deleteAll();
        vehicle = createEntity();
    }

    @Test
    public void createVehicle() throws Exception {
        int databaseSizeBeforeCreate = vehicleRepository.findAll().size();

        // Create the Vehicle
        VehicleDTO vehicleDTO = vehicleMapper.toDto(vehicle);
        restVehicleMockMvc.perform(post("/api/vehicles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleDTO)))
            .andExpect(status().isCreated());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeCreate + 1);
        Vehicle testVehicle = vehicleList.get(vehicleList.size() - 1);
        assertThat(testVehicle.getOwnerId()).isEqualTo(DEFAULT_OWNER_ID);
        assertThat(testVehicle.getAvailability()).isEqualTo(DEFAULT_AVAILABILITY);
        assertThat(testVehicle.getDriver()).isEqualTo(DEFAULT_DRIVER);
        assertThat(testVehicle.getImages()).isEqualTo(DEFAULT_IMAGES);
        assertThat(testVehicle.getImagesContentType()).isEqualTo(DEFAULT_IMAGES_CONTENT_TYPE);
    }

    @Test
    public void createVehicleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vehicleRepository.findAll().size();

        // Create the Vehicle with an existing ID
        vehicle.setId("existing_id");
        VehicleDTO vehicleDTO = vehicleMapper.toDto(vehicle);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehicleMockMvc.perform(post("/api/vehicles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllVehicles() throws Exception {
        // Initialize the database
        vehicleRepository.save(vehicle);

        // Get all the vehicleList
        restVehicleMockMvc.perform(get("/api/vehicles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicle.getId())))
            .andExpect(jsonPath("$.[*].ownerId").value(hasItem(DEFAULT_OWNER_ID)))
            .andExpect(jsonPath("$.[*].availability").value(hasItem(DEFAULT_AVAILABILITY.toString())))
            .andExpect(jsonPath("$.[*].driver").value(hasItem(DEFAULT_DRIVER.toString())))
            .andExpect(jsonPath("$.[*].imagesContentType").value(hasItem(DEFAULT_IMAGES_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].images").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGES))));
    }
    
    @Test
    public void getVehicle() throws Exception {
        // Initialize the database
        vehicleRepository.save(vehicle);

        // Get the vehicle
        restVehicleMockMvc.perform(get("/api/vehicles/{id}", vehicle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vehicle.getId()))
            .andExpect(jsonPath("$.ownerId").value(DEFAULT_OWNER_ID))
            .andExpect(jsonPath("$.availability").value(DEFAULT_AVAILABILITY.toString()))
            .andExpect(jsonPath("$.driver").value(DEFAULT_DRIVER.toString()))
            .andExpect(jsonPath("$.imagesContentType").value(DEFAULT_IMAGES_CONTENT_TYPE))
            .andExpect(jsonPath("$.images").value(Base64Utils.encodeToString(DEFAULT_IMAGES)));
    }

    @Test
    public void getNonExistingVehicle() throws Exception {
        // Get the vehicle
        restVehicleMockMvc.perform(get("/api/vehicles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateVehicle() throws Exception {
        // Initialize the database
        vehicleRepository.save(vehicle);

        int databaseSizeBeforeUpdate = vehicleRepository.findAll().size();

        // Update the vehicle
        Vehicle updatedVehicle = vehicleRepository.findById(vehicle.getId()).get();
        updatedVehicle
            .ownerId(UPDATED_OWNER_ID)
            .availability(UPDATED_AVAILABILITY)
            .driver(UPDATED_DRIVER)
            .images(UPDATED_IMAGES)
            .imagesContentType(UPDATED_IMAGES_CONTENT_TYPE);
        VehicleDTO vehicleDTO = vehicleMapper.toDto(updatedVehicle);

        restVehicleMockMvc.perform(put("/api/vehicles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleDTO)))
            .andExpect(status().isOk());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeUpdate);
        Vehicle testVehicle = vehicleList.get(vehicleList.size() - 1);
        assertThat(testVehicle.getOwnerId()).isEqualTo(UPDATED_OWNER_ID);
        assertThat(testVehicle.getAvailability()).isEqualTo(UPDATED_AVAILABILITY);
        assertThat(testVehicle.getDriver()).isEqualTo(UPDATED_DRIVER);
        assertThat(testVehicle.getImages()).isEqualTo(UPDATED_IMAGES);
        assertThat(testVehicle.getImagesContentType()).isEqualTo(UPDATED_IMAGES_CONTENT_TYPE);
    }

    @Test
    public void updateNonExistingVehicle() throws Exception {
        int databaseSizeBeforeUpdate = vehicleRepository.findAll().size();

        // Create the Vehicle
        VehicleDTO vehicleDTO = vehicleMapper.toDto(vehicle);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehicleMockMvc.perform(put("/api/vehicles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteVehicle() throws Exception {
        // Initialize the database
        vehicleRepository.save(vehicle);

        int databaseSizeBeforeDelete = vehicleRepository.findAll().size();

        // Get the vehicle
        restVehicleMockMvc.perform(delete("/api/vehicles/{id}", vehicle.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vehicle.class);
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setId("id1");
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setId(vehicle1.getId());
        assertThat(vehicle1).isEqualTo(vehicle2);
        vehicle2.setId("id2");
        assertThat(vehicle1).isNotEqualTo(vehicle2);
        vehicle1.setId(null);
        assertThat(vehicle1).isNotEqualTo(vehicle2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleDTO.class);
        VehicleDTO vehicleDTO1 = new VehicleDTO();
        vehicleDTO1.setId("id1");
        VehicleDTO vehicleDTO2 = new VehicleDTO();
        assertThat(vehicleDTO1).isNotEqualTo(vehicleDTO2);
        vehicleDTO2.setId(vehicleDTO1.getId());
        assertThat(vehicleDTO1).isEqualTo(vehicleDTO2);
        vehicleDTO2.setId("id2");
        assertThat(vehicleDTO1).isNotEqualTo(vehicleDTO2);
        vehicleDTO1.setId(null);
        assertThat(vehicleDTO1).isNotEqualTo(vehicleDTO2);
    }
}
