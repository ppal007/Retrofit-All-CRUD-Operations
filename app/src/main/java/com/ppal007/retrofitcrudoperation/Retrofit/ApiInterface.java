package com.ppal007.retrofitcrudoperation.Retrofit;

import com.ppal007.retrofitcrudoperation.Model.RetriveAllModel;
import com.ppal007.retrofitcrudoperation.Model.StudentModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
public interface ApiInterface {

//    registration.......................................................
    @FormUrlEncoded
    @POST("register.php")
    Call<StudentModel> studentRegistration(
            @Field("user_name") String userName,
            @Field("user_email") String userEmail,
            @Field("user_phone") String userPhone,
            @Field("user_password") String userPassword
    );


//    login.....................................................................
    @FormUrlEncoded
    @POST("login.php")
    Call<StudentModel> studentLogin(
            @Field("user_email") String userEmail,
            @Field("user_password") String userPassword
    );


//    retrieve add data..............................................................
    @GET("retriveAll.php")
    Call<List<RetriveAllModel>> getAllData();


//    update student information...............................................
    @FormUrlEncoded
    @POST("updateUser.php")
    Call<StudentModel> updateUser(
            @Field("id") String id,
            @Field("user_name") String userName,
            @Field("user_email") String userEmail,
            @Field("user_phone") String userPhone
    );


//    delete user..........................................................................
    @FormUrlEncoded
    @POST("deleteUser.php")
    Call<StudentModel> deleteUser(@Field("id") String id);



}
