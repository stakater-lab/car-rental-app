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

import io.aurora.provider.domain.enumeration.ProviderType;

/**
 * A Provider.
 */
@Document(collection = "provider")
public class Provider implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("type")
    private ProviderType type;

    @Field("name")
    private String name;

    @Field("ratings")
    private Long ratings;

    @Field("no_of_vehicles")
    private Long noOfVehicles;

    @Field("email_address")
    private String emailAddress;

    @DBRef
    @Field("address")
    private Set<Address> addresses = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ProviderType getType() {
        return type;
    }

    public Provider type(ProviderType type) {
        this.type = type;
        return this;
    }

    public void setType(ProviderType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Provider name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getRatings() {
        return ratings;
    }

    public Provider ratings(Long ratings) {
        this.ratings = ratings;
        return this;
    }

    public void setRatings(Long ratings) {
        this.ratings = ratings;
    }

    public Long getNoOfVehicles() {
        return noOfVehicles;
    }

    public Provider noOfVehicles(Long noOfVehicles) {
        this.noOfVehicles = noOfVehicles;
        return this;
    }

    public void setNoOfVehicles(Long noOfVehicles) {
        this.noOfVehicles = noOfVehicles;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public Provider emailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public Provider addresses(Set<Address> addresses) {
        this.addresses = addresses;
        return this;
    }

    public Provider addAddress(Address address) {
        this.addresses.add(address);
        address.setProvider(this);
        return this;
    }

    public Provider removeAddress(Address address) {
        this.addresses.remove(address);
        address.setProvider(null);
        return this;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
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
        Provider provider = (Provider) o;
        if (provider.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), provider.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Provider{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", name='" + getName() + "'" +
            ", ratings=" + getRatings() +
            ", noOfVehicles=" + getNoOfVehicles() +
            ", emailAddress='" + getEmailAddress() + "'" +
            "}";
    }
}
