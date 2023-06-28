package com.radius.assignment.presenter;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.radius.assignment.model.FacilityResponse;
import com.radius.assignment.remote.ApiInterface;
import com.radius.assignment.remote.ApiManager;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainPresenter implements MainContract.Presenter {
    private final MainContract.View view;

    public MainPresenter(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void fetchData() {
        ApiInterface apiInterface = ApiManager.getApiInterface();

        apiInterface.getFacilities()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(JsonObject jsonObject) {
                        FacilityResponse facilityResponse = new Gson().fromJson(jsonObject.toString(), FacilityResponse.class);
                        view.showData(facilityResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }
}


