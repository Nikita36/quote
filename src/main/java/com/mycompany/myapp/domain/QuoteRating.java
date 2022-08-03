package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A QuoteRating.
 */
@Entity
@Table(name = "quote_rating")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class QuoteRating implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "is_like")
    private Boolean isLike;

    @Column(name = "quote_id")
    private Long quote_id;

    @ManyToOne
    @JsonIgnoreProperties(value = { "quoteRatings", "quote" }, allowSetters = true)
    private UserData user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public QuoteRating id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsLike() {
        return this.isLike;
    }

    public QuoteRating isLike(Boolean isLike) {
        this.setIsLike(isLike);
        return this;
    }

    public void setIsLike(Boolean isLike) {
        this.isLike = isLike;
    }

    public UserData getUser() {
        return this.user;
    }

    public void setUser(UserData userData) {
        this.user = userData;
    }

    public QuoteRating user(UserData userData) {
        this.setUser(userData);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QuoteRating)) {
            return false;
        }
        return id != null && id.equals(((QuoteRating) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuoteRating{" +
            "id=" + getId() +
            ", isLike='" + getIsLike() + "'" +
            "}";
    }

    public Long getQuote_id() {
        return quote_id;
    }

    public void setQuote_id(Long quote_id) {
        this.quote_id = quote_id;
    }
}
