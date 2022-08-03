package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.QuoteRatingRepository;
import com.mycompany.myapp.service.QuoteRatingService;
import com.mycompany.myapp.service.dto.QuoteRatingDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.QuoteRating}.
 */
@RestController
@RequestMapping("/api/quote-rating")
public class QuoteRatingResource {

    private final Logger log = LoggerFactory.getLogger(QuoteRatingResource.class);

    private static final String ENTITY_NAME = "quoteQuoteRating";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuoteRatingService quoteRatingService;

    private final QuoteRatingRepository quoteRatingRepository;

    public QuoteRatingResource(QuoteRatingService quoteRatingService, QuoteRatingRepository quoteRatingRepository) {
        this.quoteRatingService = quoteRatingService;
        this.quoteRatingRepository = quoteRatingRepository;
    }

    /**
     * {@code POST  } : Create a new quoteRating.
     *
     * @param quoteRatingDTO the quoteRatingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new quoteRatingDTO, or with status {@code 400 (Bad Request)} if the quoteRating has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<QuoteRatingDTO> createQuoteRating(@RequestBody QuoteRatingDTO quoteRatingDTO) throws URISyntaxException {
        log.debug("REST request to save QuoteRating : {}", quoteRatingDTO);
        if (quoteRatingDTO.getId() != null) {
            throw new BadRequestAlertException("A new quoteRating cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QuoteRatingDTO result = quoteRatingService.save(quoteRatingDTO);
        return ResponseEntity
            .created(new URI("/api/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /:id} : Updates an existing quoteRating.
     *
     * @param id             the id of the quoteRatingDTO to save.
     * @param quoteRatingDTO the quoteRatingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated quoteRatingDTO,
     * or with status {@code 400 (Bad Request)} if the quoteRatingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the quoteRatingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<QuoteRatingDTO> updateQuoteRating(@RequestBody QuoteRatingDTO quoteRatingDTO) {
        QuoteRatingDTO result = quoteRatingService.update(quoteRatingDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, quoteRatingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  } : get all the quoteRatings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of quoteRatings in body.
     */
    @GetMapping("")
    public List<QuoteRatingDTO> getAllQuoteRatings() {
        log.debug("REST request to get all QuoteRatings");
        return quoteRatingService.findAll();
    }

    /**
     * {@code GET  /:id} : get the "id" quoteRating.
     *
     * @param id the id of the quoteRatingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the quoteRatingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<QuoteRatingDTO> getQuoteRating(@PathVariable Long id) {
        log.debug("REST request to get QuoteRating : {}", id);
        Optional<QuoteRatingDTO> quoteRatingDTO = quoteRatingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(quoteRatingDTO);
    }

    /**
     * {@code DELETE  /:id} : delete the "id" quoteRating.
     *
     * @param id the id of the quoteRatingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuoteRating(@PathVariable Long id) {
        log.debug("REST request to delete QuoteRating : {}", id);
        quoteRatingService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
