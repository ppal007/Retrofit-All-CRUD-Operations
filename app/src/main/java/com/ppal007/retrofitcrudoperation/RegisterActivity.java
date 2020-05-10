package com.ppal007.retrofitcrudoperation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ppal007.retrofitcrudoperation.Model.StudentModel;
import com.ppal007.retrofitcrudoperation.Retrofit.ApiClient;
import com.ppal007.retrofitcrudoperation.Retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText userName,userEmail,userPhone,userPass;
    private TextView textViewLogin;
    private Button buttonRegister;

    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ininViews();

//        text view login click listener...........................................
        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

//        register button click listener...................................................
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrationFunction();
            }
        });
    }

    private void registrationFunction() {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        String _userName     = userName.getText().toString();
        String _userEmail    = userEmail.getText().toString();
        String _userPhone    = userPhone.getText().toString().trim();
        String _userPassword = userPass.getText().toString().trim();

        if (_userName.isEmpty()){
            userName.requestFocus();
            userName.setError("Enter User Name");
        }else if (_userEmail.isEmpty()){
            userEmail.requestFocus();
            userEmail.setError("Enter User Email-Address");
        }else if (!Patterns.EMAIL_ADDRESS.matcher(_userEmail).matches()){
            userEmail.requestFocus();
            userEmail.setError("Enter Valid Email-Address");
        }else if (_userPhone.isEmpty()){
            userPhone.requestFocus();
            userPhone.setError("Enter User Phone Number");
        }else if (_userPassword.isEmpty()){
            userPass.requestFocus();
            userPass.setError("Enter User Password");
        }else {

            Call<StudentModel> studentModelCall =apiInterface.studentRegistration(_userName,_userEmail,_userPhone,_userPassword);
            studentModelCall.enqueue(new Callback<StudentModel>() {
                @Override
                public void onResponse(Call<StudentModel> call, Response<StudentModel> response) {
                    if (response.body() != null && response.isSuccessful()){
                        StudentModel model = response.body();

                        if (model.isSuccess()){
                            userName.setText("");
                            userEmail.setText("");
                            userPhone.setText("");
                            userPass.setText("");
                            Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));

                        }else {
                            Toast.makeText(RegisterActivity.this, "User Already exist", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(RegisterActivity.this, "Can't Registered", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<StudentModel> call, Throwable t) {
                    Toast.makeText(RegisterActivity.this, ""+t.toString(), Toast.LENGTH_SHORT).show();

                }
            });

        }
    }

    private void ininViews() {
        userName = findViewById(R.id.etRegisterUserName_id);
        userEmail = findViewById(R.id.etRegisterUserEmail_id);
        userPhone = findViewById(R.id.etRegisterUserPhone_id);
        userPass = findViewById(R.id.etRegisterUserPassId);
        textViewLogin = findViewById(R.id.tvLogin_id);
        buttonRegister = findViewById(R.id.buttonRegisterId);
    }
}
