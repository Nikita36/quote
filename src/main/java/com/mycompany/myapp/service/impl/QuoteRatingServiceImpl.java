package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.QuoteRating;
import com.mycompany.myapp.repository.QuoteRatingRepository;
import com.mycompany.myapp.service.QuoteRatingService;
import com.mycompany.myapp.service.dto.QuoteRatingDTO;
import com.mycompany.myapp.service.mapper.QuoteRatingMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link QuoteRating}.
 */
@Service
@Transactional
public class QuoteRatingServiceImpl implements QuoteRatingService {

    private final Logger log = LoggerFactory.getLogger(QuoteRatingServiceImpl.class);

    private final QuoteRatingRepository quoteRatingRepository;

    private final QuoteRatingMapper quoteRatingMapper;

    public QuoteRatingServiceImpl(QuoteRatingRepository quoteRatingRepository, QuoteRatingMapper quoteRatingMapper) {
        this.quoteRatingRepository = quoteRatingRepository;
        this.quoteRatingMapper = quoteRatingMapper;
    }

    @Override
    public QuoteRatingDTO save(QuoteRatingDTO quoteRatingDTO) {
        log.debug("Request to save QuoteRating : {}", quoteRatingDTO);
        QuoteRating quoteRating = quoteRatingMapper.toEntity(quoteRatingDTO);
        quoteRating = quoteRatingRepository.save(quoteRating);
        return quoteRatingMapper.toDto(quoteRating);
    }

    @Override
    public QuoteRatingDTO update(QuoteRatingDTO quoteRatingDTO) {
        log.debug("Request to save QuoteRating : {}", quoteRatingDTO);
        QuoteRating quoteRating = quoteRatingMapper.toEntity(quoteRatingDTO);
        quoteRating = quoteRatingRepository.save(quoteRating);
        return quoteRatingMapper.toDto(quoteRating);
    }

    @Override
    public Optional<QuoteRatingDTO> partialUpdate(QuoteRatingDTO quoteRatingDTO) {
        log.debug("Request to partially update QuoteRating : {}", quoteRatingDTO);

        return quoteRatingRepository
            .findById(quoteRatingDTO.getId())
            .map(existingQuoteRating -> {
                quoteRatingMapper.partialUpdate(existingQuoteRating, quoteRatingDTO);

                return existingQuoteRating;
            })
            .map(quoteRatingRepository::save)
            .map(quoteRatingMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuoteRatingDTO> findAll() {
        log.debug("Request to get all QuoteRatings");
        return quoteRatingRepository.findAll().stream().map(quoteRatingMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<QuoteRatingDTO> findOne(Long id) {
        log.debug("Request to get QuoteRating : {}", id);
        return quoteRatingRepository.findById(id).map(quoteRatingMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete QuoteRating : {}", id);
        quoteRatingRepository.deleteById(id);
    }
}
