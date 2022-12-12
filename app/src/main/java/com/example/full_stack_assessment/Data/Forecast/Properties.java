package com.example.full_stack_assessment.Data.Forecast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Properties {
    @SerializedName("updated")
    @Expose
    private String updated;
    @SerializedName("units")
    @Expose
    private String units;
    @SerializedName("forecastGenerator")
    @Expose
    private String forecastGenerator;
    @SerializedName("generatedAt")
    @Expose
    private String generatedAt;
    @SerializedName("updateTime")
    @Expose
    private String updateTime;
    @SerializedName("validTimes")
    @Expose
    private String validTimes;
    @SerializedName("periods")
    @Expose
    private List<Period> periods = null;

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getForecastGenerator() {
        return forecastGenerator;
    }

    public void setForecastGenerator(String forecastGenerator) {
        this.forecastGenerator = forecastGenerator;
    }

    public String getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(String generatedAt) {
        this.generatedAt = generatedAt;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getValidTimes() {
        return validTimes;
    }

    public void setValidTimes(String validTimes) {
        this.validTimes = validTimes;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    public void setPeriods(List<Period> periods) {
        this.periods = periods;
    }
}
