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

import io.aurora.provider.domain.enumeration.VehicleAvailability;

import io.aurora.provider.domain.enumeration.DriverAvailability;

/**
 * A Vehicle.
 */
@Document(collection = "vehicle")
public class Vehicle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("owner_id")
    private Integer ownerId;

    @Field("availability")
    private VehicleAvailability availability;

    @Field("driver")
    private DriverAvailability driver;

    @Field("images")
    private byte[] images;

    @Field("images_content_type")
    private String imagesContentType;

    @DBRef
    @Field("rating")
    private Rating rating;

    @DBRef
    @Field("type")
    private VehicleType type;

    @DBRef
    @Field("rate")
    private Rate rate;

    @DBRef
    @Field("detail")
    private VehicleDetails detail;

    @DBRef
    @Field("metaData")
    private Set<MetaData> metaData = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public Vehicle ownerId(Integer ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public VehicleAvailability getAvailability() {
        return availability;
    }

    public Vehicle availability(VehicleAvailability availability) {
        this.availability = availability;
        return this;
    }

    public void setAvailability(VehicleAvailability availability) {
        this.availability = availability;
    }

    public DriverAvailability getDriver() {
        return driver;
    }

    public Vehicle driver(DriverAvailability driver) {
        this.driver = driver;
        return this;
    }

    public void setDriver(DriverAvailability driver) {
        this.driver = driver;
    }

    public byte[] getImages() {
        return images;
    }

    public Vehicle images(byte[] images) {
        this.images = images;
        return this;
    }

    public void setImages(byte[] images) {
        this.images = images;
    }

    public String getImagesContentType() {
        return imagesContentType;
    }

    public Vehicle imagesContentType(String imagesContentType) {
        this.imagesContentType = imagesContentType;
        return this;
    }

    public void setImagesContentType(String imagesContentType) {
        this.imagesContentType = imagesContentType;
    }

    public Rating getRating() {
        return rating;
    }

    public Vehicle rating(Rating rating) {
        this.rating = rating;
        return this;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public VehicleType getType() {
        return type;
    }

    public Vehicle type(VehicleType vehicleType) {
        this.type = vehicleType;
        return this;
    }

    public void setType(VehicleType vehicleType) {
        this.type = vehicleType;
    }

    public Rate getRate() {
        return rate;
    }

    public Vehicle rate(Rate rate) {
        this.rate = rate;
        return this;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }

    public VehicleDetails getDetail() {
        return detail;
    }

    public Vehicle detail(VehicleDetails vehicleDetails) {
        this.detail = vehicleDetails;
        return this;
    }

    public void setDetail(VehicleDetails vehicleDetails) {
        this.detail = vehicleDetails;
    }

    public Set<MetaData> getMetaData() {
        return metaData;
    }

    public Vehicle metaData(Set<MetaData> metaData) {
        this.metaData = metaData;
        return this;
    }

    public Vehicle addMetaData(MetaData metaData) {
        this.metaData.add(metaData);
        metaData.setVehicle(this);
        return this;
    }

    public Vehicle removeMetaData(MetaData metaData) {
        this.metaData.remove(metaData);
        metaData.setVehicle(null);
        return this;
    }

    public void setMetaData(Set<MetaData> metaData) {
        this.metaData = metaData;
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
        Vehicle vehicle = (Vehicle) o;
        if (vehicle.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vehicle.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Vehicle{" +
            "id=" + getId() +
            ", ownerId=" + getOwnerId() +
            ", availability='" + getAvailability() + "'" +
            ", driver='" + getDriver() + "'" +
            ", images='" + getImages() + "'" +
            ", imagesContentType='" + getImagesContentType() + "'" +
            "}";
    }
}
