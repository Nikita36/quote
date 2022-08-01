package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class QuoteRatingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuoteRating.class);
        QuoteRating quoteRating1 = new QuoteRating();
        quoteRating1.setId(1L);
        QuoteRating quoteRating2 = new QuoteRating();
        quoteRating2.setId(quoteRating1.getId());
        assertThat(quoteRating1).isEqualTo(quoteRating2);
        quoteRating2.setId(2L);
        assertThat(quoteRating1).isNotEqualTo(quoteRating2);
        quoteRating1.setId(null);
        assertThat(quoteRating1).isNotEqualTo(quoteRating2);
    }
}
