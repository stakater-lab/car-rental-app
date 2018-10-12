package io.aurora.provider.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Rating.
 */
@Document(collection = "rating")
public class Rating implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("commulative")
    private Double commulative;

    @DBRef
    @Field("feedback")
    private Set<FeedBack> feedbacks = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getCommulative() {
        return commulative;
    }

    public Rating commulative(Double commulative) {
        this.commulative = commulative;
        return this;
    }

    public void setCommulative(Double commulative) {
        this.commulative = commulative;
    }

    public Set<FeedBack> getFeedbacks() {
        return feedbacks;
    }

    public Rating feedbacks(Set<FeedBack> feedBacks) {
        this.feedbacks = feedBacks;
        return this;
    }

    public Rating addFeedback(FeedBack feedBack) {
        this.feedbacks.add(feedBack);
        feedBack.setRating(this);
        return this;
    }

    public Rating removeFeedback(FeedBack feedBack) {
        this.feedbacks.remove(feedBack);
        feedBack.setRating(null);
        return this;
    }

    public void setFeedbacks(Set<FeedBack> feedBacks) {
        this.feedbacks = feedBacks;
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
        Rating rating = (Rating) o;
        if (rating.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rating.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Rating{" +
            "id=" + getId() +
            ", commulative=" + getCommulative() +
            "}";
    }
}
