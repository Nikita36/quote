package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.QuoteRating;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the QuoteRating entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuoteRatingRepository extends JpaRepository<QuoteRating, Long> {}
