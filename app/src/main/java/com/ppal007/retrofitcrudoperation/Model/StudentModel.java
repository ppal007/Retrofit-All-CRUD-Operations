package com.ppal007.retrofitcrudoperation.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudentModel {

    @SerializedName("user_name")
    @Expose
    private String userName;

    @SerializedName("user_email")
    @Expose
    private String userEmail;

    @SerializedName("user_phone")
    @Expose
    private String userPhone;

    @SerializedName("user_password")
    @Expose
    private String userPassword;

    @SerializedName("success")
    @Expose
    private boolean success;

    @SerializedName("message")
    @Expose
    private String message;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
