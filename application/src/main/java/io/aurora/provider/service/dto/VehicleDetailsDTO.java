package io.aurora.provider.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.aurora.provider.domain.enumeration.Transmission;
import io.aurora.provider.domain.enumeration.Fuel;

/**
 * A DTO for the VehicleDetails entity.
 */
public class VehicleDetailsDTO implements Serializable {

    private String id;

    private String manufacturer;

    private String color;

    private Transmission transmission;

    private Fuel fuel;

    private String modelId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Transmission getTransmission() {
        return transmission;
    }

    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }

    public Fuel getFuel() {
        return fuel;
    }

    public void setFuel(Fuel fuel) {
        this.fuel = fuel;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String vehicleModelId) {
        this.modelId = vehicleModelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VehicleDetailsDTO vehicleDetailsDTO = (VehicleDetailsDTO) o;
        if (vehicleDetailsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vehicleDetailsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VehicleDetailsDTO{" +
            "id=" + getId() +
            ", manufacturer='" + getManufacturer() + "'" +
            ", color='" + getColor() + "'" +
            ", transmission='" + getTransmission() + "'" +
            ", fuel='" + getFuel() + "'" +
            ", model=" + getModelId() +
            "}";
    }
}
