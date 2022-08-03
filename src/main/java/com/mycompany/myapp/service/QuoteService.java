package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.QuoteDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Quote}.
 */
public interface QuoteService {
    /**
     * Save a quote.
     *
     * @param quoteDTO the entity to save.
     * @return the persisted entity.
     */
    QuoteDTO save(QuoteDTO quoteDTO);

    /**
     * Updates a quote.
     *
     * @param quoteDTO the entity to update.
     * @return the persisted entity.
     */
    QuoteDTO update(QuoteDTO quoteDTO);

    /**
     * Partially updates a quote.
     *
     * @param quoteDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<QuoteDTO> partialUpdate(QuoteDTO quoteDTO);

    /**
     * Get all the quotes.
     *
     * @return the list of entities.
     */
    List<QuoteDTO> findAll();

    /**
     * Get the "id" quote.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<QuoteDTO> findOne(Long id);

    /**
     * Delete the "id" quote.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<QuoteDTO> findTopTen();

    List<QuoteDTO> findFlopTen();

    QuoteDTO findRandom();
}
