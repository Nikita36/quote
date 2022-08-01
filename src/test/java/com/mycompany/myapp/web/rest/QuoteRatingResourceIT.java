package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.QuoteRating;
import com.mycompany.myapp.repository.QuoteRatingRepository;
import com.mycompany.myapp.service.dto.QuoteRatingDTO;
import com.mycompany.myapp.service.mapper.QuoteRatingMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link QuoteRatingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class QuoteRatingResourceIT {

    private static final Boolean DEFAULT_IS_LIKE = false;
    private static final Boolean UPDATED_IS_LIKE = true;

    private static final String ENTITY_API_URL = "/api/quote-ratings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/quote-ratings";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private QuoteRatingRepository quoteRatingRepository;

    @Autowired
    private QuoteRatingMapper quoteRatingMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQuoteRatingMockMvc;

    private QuoteRating quoteRating;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuoteRating createEntity(EntityManager em) {
        QuoteRating quoteRating = new QuoteRating().isLike(DEFAULT_IS_LIKE);
        return quoteRating;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuoteRating createUpdatedEntity(EntityManager em) {
        QuoteRating quoteRating = new QuoteRating().isLike(UPDATED_IS_LIKE);
        return quoteRating;
    }

    @BeforeEach
    public void initTest() {
        quoteRating = createEntity(em);
    }

    @Test
    @Transactional
    void createQuoteRating() throws Exception {
        int databaseSizeBeforeCreate = quoteRatingRepository.findAll().size();
        // Create the QuoteRating
        QuoteRatingDTO quoteRatingDTO = quoteRatingMapper.toDto(quoteRating);
        restQuoteRatingMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quoteRatingDTO))
            )
            .andExpect(status().isCreated());

        // Validate the QuoteRating in the database
        List<QuoteRating> quoteRatingList = quoteRatingRepository.findAll();
        assertThat(quoteRatingList).hasSize(databaseSizeBeforeCreate + 1);
        QuoteRating testQuoteRating = quoteRatingList.get(quoteRatingList.size() - 1);
        assertThat(testQuoteRating.getIsLike()).isEqualTo(DEFAULT_IS_LIKE);
    }

    @Test
    @Transactional
    void createQuoteRatingWithExistingId() throws Exception {
        // Create the QuoteRating with an existing ID
        quoteRating.setId(1L);
        QuoteRatingDTO quoteRatingDTO = quoteRatingMapper.toDto(quoteRating);

        int databaseSizeBeforeCreate = quoteRatingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuoteRatingMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quoteRatingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuoteRating in the database
        List<QuoteRating> quoteRatingList = quoteRatingRepository.findAll();
        assertThat(quoteRatingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllQuoteRatings() throws Exception {
        // Initialize the database
        quoteRatingRepository.saveAndFlush(quoteRating);

        // Get all the quoteRatingList
        restQuoteRatingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quoteRating.getId().intValue())))
            .andExpect(jsonPath("$.[*].isLike").value(hasItem(DEFAULT_IS_LIKE.booleanValue())));
    }

    @Test
    @Transactional
    void getQuoteRating() throws Exception {
        // Initialize the database
        quoteRatingRepository.saveAndFlush(quoteRating);

        // Get the quoteRating
        restQuoteRatingMockMvc
            .perform(get(ENTITY_API_URL_ID, quoteRating.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(quoteRating.getId().intValue()))
            .andExpect(jsonPath("$.isLike").value(DEFAULT_IS_LIKE.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingQuoteRating() throws Exception {
        // Get the quoteRating
        restQuoteRatingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewQuoteRating() throws Exception {
        // Initialize the database
        quoteRatingRepository.saveAndFlush(quoteRating);

        int databaseSizeBeforeUpdate = quoteRatingRepository.findAll().size();

        // Update the quoteRating
        QuoteRating updatedQuoteRating = quoteRatingRepository.findById(quoteRating.getId()).get();
        // Disconnect from session so that the updates on updatedQuoteRating are not directly saved in db
        em.detach(updatedQuoteRating);
        updatedQuoteRating.isLike(UPDATED_IS_LIKE);
        QuoteRatingDTO quoteRatingDTO = quoteRatingMapper.toDto(updatedQuoteRating);

        restQuoteRatingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, quoteRatingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(quoteRatingDTO))
            )
            .andExpect(status().isOk());

        // Validate the QuoteRating in the database
        List<QuoteRating> quoteRatingList = quoteRatingRepository.findAll();
        assertThat(quoteRatingList).hasSize(databaseSizeBeforeUpdate);
        QuoteRating testQuoteRating = quoteRatingList.get(quoteRatingList.size() - 1);
        assertThat(testQuoteRating.getIsLike()).isEqualTo(UPDATED_IS_LIKE);
    }

    @Test
    @Transactional
    void putNonExistingQuoteRating() throws Exception {
        int databaseSizeBeforeUpdate = quoteRatingRepository.findAll().size();
        quoteRating.setId(count.incrementAndGet());

        // Create the QuoteRating
        QuoteRatingDTO quoteRatingDTO = quoteRatingMapper.toDto(quoteRating);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuoteRatingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, quoteRatingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(quoteRatingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuoteRating in the database
        List<QuoteRating> quoteRatingList = quoteRatingRepository.findAll();
        assertThat(quoteRatingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchQuoteRating() throws Exception {
        int databaseSizeBeforeUpdate = quoteRatingRepository.findAll().size();
        quoteRating.setId(count.incrementAndGet());

        // Create the QuoteRating
        QuoteRatingDTO quoteRatingDTO = quoteRatingMapper.toDto(quoteRating);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuoteRatingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(quoteRatingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuoteRating in the database
        List<QuoteRating> quoteRatingList = quoteRatingRepository.findAll();
        assertThat(quoteRatingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamQuoteRating() throws Exception {
        int databaseSizeBeforeUpdate = quoteRatingRepository.findAll().size();
        quoteRating.setId(count.incrementAndGet());

        // Create the QuoteRating
        QuoteRatingDTO quoteRatingDTO = quoteRatingMapper.toDto(quoteRating);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuoteRatingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quoteRatingDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the QuoteRating in the database
        List<QuoteRating> quoteRatingList = quoteRatingRepository.findAll();
        assertThat(quoteRatingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateQuoteRatingWithPatch() throws Exception {
        // Initialize the database
        quoteRatingRepository.saveAndFlush(quoteRating);

        int databaseSizeBeforeUpdate = quoteRatingRepository.findAll().size();

        // Update the quoteRating using partial update
        QuoteRating partialUpdatedQuoteRating = new QuoteRating();
        partialUpdatedQuoteRating.setId(quoteRating.getId());

        partialUpdatedQuoteRating.isLike(UPDATED_IS_LIKE);

        restQuoteRatingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuoteRating.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQuoteRating))
            )
            .andExpect(status().isOk());

        // Validate the QuoteRating in the database
        List<QuoteRating> quoteRatingList = quoteRatingRepository.findAll();
        assertThat(quoteRatingList).hasSize(databaseSizeBeforeUpdate);
        QuoteRating testQuoteRating = quoteRatingList.get(quoteRatingList.size() - 1);
        assertThat(testQuoteRating.getIsLike()).isEqualTo(UPDATED_IS_LIKE);
    }

    @Test
    @Transactional
    void fullUpdateQuoteRatingWithPatch() throws Exception {
        // Initialize the database
        quoteRatingRepository.saveAndFlush(quoteRating);

        int databaseSizeBeforeUpdate = quoteRatingRepository.findAll().size();

        // Update the quoteRating using partial update
        QuoteRating partialUpdatedQuoteRating = new QuoteRating();
        partialUpdatedQuoteRating.setId(quoteRating.getId());

        partialUpdatedQuoteRating.isLike(UPDATED_IS_LIKE);

        restQuoteRatingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuoteRating.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQuoteRating))
            )
            .andExpect(status().isOk());

        // Validate the QuoteRating in the database
        List<QuoteRating> quoteRatingList = quoteRatingRepository.findAll();
        assertThat(quoteRatingList).hasSize(databaseSizeBeforeUpdate);
        QuoteRating testQuoteRating = quoteRatingList.get(quoteRatingList.size() - 1);
        assertThat(testQuoteRating.getIsLike()).isEqualTo(UPDATED_IS_LIKE);
    }

    @Test
    @Transactional
    void patchNonExistingQuoteRating() throws Exception {
        int databaseSizeBeforeUpdate = quoteRatingRepository.findAll().size();
        quoteRating.setId(count.incrementAndGet());

        // Create the QuoteRating
        QuoteRatingDTO quoteRatingDTO = quoteRatingMapper.toDto(quoteRating);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuoteRatingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, quoteRatingDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(quoteRatingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuoteRating in the database
        List<QuoteRating> quoteRatingList = quoteRatingRepository.findAll();
        assertThat(quoteRatingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchQuoteRating() throws Exception {
        int databaseSizeBeforeUpdate = quoteRatingRepository.findAll().size();
        quoteRating.setId(count.incrementAndGet());

        // Create the QuoteRating
        QuoteRatingDTO quoteRatingDTO = quoteRatingMapper.toDto(quoteRating);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuoteRatingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(quoteRatingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuoteRating in the database
        List<QuoteRating> quoteRatingList = quoteRatingRepository.findAll();
        assertThat(quoteRatingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamQuoteRating() throws Exception {
        int databaseSizeBeforeUpdate = quoteRatingRepository.findAll().size();
        quoteRating.setId(count.incrementAndGet());

        // Create the QuoteRating
        QuoteRatingDTO quoteRatingDTO = quoteRatingMapper.toDto(quoteRating);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuoteRatingMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(quoteRatingDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the QuoteRating in the database
        List<QuoteRating> quoteRatingList = quoteRatingRepository.findAll();
        assertThat(quoteRatingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteQuoteRating() throws Exception {
        // Initialize the database
        quoteRatingRepository.saveAndFlush(quoteRating);

        int databaseSizeBeforeDelete = quoteRatingRepository.findAll().size();

        // Delete the quoteRating
        restQuoteRatingMockMvc
            .perform(delete(ENTITY_API_URL_ID, quoteRating.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<QuoteRating> quoteRatingList = quoteRatingRepository.findAll();
        assertThat(quoteRatingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    void searchQuoteRating() throws Exception {
        // Initialize the database
        quoteRatingRepository.saveAndFlush(quoteRating);

        // Search the quoteRating
        restQuoteRatingMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + quoteRating.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quoteRating.getId().intValue())))
            .andExpect(jsonPath("$.[*].isLike").value(hasItem(DEFAULT_IS_LIKE.booleanValue())));
    }
}
