package io.aurora.provider.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the MetaData entity.
 */
public class MetaDataDTO implements Serializable {

    private String id;

    private String name;

    private String value;

    private String vehicleId;

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MetaDataDTO metaDataDTO = (MetaDataDTO) o;
        if (metaDataDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), metaDataDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MetaDataDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", value='" + getValue() + "'" +
            ", vehicle=" + getVehicleId() +
            "}";
    }
}
