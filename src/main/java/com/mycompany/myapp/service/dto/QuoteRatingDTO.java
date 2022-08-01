package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.QuoteRating} entity.
 */
public class QuoteRatingDTO implements Serializable {

    private Long id;

    private Boolean isLike;

    private UserDataDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsLike() {
        return isLike;
    }

    public void setIsLike(Boolean isLike) {
        this.isLike = isLike;
    }

    public UserDataDTO getUser() {
        return user;
    }

    public void setUser(UserDataDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QuoteRatingDTO)) {
            return false;
        }

        QuoteRatingDTO quoteRatingDTO = (QuoteRatingDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, quoteRatingDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuoteRatingDTO{" +
            "id=" + getId() +
            ", isLike='" + getIsLike() + "'" +
            ", user=" + getUser() +
            "}";
    }
}
