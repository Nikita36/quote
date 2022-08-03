package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Quote;
import com.mycompany.myapp.domain.QuoteRating;
import com.mycompany.myapp.service.mapper.QuoteRatingMapper;
import java.util.List;
import org.mapstruct.Mapper;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Quote entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {
    @Query(
        nativeQuery = true,
        value = "SELECT * FROM quote WHERE id IN (SELECT quote_id " +
        "FROM quote_rating " +
        "WHERE is_like = TRUE " +
        "GROUP BY quote_id " +
        "ORDER BY COUNT(*) DESC)"
    )
    List<Quote> findTopTen();

    @Query(
        nativeQuery = true,
        value = "SELECT * FROM quote WHERE id IN (SELECT quote_id " +
        "FROM quote_rating " +
        "WHERE is_like = FALSE " +
        "GROUP BY quote_id " +
        "ORDER BY COUNT(*) DESC)"
    )
    List<Quote> findFlopTen();

    @Query(nativeQuery = true, value = "SELECT * FROM quote\n" + "ORDER BY RANDOM()\n" + "LIMIT 1")
    Quote findRandom();
}
