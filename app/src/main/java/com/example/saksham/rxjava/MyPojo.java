package com.example.saksham.rxjava;

/*
 * Created by saksham on 11/May/2018
 */

import com.google.gson.annotations.SerializedName;

public class MyPojo {

    /*
        serialized-name is used during serialising and deserializing the model class
        this annotation should be used, when we have Proguard is enabled (which obscured the variable, method and class names)
     */

    @SerializedName("data")
    Data dataPojo;

    public Data getDataPojo() {
        return dataPojo;
    }
}
