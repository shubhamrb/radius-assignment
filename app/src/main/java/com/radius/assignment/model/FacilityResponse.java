package com.radius.assignment.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class FacilityResponse implements Serializable {

    @SerializedName("facilities")
    private List<Facility> facilities;

    @SerializedName("exclusions")
    private List<List<ExclusionsModel>> exclusions;


    public List<Facility> getFacilities() {
        return facilities;
    }

    public void setFacilities(List<Facility> facilities) {
        this.facilities = facilities;
    }

    public List<List<ExclusionsModel>> getExclusions() {
        return exclusions;
    }

    public void setExclusions(List<List<ExclusionsModel>> exclusions) {
        this.exclusions = exclusions;
    }

    @Override
    public String toString() {
        return "{" +
                "facilities=" + facilities +
                ", exclusions=" + exclusions +
                '}';
    }
}

