package com.radius.assignment.presenter;

import com.radius.assignment.model.FacilityResponse;

public interface MainContract {
    interface View {
        void showData(FacilityResponse data);
    }

    interface Presenter {
        void fetchData();
    }
}