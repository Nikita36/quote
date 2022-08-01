package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.UserDataDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.UserData}.
 */
public interface UserDataService {
    /**
     * Save a userData.
     *
     * @param userDataDTO the entity to save.
     * @return the persisted entity.
     */
    UserDataDTO save(UserDataDTO userDataDTO);

    /**
     * Updates a userData.
     *
     * @param userDataDTO the entity to update.
     * @return the persisted entity.
     */
    UserDataDTO update(UserDataDTO userDataDTO);

    /**
     * Partially updates a userData.
     *
     * @param userDataDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<UserDataDTO> partialUpdate(UserDataDTO userDataDTO);

    /**
     * Get all the userData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserDataDTO> findAll(Pageable pageable);
    /**
     * Get all the UserDataDTO where Quote is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<UserDataDTO> findAllWhereQuoteIsNull();

    /**
     * Get the "id" userData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserDataDTO> findOne(Long id);

    /**
     * Delete the "id" userData.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the userData corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserDataDTO> search(String query, Pageable pageable);
}
