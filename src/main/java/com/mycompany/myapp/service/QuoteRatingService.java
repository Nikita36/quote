package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.QuoteRatingDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.QuoteRating}.
 */
public interface QuoteRatingService {
    /**
     * Save a quoteRating.
     *
     * @param quoteRatingDTO the entity to save.
     * @return the persisted entity.
     */
    QuoteRatingDTO save(QuoteRatingDTO quoteRatingDTO);

    /**
     * Updates a quoteRating.
     *
     * @param quoteRatingDTO the entity to update.
     * @return the persisted entity.
     */
    QuoteRatingDTO update(QuoteRatingDTO quoteRatingDTO);

    /**
     * Partially updates a quoteRating.
     *
     * @param quoteRatingDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<QuoteRatingDTO> partialUpdate(QuoteRatingDTO quoteRatingDTO);

    /**
     * Get all the quoteRatings.
     *
     * @return the list of entities.
     */
    List<QuoteRatingDTO> findAll();

    /**
     * Get the "id" quoteRating.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<QuoteRatingDTO> findOne(Long id);

    /**
     * Delete the "id" quoteRating.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
