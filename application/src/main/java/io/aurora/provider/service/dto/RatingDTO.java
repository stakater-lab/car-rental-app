package io.aurora.provider.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Rating entity.
 */
public class RatingDTO implements Serializable {

    private String id;

    private Double commulative;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getCommulative() {
        return commulative;
    }

    public void setCommulative(Double commulative) {
        this.commulative = commulative;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RatingDTO ratingDTO = (RatingDTO) o;
        if (ratingDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ratingDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RatingDTO{" +
            "id=" + getId() +
            ", commulative=" + getCommulative() +
            "}";
    }
}
