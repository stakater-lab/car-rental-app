package io.aurora.provider.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the VehicleModel entity.
 */
public class VehicleModelDTO implements Serializable {

    private String id;

    private String name;

    private Integer year;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VehicleModelDTO vehicleModelDTO = (VehicleModelDTO) o;
        if (vehicleModelDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vehicleModelDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VehicleModelDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", year=" + getYear() +
            "}";
    }
}
