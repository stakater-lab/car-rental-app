package io.aurora.provider.web.rest;

import io.aurora.provider.JhipsterApp;

import io.aurora.provider.domain.VehicleType;
import io.aurora.provider.repository.VehicleTypeRepository;
import io.aurora.provider.service.VehicleTypeService;
import io.aurora.provider.service.dto.VehicleTypeDTO;
import io.aurora.provider.service.mapper.VehicleTypeMapper;
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

/**
 * Test class for the VehicleTypeResource REST controller.
 *
 * @see VehicleTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterApp.class)
public class VehicleTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private VehicleTypeRepository vehicleTypeRepository;

    @Autowired
    private VehicleTypeMapper vehicleTypeMapper;
    
    @Autowired
    private VehicleTypeService vehicleTypeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restVehicleTypeMockMvc;

    private VehicleType vehicleType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VehicleTypeResource vehicleTypeResource = new VehicleTypeResource(vehicleTypeService);
        this.restVehicleTypeMockMvc = MockMvcBuilders.standaloneSetup(vehicleTypeResource)
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
    public static VehicleType createEntity() {
        VehicleType vehicleType = new VehicleType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return vehicleType;
    }

    @Before
    public void initTest() {
        vehicleTypeRepository.deleteAll();
        vehicleType = createEntity();
    }

    @Test
    public void createVehicleType() throws Exception {
        int databaseSizeBeforeCreate = vehicleTypeRepository.findAll().size();

        // Create the VehicleType
        VehicleTypeDTO vehicleTypeDTO = vehicleTypeMapper.toDto(vehicleType);
        restVehicleTypeMockMvc.perform(post("/api/vehicle-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the VehicleType in the database
        List<VehicleType> vehicleTypeList = vehicleTypeRepository.findAll();
        assertThat(vehicleTypeList).hasSize(databaseSizeBeforeCreate + 1);
        VehicleType testVehicleType = vehicleTypeList.get(vehicleTypeList.size() - 1);
        assertThat(testVehicleType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVehicleType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    public void createVehicleTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vehicleTypeRepository.findAll().size();

        // Create the VehicleType with an existing ID
        vehicleType.setId("existing_id");
        VehicleTypeDTO vehicleTypeDTO = vehicleTypeMapper.toDto(vehicleType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehicleTypeMockMvc.perform(post("/api/vehicle-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VehicleType in the database
        List<VehicleType> vehicleTypeList = vehicleTypeRepository.findAll();
        assertThat(vehicleTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllVehicleTypes() throws Exception {
        // Initialize the database
        vehicleTypeRepository.save(vehicleType);

        // Get all the vehicleTypeList
        restVehicleTypeMockMvc.perform(get("/api/vehicle-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicleType.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    public void getVehicleType() throws Exception {
        // Initialize the database
        vehicleTypeRepository.save(vehicleType);

        // Get the vehicleType
        restVehicleTypeMockMvc.perform(get("/api/vehicle-types/{id}", vehicleType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vehicleType.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    public void getNonExistingVehicleType() throws Exception {
        // Get the vehicleType
        restVehicleTypeMockMvc.perform(get("/api/vehicle-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateVehicleType() throws Exception {
        // Initialize the database
        vehicleTypeRepository.save(vehicleType);

        int databaseSizeBeforeUpdate = vehicleTypeRepository.findAll().size();

        // Update the vehicleType
        VehicleType updatedVehicleType = vehicleTypeRepository.findById(vehicleType.getId()).get();
        updatedVehicleType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        VehicleTypeDTO vehicleTypeDTO = vehicleTypeMapper.toDto(updatedVehicleType);

        restVehicleTypeMockMvc.perform(put("/api/vehicle-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleTypeDTO)))
            .andExpect(status().isOk());

        // Validate the VehicleType in the database
        List<VehicleType> vehicleTypeList = vehicleTypeRepository.findAll();
        assertThat(vehicleTypeList).hasSize(databaseSizeBeforeUpdate);
        VehicleType testVehicleType = vehicleTypeList.get(vehicleTypeList.size() - 1);
        assertThat(testVehicleType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVehicleType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    public void updateNonExistingVehicleType() throws Exception {
        int databaseSizeBeforeUpdate = vehicleTypeRepository.findAll().size();

        // Create the VehicleType
        VehicleTypeDTO vehicleTypeDTO = vehicleTypeMapper.toDto(vehicleType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehicleTypeMockMvc.perform(put("/api/vehicle-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VehicleType in the database
        List<VehicleType> vehicleTypeList = vehicleTypeRepository.findAll();
        assertThat(vehicleTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteVehicleType() throws Exception {
        // Initialize the database
        vehicleTypeRepository.save(vehicleType);

        int databaseSizeBeforeDelete = vehicleTypeRepository.findAll().size();

        // Get the vehicleType
        restVehicleTypeMockMvc.perform(delete("/api/vehicle-types/{id}", vehicleType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VehicleType> vehicleTypeList = vehicleTypeRepository.findAll();
        assertThat(vehicleTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleType.class);
        VehicleType vehicleType1 = new VehicleType();
        vehicleType1.setId("id1");
        VehicleType vehicleType2 = new VehicleType();
        vehicleType2.setId(vehicleType1.getId());
        assertThat(vehicleType1).isEqualTo(vehicleType2);
        vehicleType2.setId("id2");
        assertThat(vehicleType1).isNotEqualTo(vehicleType2);
        vehicleType1.setId(null);
        assertThat(vehicleType1).isNotEqualTo(vehicleType2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleTypeDTO.class);
        VehicleTypeDTO vehicleTypeDTO1 = new VehicleTypeDTO();
        vehicleTypeDTO1.setId("id1");
        VehicleTypeDTO vehicleTypeDTO2 = new VehicleTypeDTO();
        assertThat(vehicleTypeDTO1).isNotEqualTo(vehicleTypeDTO2);
        vehicleTypeDTO2.setId(vehicleTypeDTO1.getId());
        assertThat(vehicleTypeDTO1).isEqualTo(vehicleTypeDTO2);
        vehicleTypeDTO2.setId("id2");
        assertThat(vehicleTypeDTO1).isNotEqualTo(vehicleTypeDTO2);
        vehicleTypeDTO1.setId(null);
        assertThat(vehicleTypeDTO1).isNotEqualTo(vehicleTypeDTO2);
    }
}
