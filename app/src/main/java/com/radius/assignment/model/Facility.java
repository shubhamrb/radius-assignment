package com.radius.assignment.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;

public class Facility implements Serializable {
    @SerializedName("facility_id")
    private String facility_id;

    @SerializedName("name")
    private String name;

    @SerializedName("options")
    private List<FacilityOptions> options;

    private int selected_option = -1;

    private ExclusionsModel selectedOption;

    public int getSelected_option() {
        return selected_option;
    }

    public void setSelected_option(int selected_option) {
        this.selected_option = selected_option;
    }

    public ExclusionsModel getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(ExclusionsModel selectedOption) {
        this.selectedOption = selectedOption;
    }

    public String getFacility_id() {
        return facility_id;
    }

    public void setFacility_id(String facility_id) {
        this.facility_id = facility_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FacilityOptions> getOptions() {
        return options;
    }

    public void setOptions(List<FacilityOptions> options) {
        this.options = options;
    }


    @Override
    public String toString() {
        return "{" +
                "facility_id='" + facility_id + '\'' +
                ", name='" + name + '\'' +
                ", options=" + options +
                ", selectedOption=" + selectedOption +
                '}';
    }

    public boolean isCombinationExcluded(List<ExclusionsModel> selectedOptions, List<List<ExclusionsModel>> exclusions) {
        for (List<ExclusionsModel> exclusion : exclusions) {
            if (new HashSet<>(selectedOptions).containsAll(exclusion)) {
                return true; // Combination violates an exclusion
            }
        }
        return false; // Combination is not excluded
    }
}

