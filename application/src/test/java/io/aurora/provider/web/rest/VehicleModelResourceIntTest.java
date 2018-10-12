package io.aurora.provider.web.rest;

import io.aurora.provider.JhipsterApp;

import io.aurora.provider.domain.VehicleModel;
import io.aurora.provider.repository.VehicleModelRepository;
import io.aurora.provider.service.VehicleModelService;
import io.aurora.provider.service.dto.VehicleModelDTO;
import io.aurora.provider.service.mapper.VehicleModelMapper;
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
 * Test class for the VehicleModelResource REST controller.
 *
 * @see VehicleModelResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterApp.class)
public class VehicleModelResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    @Autowired
    private VehicleModelRepository vehicleModelRepository;

    @Autowired
    private VehicleModelMapper vehicleModelMapper;
    
    @Autowired
    private VehicleModelService vehicleModelService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restVehicleModelMockMvc;

    private VehicleModel vehicleModel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VehicleModelResource vehicleModelResource = new VehicleModelResource(vehicleModelService);
        this.restVehicleModelMockMvc = MockMvcBuilders.standaloneSetup(vehicleModelResource)
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
    public static VehicleModel createEntity() {
        VehicleModel vehicleModel = new VehicleModel()
            .name(DEFAULT_NAME)
            .year(DEFAULT_YEAR);
        return vehicleModel;
    }

    @Before
    public void initTest() {
        vehicleModelRepository.deleteAll();
        vehicleModel = createEntity();
    }

    @Test
    public void createVehicleModel() throws Exception {
        int databaseSizeBeforeCreate = vehicleModelRepository.findAll().size();

        // Create the VehicleModel
        VehicleModelDTO vehicleModelDTO = vehicleModelMapper.toDto(vehicleModel);
        restVehicleModelMockMvc.perform(post("/api/vehicle-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleModelDTO)))
            .andExpect(status().isCreated());

        // Validate the VehicleModel in the database
        List<VehicleModel> vehicleModelList = vehicleModelRepository.findAll();
        assertThat(vehicleModelList).hasSize(databaseSizeBeforeCreate + 1);
        VehicleModel testVehicleModel = vehicleModelList.get(vehicleModelList.size() - 1);
        assertThat(testVehicleModel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVehicleModel.getYear()).isEqualTo(DEFAULT_YEAR);
    }

    @Test
    public void createVehicleModelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vehicleModelRepository.findAll().size();

        // Create the VehicleModel with an existing ID
        vehicleModel.setId("existing_id");
        VehicleModelDTO vehicleModelDTO = vehicleModelMapper.toDto(vehicleModel);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehicleModelMockMvc.perform(post("/api/vehicle-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleModelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VehicleModel in the database
        List<VehicleModel> vehicleModelList = vehicleModelRepository.findAll();
        assertThat(vehicleModelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllVehicleModels() throws Exception {
        // Initialize the database
        vehicleModelRepository.save(vehicleModel);

        // Get all the vehicleModelList
        restVehicleModelMockMvc.perform(get("/api/vehicle-models?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicleModel.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)));
    }
    
    @Test
    public void getVehicleModel() throws Exception {
        // Initialize the database
        vehicleModelRepository.save(vehicleModel);

        // Get the vehicleModel
        restVehicleModelMockMvc.perform(get("/api/vehicle-models/{id}", vehicleModel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vehicleModel.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR));
    }

    @Test
    public void getNonExistingVehicleModel() throws Exception {
        // Get the vehicleModel
        restVehicleModelMockMvc.perform(get("/api/vehicle-models/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateVehicleModel() throws Exception {
        // Initialize the database
        vehicleModelRepository.save(vehicleModel);

        int databaseSizeBeforeUpdate = vehicleModelRepository.findAll().size();

        // Update the vehicleModel
        VehicleModel updatedVehicleModel = vehicleModelRepository.findById(vehicleModel.getId()).get();
        updatedVehicleModel
            .name(UPDATED_NAME)
            .year(UPDATED_YEAR);
        VehicleModelDTO vehicleModelDTO = vehicleModelMapper.toDto(updatedVehicleModel);

        restVehicleModelMockMvc.perform(put("/api/vehicle-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleModelDTO)))
            .andExpect(status().isOk());

        // Validate the VehicleModel in the database
        List<VehicleModel> vehicleModelList = vehicleModelRepository.findAll();
        assertThat(vehicleModelList).hasSize(databaseSizeBeforeUpdate);
        VehicleModel testVehicleModel = vehicleModelList.get(vehicleModelList.size() - 1);
        assertThat(testVehicleModel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVehicleModel.getYear()).isEqualTo(UPDATED_YEAR);
    }

    @Test
    public void updateNonExistingVehicleModel() throws Exception {
        int databaseSizeBeforeUpdate = vehicleModelRepository.findAll().size();

        // Create the VehicleModel
        VehicleModelDTO vehicleModelDTO = vehicleModelMapper.toDto(vehicleModel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehicleModelMockMvc.perform(put("/api/vehicle-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleModelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VehicleModel in the database
        List<VehicleModel> vehicleModelList = vehicleModelRepository.findAll();
        assertThat(vehicleModelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteVehicleModel() throws Exception {
        // Initialize the database
        vehicleModelRepository.save(vehicleModel);

        int databaseSizeBeforeDelete = vehicleModelRepository.findAll().size();

        // Get the vehicleModel
        restVehicleModelMockMvc.perform(delete("/api/vehicle-models/{id}", vehicleModel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VehicleModel> vehicleModelList = vehicleModelRepository.findAll();
        assertThat(vehicleModelList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleModel.class);
        VehicleModel vehicleModel1 = new VehicleModel();
        vehicleModel1.setId("id1");
        VehicleModel vehicleModel2 = new VehicleModel();
        vehicleModel2.setId(vehicleModel1.getId());
        assertThat(vehicleModel1).isEqualTo(vehicleModel2);
        vehicleModel2.setId("id2");
        assertThat(vehicleModel1).isNotEqualTo(vehicleModel2);
        vehicleModel1.setId(null);
        assertThat(vehicleModel1).isNotEqualTo(vehicleModel2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleModelDTO.class);
        VehicleModelDTO vehicleModelDTO1 = new VehicleModelDTO();
        vehicleModelDTO1.setId("id1");
        VehicleModelDTO vehicleModelDTO2 = new VehicleModelDTO();
        assertThat(vehicleModelDTO1).isNotEqualTo(vehicleModelDTO2);
        vehicleModelDTO2.setId(vehicleModelDTO1.getId());
        assertThat(vehicleModelDTO1).isEqualTo(vehicleModelDTO2);
        vehicleModelDTO2.setId("id2");
        assertThat(vehicleModelDTO1).isNotEqualTo(vehicleModelDTO2);
        vehicleModelDTO1.setId(null);
        assertThat(vehicleModelDTO1).isNotEqualTo(vehicleModelDTO2);
    }
}
