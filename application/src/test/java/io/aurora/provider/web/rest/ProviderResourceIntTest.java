package io.aurora.provider.web.rest;

import io.aurora.provider.JhipsterApp;

import io.aurora.provider.domain.Provider;
import io.aurora.provider.repository.ProviderRepository;
import io.aurora.provider.service.ProviderService;
import io.aurora.provider.service.dto.ProviderDTO;
import io.aurora.provider.service.mapper.ProviderMapper;
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

import io.aurora.provider.domain.enumeration.ProviderType;
/**
 * Test class for the ProviderResource REST controller.
 *
 * @see ProviderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterApp.class)
public class ProviderResourceIntTest {

    private static final ProviderType DEFAULT_TYPE = ProviderType.Individual;
    private static final ProviderType UPDATED_TYPE = ProviderType.Business;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_RATINGS = 1L;
    private static final Long UPDATED_RATINGS = 2L;

    private static final Long DEFAULT_NO_OF_VEHICLES = 1L;
    private static final Long UPDATED_NO_OF_VEHICLES = 2L;

    private static final String DEFAULT_EMAIL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_ADDRESS = "BBBBBBBBBB";

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private ProviderMapper providerMapper;
    
    @Autowired
    private ProviderService providerService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restProviderMockMvc;

    private Provider provider;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProviderResource providerResource = new ProviderResource(providerService);
        this.restProviderMockMvc = MockMvcBuilders.standaloneSetup(providerResource)
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
    public static Provider createEntity() {
        Provider provider = new Provider()
            .type(DEFAULT_TYPE)
            .name(DEFAULT_NAME)
            .ratings(DEFAULT_RATINGS)
            .noOfVehicles(DEFAULT_NO_OF_VEHICLES)
            .emailAddress(DEFAULT_EMAIL_ADDRESS);
        return provider;
    }

    @Before
    public void initTest() {
        providerRepository.deleteAll();
        provider = createEntity();
    }

    @Test
    public void createProvider() throws Exception {
        int databaseSizeBeforeCreate = providerRepository.findAll().size();

        // Create the Provider
        ProviderDTO providerDTO = providerMapper.toDto(provider);
        restProviderMockMvc.perform(post("/api/providers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(providerDTO)))
            .andExpect(status().isCreated());

        // Validate the Provider in the database
        List<Provider> providerList = providerRepository.findAll();
        assertThat(providerList).hasSize(databaseSizeBeforeCreate + 1);
        Provider testProvider = providerList.get(providerList.size() - 1);
        assertThat(testProvider.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testProvider.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProvider.getRatings()).isEqualTo(DEFAULT_RATINGS);
        assertThat(testProvider.getNoOfVehicles()).isEqualTo(DEFAULT_NO_OF_VEHICLES);
        assertThat(testProvider.getEmailAddress()).isEqualTo(DEFAULT_EMAIL_ADDRESS);
    }

    @Test
    public void createProviderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = providerRepository.findAll().size();

        // Create the Provider with an existing ID
        provider.setId("existing_id");
        ProviderDTO providerDTO = providerMapper.toDto(provider);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProviderMockMvc.perform(post("/api/providers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(providerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Provider in the database
        List<Provider> providerList = providerRepository.findAll();
        assertThat(providerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllProviders() throws Exception {
        // Initialize the database
        providerRepository.save(provider);

        // Get all the providerList
        restProviderMockMvc.perform(get("/api/providers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(provider.getId())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].ratings").value(hasItem(DEFAULT_RATINGS.intValue())))
            .andExpect(jsonPath("$.[*].noOfVehicles").value(hasItem(DEFAULT_NO_OF_VEHICLES.intValue())))
            .andExpect(jsonPath("$.[*].emailAddress").value(hasItem(DEFAULT_EMAIL_ADDRESS.toString())));
    }
    
    @Test
    public void getProvider() throws Exception {
        // Initialize the database
        providerRepository.save(provider);

        // Get the provider
        restProviderMockMvc.perform(get("/api/providers/{id}", provider.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(provider.getId()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.ratings").value(DEFAULT_RATINGS.intValue()))
            .andExpect(jsonPath("$.noOfVehicles").value(DEFAULT_NO_OF_VEHICLES.intValue()))
            .andExpect(jsonPath("$.emailAddress").value(DEFAULT_EMAIL_ADDRESS.toString()));
    }

    @Test
    public void getNonExistingProvider() throws Exception {
        // Get the provider
        restProviderMockMvc.perform(get("/api/providers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateProvider() throws Exception {
        // Initialize the database
        providerRepository.save(provider);

        int databaseSizeBeforeUpdate = providerRepository.findAll().size();

        // Update the provider
        Provider updatedProvider = providerRepository.findById(provider.getId()).get();
        updatedProvider
            .type(UPDATED_TYPE)
            .name(UPDATED_NAME)
            .ratings(UPDATED_RATINGS)
            .noOfVehicles(UPDATED_NO_OF_VEHICLES)
            .emailAddress(UPDATED_EMAIL_ADDRESS);
        ProviderDTO providerDTO = providerMapper.toDto(updatedProvider);

        restProviderMockMvc.perform(put("/api/providers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(providerDTO)))
            .andExpect(status().isOk());

        // Validate the Provider in the database
        List<Provider> providerList = providerRepository.findAll();
        assertThat(providerList).hasSize(databaseSizeBeforeUpdate);
        Provider testProvider = providerList.get(providerList.size() - 1);
        assertThat(testProvider.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProvider.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProvider.getRatings()).isEqualTo(UPDATED_RATINGS);
        assertThat(testProvider.getNoOfVehicles()).isEqualTo(UPDATED_NO_OF_VEHICLES);
        assertThat(testProvider.getEmailAddress()).isEqualTo(UPDATED_EMAIL_ADDRESS);
    }

    @Test
    public void updateNonExistingProvider() throws Exception {
        int databaseSizeBeforeUpdate = providerRepository.findAll().size();

        // Create the Provider
        ProviderDTO providerDTO = providerMapper.toDto(provider);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProviderMockMvc.perform(put("/api/providers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(providerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Provider in the database
        List<Provider> providerList = providerRepository.findAll();
        assertThat(providerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteProvider() throws Exception {
        // Initialize the database
        providerRepository.save(provider);

        int databaseSizeBeforeDelete = providerRepository.findAll().size();

        // Get the provider
        restProviderMockMvc.perform(delete("/api/providers/{id}", provider.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Provider> providerList = providerRepository.findAll();
        assertThat(providerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Provider.class);
        Provider provider1 = new Provider();
        provider1.setId("id1");
        Provider provider2 = new Provider();
        provider2.setId(provider1.getId());
        assertThat(provider1).isEqualTo(provider2);
        provider2.setId("id2");
        assertThat(provider1).isNotEqualTo(provider2);
        provider1.setId(null);
        assertThat(provider1).isNotEqualTo(provider2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProviderDTO.class);
        ProviderDTO providerDTO1 = new ProviderDTO();
        providerDTO1.setId("id1");
        ProviderDTO providerDTO2 = new ProviderDTO();
        assertThat(providerDTO1).isNotEqualTo(providerDTO2);
        providerDTO2.setId(providerDTO1.getId());
        assertThat(providerDTO1).isEqualTo(providerDTO2);
        providerDTO2.setId("id2");
        assertThat(providerDTO1).isNotEqualTo(providerDTO2);
        providerDTO1.setId(null);
        assertThat(providerDTO1).isNotEqualTo(providerDTO2);
    }
}
