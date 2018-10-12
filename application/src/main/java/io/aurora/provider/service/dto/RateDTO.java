package io.aurora.provider.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the Rate entity.
 */
public class RateDTO implements Serializable {

    private String id;

    private BigDecimal hourly;

    private BigDecimal hourlyWithoutDriver;

    private BigDecimal daily;

    private BigDecimal dailyWithoutDriver;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getHourly() {
        return hourly;
    }

    public void setHourly(BigDecimal hourly) {
        this.hourly = hourly;
    }

    public BigDecimal getHourlyWithoutDriver() {
        return hourlyWithoutDriver;
    }

    public void setHourlyWithoutDriver(BigDecimal hourlyWithoutDriver) {
        this.hourlyWithoutDriver = hourlyWithoutDriver;
    }

    public BigDecimal getDaily() {
        return daily;
    }

    public void setDaily(BigDecimal daily) {
        this.daily = daily;
    }

    public BigDecimal getDailyWithoutDriver() {
        return dailyWithoutDriver;
    }

    public void setDailyWithoutDriver(BigDecimal dailyWithoutDriver) {
        this.dailyWithoutDriver = dailyWithoutDriver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RateDTO rateDTO = (RateDTO) o;
        if (rateDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rateDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RateDTO{" +
            "id=" + getId() +
            ", hourly=" + getHourly() +
            ", hourlyWithoutDriver=" + getHourlyWithoutDriver() +
            ", daily=" + getDaily() +
            ", dailyWithoutDriver=" + getDailyWithoutDriver() +
            "}";
    }
}
