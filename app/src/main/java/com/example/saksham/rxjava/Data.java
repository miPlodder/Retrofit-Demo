package com.example.saksham.rxjava;

/*
 * Created by saksham on 11/May/2018
 */

import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("id")
    int id;

    @SerializedName("first_name")
    String firstName;

    @SerializedName("last_name")
    String lastName;

    @SerializedName("avatar")
    String avatar;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAvatar() {
        return avatar;
    }
}
