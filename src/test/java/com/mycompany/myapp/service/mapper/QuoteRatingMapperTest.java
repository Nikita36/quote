package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QuoteRatingMapperTest {

    private QuoteRatingMapper quoteRatingMapper;

    @BeforeEach
    public void setUp() {
        quoteRatingMapper = new QuoteRatingMapperImpl();
    }
}
