package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Quote;
import com.mycompany.myapp.repository.QuoteRepository;
import com.mycompany.myapp.service.QuoteService;
import com.mycompany.myapp.service.dto.QuoteDTO;
import com.mycompany.myapp.service.mapper.QuoteMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Quote}.
 */
@Service
@Transactional
public class QuoteServiceImpl implements QuoteService {

    private final Logger log = LoggerFactory.getLogger(QuoteServiceImpl.class);

    private final QuoteRepository quoteRepository;

    private final QuoteMapper quoteMapper;

    public QuoteServiceImpl(QuoteRepository quoteRepository, QuoteMapper quoteMapper) {
        this.quoteRepository = quoteRepository;
        this.quoteMapper = quoteMapper;
    }

    @Override
    public QuoteDTO save(QuoteDTO quoteDTO) {
        log.debug("Request to save Quote : {}", quoteDTO);
        Quote quote = quoteMapper.toEntity(quoteDTO);
        quote = quoteRepository.save(quote);
        return quoteMapper.toDto(quote);
    }

    @Override
    public QuoteDTO update(QuoteDTO quoteDTO) {
        log.debug("Request to save Quote : {}", quoteDTO);
        Quote quote = quoteMapper.toEntity(quoteDTO);
        quote = quoteRepository.save(quote);
        return quoteMapper.toDto(quote);
    }

    @Override
    public Optional<QuoteDTO> partialUpdate(QuoteDTO quoteDTO) {
        log.debug("Request to partially update Quote : {}", quoteDTO);

        return quoteRepository
            .findById(quoteDTO.getId())
            .map(existingQuote -> {
                quoteMapper.partialUpdate(existingQuote, quoteDTO);

                return existingQuote;
            })
            .map(quoteRepository::save)
            .map(quoteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuoteDTO> findAll() {
        log.debug("Request to get all Quotes");
        return quoteRepository.findAll().stream().map(quoteMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<QuoteDTO> findOne(Long id) {
        log.debug("Request to get Quote : {}", id);
        return quoteRepository.findById(id).map(quoteMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Quote : {}", id);
        quoteRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuoteDTO> search(String query) {
        log.debug("Request to search Quotes for query {}", query);
        return quoteRepository.search(query).stream().map(quoteMapper::toDto).collect(Collectors.toList());
    }
}
