package io.aurora.provider.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Rate.
 */
@Document(collection = "rate")
public class Rate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("hourly")
    private BigDecimal hourly;

    @Field("hourly_without_driver")
    private BigDecimal hourlyWithoutDriver;

    @Field("daily")
    private BigDecimal daily;

    @Field("daily_without_driver")
    private BigDecimal dailyWithoutDriver;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getHourly() {
        return hourly;
    }

    public Rate hourly(BigDecimal hourly) {
        this.hourly = hourly;
        return this;
    }

    public void setHourly(BigDecimal hourly) {
        this.hourly = hourly;
    }

    public BigDecimal getHourlyWithoutDriver() {
        return hourlyWithoutDriver;
    }

    public Rate hourlyWithoutDriver(BigDecimal hourlyWithoutDriver) {
        this.hourlyWithoutDriver = hourlyWithoutDriver;
        return this;
    }

    public void setHourlyWithoutDriver(BigDecimal hourlyWithoutDriver) {
        this.hourlyWithoutDriver = hourlyWithoutDriver;
    }

    public BigDecimal getDaily() {
        return daily;
    }

    public Rate daily(BigDecimal daily) {
        this.daily = daily;
        return this;
    }

    public void setDaily(BigDecimal daily) {
        this.daily = daily;
    }

    public BigDecimal getDailyWithoutDriver() {
        return dailyWithoutDriver;
    }

    public Rate dailyWithoutDriver(BigDecimal dailyWithoutDriver) {
        this.dailyWithoutDriver = dailyWithoutDriver;
        return this;
    }

    public void setDailyWithoutDriver(BigDecimal dailyWithoutDriver) {
        this.dailyWithoutDriver = dailyWithoutDriver;
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
        Rate rate = (Rate) o;
        if (rate.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rate.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Rate{" +
            "id=" + getId() +
            ", hourly=" + getHourly() +
            ", hourlyWithoutDriver=" + getHourlyWithoutDriver() +
            ", daily=" + getDaily() +
            ", dailyWithoutDriver=" + getDailyWithoutDriver() +
            "}";
    }
}
