package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.QuoteRating;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import liquibase.pro.packaged.S;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Quote} entity.
 */
public class QuoteDTO implements Serializable {

    private Long id;

    private String content;

    private String author;

    private UserDataDTO userCreated;

    private List<QuoteRating> rating;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QuoteDTO)) {
            return false;
        }

        QuoteDTO quoteDTO = (QuoteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, quoteDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuoteDTO{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", author=" + getAuthor() +
            "}";
    }

    public UserDataDTO getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(UserDataDTO userCreated) {
        this.userCreated = userCreated;
    }

    public List<QuoteRating> getRating() {
        return rating;
    }

    public void setRating(List<QuoteRating> rating) {
        this.rating = rating;
    }
}
