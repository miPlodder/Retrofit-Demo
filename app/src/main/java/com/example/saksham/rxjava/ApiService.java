package com.example.saksham.rxjava;

/*
 * Created by saksham on 11/May/2018
 */

import io.reactivex.*;

import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("users/{id}")
    Observable<MyPojo> getUser(@Path("id") int id);
}
