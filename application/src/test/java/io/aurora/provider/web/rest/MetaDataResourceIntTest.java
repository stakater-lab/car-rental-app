package io.aurora.provider.web.rest;

import io.aurora.provider.JhipsterApp;

import io.aurora.provider.domain.MetaData;
import io.aurora.provider.repository.MetaDataRepository;
import io.aurora.provider.service.MetaDataService;
import io.aurora.provider.service.dto.MetaDataDTO;
import io.aurora.provider.service.mapper.MetaDataMapper;
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
 * Test class for the MetaDataResource REST controller.
 *
 * @see MetaDataResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterApp.class)
public class MetaDataResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private MetaDataRepository metaDataRepository;

    @Autowired
    private MetaDataMapper metaDataMapper;
    
    @Autowired
    private MetaDataService metaDataService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restMetaDataMockMvc;

    private MetaData metaData;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MetaDataResource metaDataResource = new MetaDataResource(metaDataService);
        this.restMetaDataMockMvc = MockMvcBuilders.standaloneSetup(metaDataResource)
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
    public static MetaData createEntity() {
        MetaData metaData = new MetaData()
            .name(DEFAULT_NAME)
            .value(DEFAULT_VALUE);
        return metaData;
    }

    @Before
    public void initTest() {
        metaDataRepository.deleteAll();
        metaData = createEntity();
    }

    @Test
    public void createMetaData() throws Exception {
        int databaseSizeBeforeCreate = metaDataRepository.findAll().size();

        // Create the MetaData
        MetaDataDTO metaDataDTO = metaDataMapper.toDto(metaData);
        restMetaDataMockMvc.perform(post("/api/meta-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(metaDataDTO)))
            .andExpect(status().isCreated());

        // Validate the MetaData in the database
        List<MetaData> metaDataList = metaDataRepository.findAll();
        assertThat(metaDataList).hasSize(databaseSizeBeforeCreate + 1);
        MetaData testMetaData = metaDataList.get(metaDataList.size() - 1);
        assertThat(testMetaData.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMetaData.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    public void createMetaDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = metaDataRepository.findAll().size();

        // Create the MetaData with an existing ID
        metaData.setId("existing_id");
        MetaDataDTO metaDataDTO = metaDataMapper.toDto(metaData);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMetaDataMockMvc.perform(post("/api/meta-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(metaDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MetaData in the database
        List<MetaData> metaDataList = metaDataRepository.findAll();
        assertThat(metaDataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllMetaData() throws Exception {
        // Initialize the database
        metaDataRepository.save(metaData);

        // Get all the metaDataList
        restMetaDataMockMvc.perform(get("/api/meta-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(metaData.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }
    
    @Test
    public void getMetaData() throws Exception {
        // Initialize the database
        metaDataRepository.save(metaData);

        // Get the metaData
        restMetaDataMockMvc.perform(get("/api/meta-data/{id}", metaData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(metaData.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    public void getNonExistingMetaData() throws Exception {
        // Get the metaData
        restMetaDataMockMvc.perform(get("/api/meta-data/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateMetaData() throws Exception {
        // Initialize the database
        metaDataRepository.save(metaData);

        int databaseSizeBeforeUpdate = metaDataRepository.findAll().size();

        // Update the metaData
        MetaData updatedMetaData = metaDataRepository.findById(metaData.getId()).get();
        updatedMetaData
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE);
        MetaDataDTO metaDataDTO = metaDataMapper.toDto(updatedMetaData);

        restMetaDataMockMvc.perform(put("/api/meta-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(metaDataDTO)))
            .andExpect(status().isOk());

        // Validate the MetaData in the database
        List<MetaData> metaDataList = metaDataRepository.findAll();
        assertThat(metaDataList).hasSize(databaseSizeBeforeUpdate);
        MetaData testMetaData = metaDataList.get(metaDataList.size() - 1);
        assertThat(testMetaData.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMetaData.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    public void updateNonExistingMetaData() throws Exception {
        int databaseSizeBeforeUpdate = metaDataRepository.findAll().size();

        // Create the MetaData
        MetaDataDTO metaDataDTO = metaDataMapper.toDto(metaData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMetaDataMockMvc.perform(put("/api/meta-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(metaDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MetaData in the database
        List<MetaData> metaDataList = metaDataRepository.findAll();
        assertThat(metaDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteMetaData() throws Exception {
        // Initialize the database
        metaDataRepository.save(metaData);

        int databaseSizeBeforeDelete = metaDataRepository.findAll().size();

        // Get the metaData
        restMetaDataMockMvc.perform(delete("/api/meta-data/{id}", metaData.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MetaData> metaDataList = metaDataRepository.findAll();
        assertThat(metaDataList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MetaData.class);
        MetaData metaData1 = new MetaData();
        metaData1.setId("id1");
        MetaData metaData2 = new MetaData();
        metaData2.setId(metaData1.getId());
        assertThat(metaData1).isEqualTo(metaData2);
        metaData2.setId("id2");
        assertThat(metaData1).isNotEqualTo(metaData2);
        metaData1.setId(null);
        assertThat(metaData1).isNotEqualTo(metaData2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MetaDataDTO.class);
        MetaDataDTO metaDataDTO1 = new MetaDataDTO();
        metaDataDTO1.setId("id1");
        MetaDataDTO metaDataDTO2 = new MetaDataDTO();
        assertThat(metaDataDTO1).isNotEqualTo(metaDataDTO2);
        metaDataDTO2.setId(metaDataDTO1.getId());
        assertThat(metaDataDTO1).isEqualTo(metaDataDTO2);
        metaDataDTO2.setId("id2");
        assertThat(metaDataDTO1).isNotEqualTo(metaDataDTO2);
        metaDataDTO1.setId(null);
        assertThat(metaDataDTO1).isNotEqualTo(metaDataDTO2);
    }
}
