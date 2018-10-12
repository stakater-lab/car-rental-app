package io.aurora.provider.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.aurora.provider.domain.enumeration.ProviderType;

/**
 * A DTO for the Provider entity.
 */
public class ProviderDTO implements Serializable {

    private String id;

    private ProviderType type;

    private String name;

    private Long ratings;

    private Long noOfVehicles;

    private String emailAddress;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ProviderType getType() {
        return type;
    }

    public void setType(ProviderType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getRatings() {
        return ratings;
    }

    public void setRatings(Long ratings) {
        this.ratings = ratings;
    }

    public Long getNoOfVehicles() {
        return noOfVehicles;
    }

    public void setNoOfVehicles(Long noOfVehicles) {
        this.noOfVehicles = noOfVehicles;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProviderDTO providerDTO = (ProviderDTO) o;
        if (providerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), providerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProviderDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", name='" + getName() + "'" +
            ", ratings=" + getRatings() +
            ", noOfVehicles=" + getNoOfVehicles() +
            ", emailAddress='" + getEmailAddress() + "'" +
            "}";
    }
}
