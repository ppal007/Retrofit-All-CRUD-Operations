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

public class MainActivity extends AppCompatActivity {

    private EditText userEmail,userPassword;
    private TextView textViewRegister;
    private Button buttonLogin;

    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

//        text view register click listener...............................................
        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });

//        login button click listener..........................................................
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentLoginFunction();
            }
        });
    }

    private void studentLoginFunction() {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        String _email = userEmail.getText().toString();
        String _pass  = userPassword.getText().toString().trim();

        if (_email.isEmpty()){
            userEmail.requestFocus();
            userEmail.setError("Enter User Email-Address");
        }else if (!Patterns.EMAIL_ADDRESS.matcher(_email).matches()){
            userEmail.requestFocus();
            userEmail.setError("Enter Valid Email-Address");
        }else if (_pass.isEmpty()){
            userPassword.requestFocus();
            userPassword.setError("Enter User Password");
        }else {

            Call<StudentModel> studentModelCall = apiInterface.studentLogin(_email,_pass);
            studentModelCall.enqueue(new Callback<StudentModel>() {
                @Override
                public void onResponse(Call<StudentModel> call, Response<StudentModel> response) {
                    if (response.body() != null){
                        StudentModel model = response.body();

                        if (model.isSuccess()){
                            Toast.makeText(MainActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
                            startActivity(intent);

                            userEmail.setText("");
                            userPassword.setText("");
                        }else {
                            Toast.makeText(MainActivity.this, "User Not Found!", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(MainActivity.this, "Can't Login!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<StudentModel> call, Throwable t) {
                    Toast.makeText(MainActivity.this, ""+t.toString(), Toast.LENGTH_SHORT).show();

                }
            });

        }

    }

    private void initViews() {
        userEmail = findViewById(R.id.etLoginUserEmail_id);
        userPassword = findViewById(R.id.etLoginUserPassId);
        textViewRegister = findViewById(R.id.tvRegister_id);
        buttonLogin = findViewById(R.id.buttonLoginId);
    }
}
