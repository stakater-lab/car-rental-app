package io.aurora.provider.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.Objects;

import io.aurora.provider.domain.enumeration.Transmission;

import io.aurora.provider.domain.enumeration.Fuel;

/**
 * A VehicleDetails.
 */
@Document(collection = "vehicle_details")
public class VehicleDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("manufacturer")
    private String manufacturer;

    @Field("color")
    private String color;

    @Field("transmission")
    private Transmission transmission;

    @Field("fuel")
    private Fuel fuel;

    @DBRef
    @Field("model")
    private VehicleModel model;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public VehicleDetails manufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
        return this;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getColor() {
        return color;
    }

    public VehicleDetails color(String color) {
        this.color = color;
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Transmission getTransmission() {
        return transmission;
    }

    public VehicleDetails transmission(Transmission transmission) {
        this.transmission = transmission;
        return this;
    }

    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }

    public Fuel getFuel() {
        return fuel;
    }

    public VehicleDetails fuel(Fuel fuel) {
        this.fuel = fuel;
        return this;
    }

    public void setFuel(Fuel fuel) {
        this.fuel = fuel;
    }

    public VehicleModel getModel() {
        return model;
    }

    public VehicleDetails model(VehicleModel vehicleModel) {
        this.model = vehicleModel;
        return this;
    }

    public void setModel(VehicleModel vehicleModel) {
        this.model = vehicleModel;
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
        VehicleDetails vehicleDetails = (VehicleDetails) o;
        if (vehicleDetails.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vehicleDetails.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VehicleDetails{" +
            "id=" + getId() +
            ", manufacturer='" + getManufacturer() + "'" +
            ", color='" + getColor() + "'" +
            ", transmission='" + getTransmission() + "'" +
            ", fuel='" + getFuel() + "'" +
            "}";
    }
}
