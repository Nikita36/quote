package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.UserDataRepository;
import com.mycompany.myapp.service.UserDataService;
import com.mycompany.myapp.service.dto.UserDataDTO;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.UserData}.
 */
@RestController
@RequestMapping("/api")
public class UserDataResource {

    private final Logger log = LoggerFactory.getLogger(UserDataResource.class);

    private static final String ENTITY_NAME = "quoteUserData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserDataService userDataService;

    private final UserDataRepository userDataRepository;

    public UserDataResource(UserDataService userDataService, UserDataRepository userDataRepository) {
        this.userDataService = userDataService;
        this.userDataRepository = userDataRepository;
    }

    /**
     * {@code POST  /user-data} : Create a new userData.
     *
     * @param userDataDTO the userDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userDataDTO, or with status {@code 400 (Bad Request)} if the userData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-data")
    public ResponseEntity<UserDataDTO> createUserData(@RequestBody UserDataDTO userDataDTO) throws URISyntaxException {
        log.debug("REST request to save UserData : {}", userDataDTO);
        if (userDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new userData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserDataDTO result = userDataService.save(userDataDTO);
        return ResponseEntity
            .created(new URI("/api/user-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-data/:id} : Updates an existing userData.
     *
     * @param id the id of the userDataDTO to save.
     * @param userDataDTO the userDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userDataDTO,
     * or with status {@code 400 (Bad Request)} if the userDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-data/{id}")
    public ResponseEntity<UserDataDTO> updateUserData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserDataDTO userDataDTO
    ) throws URISyntaxException {
        log.debug("REST request to update UserData : {}, {}", id, userDataDTO);
        if (userDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userDataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserDataDTO result = userDataService.update(userDataDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-data/:id} : Partial updates given fields of an existing userData, field will ignore if it is null
     *
     * @param id the id of the userDataDTO to save.
     * @param userDataDTO the userDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userDataDTO,
     * or with status {@code 400 (Bad Request)} if the userDataDTO is not valid,
     * or with status {@code 404 (Not Found)} if the userDataDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the userDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-data/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserDataDTO> partialUpdateUserData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserDataDTO userDataDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserData partially : {}, {}", id, userDataDTO);
        if (userDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userDataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserDataDTO> result = userDataService.partialUpdate(userDataDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userDataDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /user-data} : get all the userData.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userData in body.
     */
    @GetMapping("/user-data")
    public ResponseEntity<List<UserDataDTO>> getAllUserData(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false) String filter
    ) {
        if ("quote-is-null".equals(filter)) {
            log.debug("REST request to get all UserDatas where quote is null");
            return new ResponseEntity<>(userDataService.findAllWhereQuoteIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of UserData");
        Page<UserDataDTO> page = userDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-data/:id} : get the "id" userData.
     *
     * @param id the id of the userDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-data/{id}")
    public ResponseEntity<UserDataDTO> getUserData(@PathVariable Long id) {
        log.debug("REST request to get UserData : {}", id);
        Optional<UserDataDTO> userDataDTO = userDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userDataDTO);
    }

    /**
     * {@code DELETE  /user-data/:id} : delete the "id" userData.
     *
     * @param id the id of the userDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-data/{id}")
    public ResponseEntity<Void> deleteUserData(@PathVariable Long id) {
        log.debug("REST request to delete UserData : {}", id);
        userDataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/user-data?query=:query} : search for the userData corresponding
     * to the query.
     *
     * @param query the query of the userData search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/user-data")
    public ResponseEntity<List<UserDataDTO>> searchUserData(
        @RequestParam String query,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to search for a page of UserData for query {}", query);
        Page<UserDataDTO> page = userDataService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
