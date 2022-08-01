package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class QuoteRatingDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuoteRatingDTO.class);
        QuoteRatingDTO quoteRatingDTO1 = new QuoteRatingDTO();
        quoteRatingDTO1.setId(1L);
        QuoteRatingDTO quoteRatingDTO2 = new QuoteRatingDTO();
        assertThat(quoteRatingDTO1).isNotEqualTo(quoteRatingDTO2);
        quoteRatingDTO2.setId(quoteRatingDTO1.getId());
        assertThat(quoteRatingDTO1).isEqualTo(quoteRatingDTO2);
        quoteRatingDTO2.setId(2L);
        assertThat(quoteRatingDTO1).isNotEqualTo(quoteRatingDTO2);
        quoteRatingDTO1.setId(null);
        assertThat(quoteRatingDTO1).isNotEqualTo(quoteRatingDTO2);
    }
}
