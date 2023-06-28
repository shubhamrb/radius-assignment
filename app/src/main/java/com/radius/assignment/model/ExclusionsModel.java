package com.radius.assignment.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ExclusionsModel implements Serializable {
    @SerializedName("facility_id")
    private String facility_id;

    @SerializedName("options_id")
    private String options_id;


    public String getFacility_id() {
        return facility_id;
    }

    public void setFacility_id(String facility_id) {
        this.facility_id = facility_id;
    }

    public String getOptions_id() {
        return options_id;
    }

    public void setOptions_id(String options_id) {
        this.options_id = options_id;
    }

    @Override
    public String toString() {
        return "{" +
                "facility_id='" + facility_id + '\'' +
                ", options_id='" + options_id + '\'' +
                '}';
    }
}

