package io.aurora.provider.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.aurora.provider.domain.enumeration.VehicleAvailability;
import io.aurora.provider.domain.enumeration.DriverAvailability;

/**
 * A DTO for the Vehicle entity.
 */
public class VehicleDTO implements Serializable {

    private String id;

    private Integer ownerId;

    private VehicleAvailability availability;

    private DriverAvailability driver;

    private byte[] images;
    private String imagesContentType;

    private String ratingId;

    private String typeId;

    private String rateId;

    private String detailId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public VehicleAvailability getAvailability() {
        return availability;
    }

    public void setAvailability(VehicleAvailability availability) {
        this.availability = availability;
    }

    public DriverAvailability getDriver() {
        return driver;
    }

    public void setDriver(DriverAvailability driver) {
        this.driver = driver;
    }

    public byte[] getImages() {
        return images;
    }

    public void setImages(byte[] images) {
        this.images = images;
    }

    public String getImagesContentType() {
        return imagesContentType;
    }

    public void setImagesContentType(String imagesContentType) {
        this.imagesContentType = imagesContentType;
    }

    public String getRatingId() {
        return ratingId;
    }

    public void setRatingId(String ratingId) {
        this.ratingId = ratingId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String vehicleTypeId) {
        this.typeId = vehicleTypeId;
    }

    public String getRateId() {
        return rateId;
    }

    public void setRateId(String rateId) {
        this.rateId = rateId;
    }

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String vehicleDetailsId) {
        this.detailId = vehicleDetailsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VehicleDTO vehicleDTO = (VehicleDTO) o;
        if (vehicleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vehicleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VehicleDTO{" +
            "id=" + getId() +
            ", ownerId=" + getOwnerId() +
            ", availability='" + getAvailability() + "'" +
            ", driver='" + getDriver() + "'" +
            ", images='" + getImages() + "'" +
            ", rating=" + getRatingId() +
            ", type=" + getTypeId() +
            ", rate=" + getRateId() +
            ", detail=" + getDetailId() +
            "}";
    }
}
