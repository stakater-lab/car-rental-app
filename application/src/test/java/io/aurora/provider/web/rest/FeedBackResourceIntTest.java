package io.aurora.provider.web.rest;

import io.aurora.provider.JhipsterApp;

import io.aurora.provider.domain.FeedBack;
import io.aurora.provider.repository.FeedBackRepository;
import io.aurora.provider.service.FeedBackService;
import io.aurora.provider.service.dto.FeedBackDTO;
import io.aurora.provider.service.mapper.FeedBackMapper;
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
 * Test class for the FeedBackResource REST controller.
 *
 * @see FeedBackResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterApp.class)
public class FeedBackResourceIntTest {

    private static final Double DEFAULT_SCORE = 1D;
    private static final Double UPDATED_SCORE = 2D;

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    @Autowired
    private FeedBackRepository feedBackRepository;

    @Autowired
    private FeedBackMapper feedBackMapper;
    
    @Autowired
    private FeedBackService feedBackService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restFeedBackMockMvc;

    private FeedBack feedBack;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FeedBackResource feedBackResource = new FeedBackResource(feedBackService);
        this.restFeedBackMockMvc = MockMvcBuilders.standaloneSetup(feedBackResource)
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
    public static FeedBack createEntity() {
        FeedBack feedBack = new FeedBack()
            .score(DEFAULT_SCORE)
            .comment(DEFAULT_COMMENT);
        return feedBack;
    }

    @Before
    public void initTest() {
        feedBackRepository.deleteAll();
        feedBack = createEntity();
    }

    @Test
    public void createFeedBack() throws Exception {
        int databaseSizeBeforeCreate = feedBackRepository.findAll().size();

        // Create the FeedBack
        FeedBackDTO feedBackDTO = feedBackMapper.toDto(feedBack);
        restFeedBackMockMvc.perform(post("/api/feed-backs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feedBackDTO)))
            .andExpect(status().isCreated());

        // Validate the FeedBack in the database
        List<FeedBack> feedBackList = feedBackRepository.findAll();
        assertThat(feedBackList).hasSize(databaseSizeBeforeCreate + 1);
        FeedBack testFeedBack = feedBackList.get(feedBackList.size() - 1);
        assertThat(testFeedBack.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(testFeedBack.getComment()).isEqualTo(DEFAULT_COMMENT);
    }

    @Test
    public void createFeedBackWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = feedBackRepository.findAll().size();

        // Create the FeedBack with an existing ID
        feedBack.setId("existing_id");
        FeedBackDTO feedBackDTO = feedBackMapper.toDto(feedBack);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFeedBackMockMvc.perform(post("/api/feed-backs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feedBackDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FeedBack in the database
        List<FeedBack> feedBackList = feedBackRepository.findAll();
        assertThat(feedBackList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllFeedBacks() throws Exception {
        // Initialize the database
        feedBackRepository.save(feedBack);

        // Get all the feedBackList
        restFeedBackMockMvc.perform(get("/api/feed-backs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feedBack.getId())))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }
    
    @Test
    public void getFeedBack() throws Exception {
        // Initialize the database
        feedBackRepository.save(feedBack);

        // Get the feedBack
        restFeedBackMockMvc.perform(get("/api/feed-backs/{id}", feedBack.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(feedBack.getId()))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE.doubleValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()));
    }

    @Test
    public void getNonExistingFeedBack() throws Exception {
        // Get the feedBack
        restFeedBackMockMvc.perform(get("/api/feed-backs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateFeedBack() throws Exception {
        // Initialize the database
        feedBackRepository.save(feedBack);

        int databaseSizeBeforeUpdate = feedBackRepository.findAll().size();

        // Update the feedBack
        FeedBack updatedFeedBack = feedBackRepository.findById(feedBack.getId()).get();
        updatedFeedBack
            .score(UPDATED_SCORE)
            .comment(UPDATED_COMMENT);
        FeedBackDTO feedBackDTO = feedBackMapper.toDto(updatedFeedBack);

        restFeedBackMockMvc.perform(put("/api/feed-backs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feedBackDTO)))
            .andExpect(status().isOk());

        // Validate the FeedBack in the database
        List<FeedBack> feedBackList = feedBackRepository.findAll();
        assertThat(feedBackList).hasSize(databaseSizeBeforeUpdate);
        FeedBack testFeedBack = feedBackList.get(feedBackList.size() - 1);
        assertThat(testFeedBack.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testFeedBack.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    public void updateNonExistingFeedBack() throws Exception {
        int databaseSizeBeforeUpdate = feedBackRepository.findAll().size();

        // Create the FeedBack
        FeedBackDTO feedBackDTO = feedBackMapper.toDto(feedBack);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeedBackMockMvc.perform(put("/api/feed-backs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feedBackDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FeedBack in the database
        List<FeedBack> feedBackList = feedBackRepository.findAll();
        assertThat(feedBackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteFeedBack() throws Exception {
        // Initialize the database
        feedBackRepository.save(feedBack);

        int databaseSizeBeforeDelete = feedBackRepository.findAll().size();

        // Get the feedBack
        restFeedBackMockMvc.perform(delete("/api/feed-backs/{id}", feedBack.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FeedBack> feedBackList = feedBackRepository.findAll();
        assertThat(feedBackList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FeedBack.class);
        FeedBack feedBack1 = new FeedBack();
        feedBack1.setId("id1");
        FeedBack feedBack2 = new FeedBack();
        feedBack2.setId(feedBack1.getId());
        assertThat(feedBack1).isEqualTo(feedBack2);
        feedBack2.setId("id2");
        assertThat(feedBack1).isNotEqualTo(feedBack2);
        feedBack1.setId(null);
        assertThat(feedBack1).isNotEqualTo(feedBack2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FeedBackDTO.class);
        FeedBackDTO feedBackDTO1 = new FeedBackDTO();
        feedBackDTO1.setId("id1");
        FeedBackDTO feedBackDTO2 = new FeedBackDTO();
        assertThat(feedBackDTO1).isNotEqualTo(feedBackDTO2);
        feedBackDTO2.setId(feedBackDTO1.getId());
        assertThat(feedBackDTO1).isEqualTo(feedBackDTO2);
        feedBackDTO2.setId("id2");
        assertThat(feedBackDTO1).isNotEqualTo(feedBackDTO2);
        feedBackDTO1.setId(null);
        assertThat(feedBackDTO1).isNotEqualTo(feedBackDTO2);
    }
}
