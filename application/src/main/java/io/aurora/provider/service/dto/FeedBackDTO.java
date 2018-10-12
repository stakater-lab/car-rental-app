package io.aurora.provider.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the FeedBack entity.
 */
public class FeedBackDTO implements Serializable {

    private String id;

    private Double score;

    private String comment;

    private String ratingId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRatingId() {
        return ratingId;
    }

    public void setRatingId(String ratingId) {
        this.ratingId = ratingId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FeedBackDTO feedBackDTO = (FeedBackDTO) o;
        if (feedBackDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), feedBackDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FeedBackDTO{" +
            "id=" + getId() +
            ", score=" + getScore() +
            ", comment='" + getComment() + "'" +
            ", rating=" + getRatingId() +
            "}";
    }
}
