package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.UserData;
import com.mycompany.myapp.repository.UserDataRepository;
import com.mycompany.myapp.service.UserDataService;
import com.mycompany.myapp.service.dto.UserDataDTO;
import com.mycompany.myapp.service.mapper.UserDataMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link UserData}.
 */
@Service
@Transactional
public class UserDataServiceImpl implements UserDataService {

    private final Logger log = LoggerFactory.getLogger(UserDataServiceImpl.class);

    private final UserDataRepository userDataRepository;

    private final UserDataMapper userDataMapper;

    public UserDataServiceImpl(UserDataRepository userDataRepository, UserDataMapper userDataMapper) {
        this.userDataRepository = userDataRepository;
        this.userDataMapper = userDataMapper;
    }

    @Override
    public UserDataDTO save(UserDataDTO userDataDTO) {
        log.debug("Request to save UserData : {}", userDataDTO);
        UserData userData = userDataMapper.toEntity(userDataDTO);
        userData = userDataRepository.save(userData);
        return userDataMapper.toDto(userData);
    }

    @Override
    public UserDataDTO update(UserDataDTO userDataDTO) {
        log.debug("Request to save UserData : {}", userDataDTO);
        UserData userData = userDataMapper.toEntity(userDataDTO);
        userData = userDataRepository.save(userData);
        return userDataMapper.toDto(userData);
    }

    @Override
    public Optional<UserDataDTO> partialUpdate(UserDataDTO userDataDTO) {
        log.debug("Request to partially update UserData : {}", userDataDTO);

        return userDataRepository
            .findById(userDataDTO.getId())
            .map(existingUserData -> {
                userDataMapper.partialUpdate(existingUserData, userDataDTO);

                return existingUserData;
            })
            .map(userDataRepository::save)
            .map(userDataMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserDataDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserData");
        return userDataRepository.findAll(pageable).map(userDataMapper::toDto);
    }

    /**
     *  Get all the userData where Quote is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<UserDataDTO> findAllWhereQuoteIsNull() {
        log.debug("Request to get all userData where Quote is null");
        return StreamSupport
            .stream(userDataRepository.findAll().spliterator(), false)
            .filter(userData -> userData.getQuote() == null)
            .map(userDataMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDataDTO> findOne(Long id) {
        log.debug("Request to get UserData : {}", id);
        return userDataRepository.findById(id).map(userDataMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserData : {}", id);
        userDataRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserDataDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of UserData for query {}", query);
        return userDataRepository.search(query, pageable).map(userDataMapper::toDto);
    }
}
