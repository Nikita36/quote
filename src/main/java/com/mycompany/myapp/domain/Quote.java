package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Quote.
 */
@Entity
@Table(name = "quote")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Quote implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "author")
    private String author;

    @JsonIgnoreProperties(value = { "quoteRatings", "quote" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private UserData userCreated;

    @OneToMany
    @JoinColumn(name = "quote_id")
    private List<QuoteRating> rating;

    public Quote() {}

    public Long getId() {
        return this.id;
    }

    public Quote id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return this.content;
    }

    public Quote content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserData getUserCreated() {
        return this.userCreated;
    }

    public void setUserCreated(UserData userData) {
        this.userCreated = userData;
    }

    public Quote author(UserData userData) {
        this.setUserCreated(userData);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Quote)) {
            return false;
        }
        return id != null && id.equals(((Quote) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Quote{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            "}";
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<QuoteRating> getRating() {
        return rating;
    }

    public void setRating(List<QuoteRating> rating) {
        this.rating = rating;
    }
}
