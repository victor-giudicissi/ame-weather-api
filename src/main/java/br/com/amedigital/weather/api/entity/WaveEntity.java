package br.com.amedigital.weather.api.entity;

import java.time.LocalDate;

public class WaveEntity {
    private String id;
    private String cityName;
    private String state;
    private LocalDate updatedAt;
    private int period;
    private LocalDate waveDay;
    private String unrest;
    private float height;
    private String direction;
    private float wind;
    private String windDirection;

    public WaveEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public LocalDate getWaveDay() {
        return waveDay;
    }

    public void setWaveDay(LocalDate waveDay) {
        this.waveDay = waveDay;
    }

    public String getUnrest() {
        return unrest;
    }

    public void setUnrest(String unrest) {
        this.unrest = unrest;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public float getWind() {
        return wind;
    }

    public void setWind(float wind) {
        this.wind = wind;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }
}