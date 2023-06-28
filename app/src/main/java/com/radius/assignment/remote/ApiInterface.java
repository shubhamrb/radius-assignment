package com.radius.assignment.remote;

import com.google.gson.JsonObject;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("ad-assignment/db")
    Observable<JsonObject> getFacilities();
}
