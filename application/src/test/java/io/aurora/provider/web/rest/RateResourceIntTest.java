package io.aurora.provider.web.rest;

import io.aurora.provider.JhipsterApp;

import io.aurora.provider.domain.Rate;
import io.aurora.provider.repository.RateRepository;
import io.aurora.provider.service.RateService;
import io.aurora.provider.service.dto.RateDTO;
import io.aurora.provider.service.mapper.RateMapper;
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

import java.math.BigDecimal;
import java.util.List;


import static io.aurora.provider.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RateResource REST controller.
 *
 * @see RateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterApp.class)
public class RateResourceIntTest {

    private static final BigDecimal DEFAULT_HOURLY = new BigDecimal(1);
    private static final BigDecimal UPDATED_HOURLY = new BigDecimal(2);

    private static final BigDecimal DEFAULT_HOURLY_WITHOUT_DRIVER = new BigDecimal(1);
    private static final BigDecimal UPDATED_HOURLY_WITHOUT_DRIVER = new BigDecimal(2);

    private static final BigDecimal DEFAULT_DAILY = new BigDecimal(1);
    private static final BigDecimal UPDATED_DAILY = new BigDecimal(2);

    private static final BigDecimal DEFAULT_DAILY_WITHOUT_DRIVER = new BigDecimal(1);
    private static final BigDecimal UPDATED_DAILY_WITHOUT_DRIVER = new BigDecimal(2);

    @Autowired
    private RateRepository rateRepository;

    @Autowired
    private RateMapper rateMapper;
    
    @Autowired
    private RateService rateService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restRateMockMvc;

    private Rate rate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RateResource rateResource = new RateResource(rateService);
        this.restRateMockMvc = MockMvcBuilders.standaloneSetup(rateResource)
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
    public static Rate createEntity() {
        Rate rate = new Rate()
            .hourly(DEFAULT_HOURLY)
            .hourlyWithoutDriver(DEFAULT_HOURLY_WITHOUT_DRIVER)
            .daily(DEFAULT_DAILY)
            .dailyWithoutDriver(DEFAULT_DAILY_WITHOUT_DRIVER);
        return rate;
    }

    @Before
    public void initTest() {
        rateRepository.deleteAll();
        rate = createEntity();
    }

    @Test
    public void createRate() throws Exception {
        int databaseSizeBeforeCreate = rateRepository.findAll().size();

        // Create the Rate
        RateDTO rateDTO = rateMapper.toDto(rate);
        restRateMockMvc.perform(post("/api/rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rateDTO)))
            .andExpect(status().isCreated());

        // Validate the Rate in the database
        List<Rate> rateList = rateRepository.findAll();
        assertThat(rateList).hasSize(databaseSizeBeforeCreate + 1);
        Rate testRate = rateList.get(rateList.size() - 1);
        assertThat(testRate.getHourly()).isEqualTo(DEFAULT_HOURLY);
        assertThat(testRate.getHourlyWithoutDriver()).isEqualTo(DEFAULT_HOURLY_WITHOUT_DRIVER);
        assertThat(testRate.getDaily()).isEqualTo(DEFAULT_DAILY);
        assertThat(testRate.getDailyWithoutDriver()).isEqualTo(DEFAULT_DAILY_WITHOUT_DRIVER);
    }

    @Test
    public void createRateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rateRepository.findAll().size();

        // Create the Rate with an existing ID
        rate.setId("existing_id");
        RateDTO rateDTO = rateMapper.toDto(rate);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRateMockMvc.perform(post("/api/rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Rate in the database
        List<Rate> rateList = rateRepository.findAll();
        assertThat(rateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllRates() throws Exception {
        // Initialize the database
        rateRepository.save(rate);

        // Get all the rateList
        restRateMockMvc.perform(get("/api/rates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rate.getId())))
            .andExpect(jsonPath("$.[*].hourly").value(hasItem(DEFAULT_HOURLY.intValue())))
            .andExpect(jsonPath("$.[*].hourlyWithoutDriver").value(hasItem(DEFAULT_HOURLY_WITHOUT_DRIVER.intValue())))
            .andExpect(jsonPath("$.[*].daily").value(hasItem(DEFAULT_DAILY.intValue())))
            .andExpect(jsonPath("$.[*].dailyWithoutDriver").value(hasItem(DEFAULT_DAILY_WITHOUT_DRIVER.intValue())));
    }
    
    @Test
    public void getRate() throws Exception {
        // Initialize the database
        rateRepository.save(rate);

        // Get the rate
        restRateMockMvc.perform(get("/api/rates/{id}", rate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rate.getId()))
            .andExpect(jsonPath("$.hourly").value(DEFAULT_HOURLY.intValue()))
            .andExpect(jsonPath("$.hourlyWithoutDriver").value(DEFAULT_HOURLY_WITHOUT_DRIVER.intValue()))
            .andExpect(jsonPath("$.daily").value(DEFAULT_DAILY.intValue()))
            .andExpect(jsonPath("$.dailyWithoutDriver").value(DEFAULT_DAILY_WITHOUT_DRIVER.intValue()));
    }

    @Test
    public void getNonExistingRate() throws Exception {
        // Get the rate
        restRateMockMvc.perform(get("/api/rates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateRate() throws Exception {
        // Initialize the database
        rateRepository.save(rate);

        int databaseSizeBeforeUpdate = rateRepository.findAll().size();

        // Update the rate
        Rate updatedRate = rateRepository.findById(rate.getId()).get();
        updatedRate
            .hourly(UPDATED_HOURLY)
            .hourlyWithoutDriver(UPDATED_HOURLY_WITHOUT_DRIVER)
            .daily(UPDATED_DAILY)
            .dailyWithoutDriver(UPDATED_DAILY_WITHOUT_DRIVER);
        RateDTO rateDTO = rateMapper.toDto(updatedRate);

        restRateMockMvc.perform(put("/api/rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rateDTO)))
            .andExpect(status().isOk());

        // Validate the Rate in the database
        List<Rate> rateList = rateRepository.findAll();
        assertThat(rateList).hasSize(databaseSizeBeforeUpdate);
        Rate testRate = rateList.get(rateList.size() - 1);
        assertThat(testRate.getHourly()).isEqualTo(UPDATED_HOURLY);
        assertThat(testRate.getHourlyWithoutDriver()).isEqualTo(UPDATED_HOURLY_WITHOUT_DRIVER);
        assertThat(testRate.getDaily()).isEqualTo(UPDATED_DAILY);
        assertThat(testRate.getDailyWithoutDriver()).isEqualTo(UPDATED_DAILY_WITHOUT_DRIVER);
    }

    @Test
    public void updateNonExistingRate() throws Exception {
        int databaseSizeBeforeUpdate = rateRepository.findAll().size();

        // Create the Rate
        RateDTO rateDTO = rateMapper.toDto(rate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRateMockMvc.perform(put("/api/rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Rate in the database
        List<Rate> rateList = rateRepository.findAll();
        assertThat(rateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteRate() throws Exception {
        // Initialize the database
        rateRepository.save(rate);

        int databaseSizeBeforeDelete = rateRepository.findAll().size();

        // Get the rate
        restRateMockMvc.perform(delete("/api/rates/{id}", rate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Rate> rateList = rateRepository.findAll();
        assertThat(rateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rate.class);
        Rate rate1 = new Rate();
        rate1.setId("id1");
        Rate rate2 = new Rate();
        rate2.setId(rate1.getId());
        assertThat(rate1).isEqualTo(rate2);
        rate2.setId("id2");
        assertThat(rate1).isNotEqualTo(rate2);
        rate1.setId(null);
        assertThat(rate1).isNotEqualTo(rate2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RateDTO.class);
        RateDTO rateDTO1 = new RateDTO();
        rateDTO1.setId("id1");
        RateDTO rateDTO2 = new RateDTO();
        assertThat(rateDTO1).isNotEqualTo(rateDTO2);
        rateDTO2.setId(rateDTO1.getId());
        assertThat(rateDTO1).isEqualTo(rateDTO2);
        rateDTO2.setId("id2");
        assertThat(rateDTO1).isNotEqualTo(rateDTO2);
        rateDTO1.setId(null);
        assertThat(rateDTO1).isNotEqualTo(rateDTO2);
    }
}
