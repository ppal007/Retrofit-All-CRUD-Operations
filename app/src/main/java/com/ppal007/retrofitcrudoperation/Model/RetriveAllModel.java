package com.ppal007.retrofitcrudoperation.Model;

import com.google.gson.annotations.SerializedName;

public class RetriveAllModel {

    @SerializedName("user_name")
    private String userName;

    @SerializedName("user_email")
    private String userEmail;

    @SerializedName("user_phone")
    private String userPhone;

    @SerializedName("id")
    private String id;


    public RetriveAllModel(String userName, String userEmail, String userPhone, String id) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public String getId() {
        return id;
    }
}
