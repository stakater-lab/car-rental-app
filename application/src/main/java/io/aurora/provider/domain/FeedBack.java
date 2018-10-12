package io.aurora.provider.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.Objects;

/**
 * A FeedBack.
 */
@Document(collection = "feed_back")
public class FeedBack implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("score")
    private Double score;

    @Field("comment")
    private String comment;

    @DBRef
    @Field("rating")
    @JsonIgnoreProperties("feedbacks")
    private Rating rating;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getScore() {
        return score;
    }

    public FeedBack score(Double score) {
        this.score = score;
        return this;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public FeedBack comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Rating getRating() {
        return rating;
    }

    public FeedBack rating(Rating rating) {
        this.rating = rating;
        return this;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FeedBack feedBack = (FeedBack) o;
        if (feedBack.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), feedBack.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FeedBack{" +
            "id=" + getId() +
            ", score=" + getScore() +
            ", comment='" + getComment() + "'" +
            "}";
    }
}
