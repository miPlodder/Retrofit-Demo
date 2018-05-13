package com.example.saksham.rxjava;

/*
 * Created by saksham on 11/May/2018
 */

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {

    public static final String BASE_URL = "https://reqres.in/api/"; //users/1
    public static Retrofit retrofit = null ;

    public static Retrofit getApiClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    /*
                        adding ConverterFactory helps in serialising and de-serialising the json
                        for example there are many libraries for this purpose like Gson, Moshi
                     */
                    .addConverterFactory(GsonConverterFactory.create())
                    /*  adding AdapterFactory helps us in adding support for rx
                        otherwise, we would have to use Call return type to work, instead of Observable
                    */
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    /*  adding client helps us to customize HTTP message
                        For example, adding query to every URL request like Auth Token
                        We can do this using OkHttpClient, instead of adding argument to every retrofit request
                    */
                    .client(new OkHttpClient())
                    .build();
        }
        return retrofit;
    }

}
