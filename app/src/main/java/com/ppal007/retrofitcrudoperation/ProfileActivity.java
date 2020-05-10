package com.ppal007.retrofitcrudoperation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.ppal007.retrofitcrudoperation.Adapter.StudentAdapter;
import com.ppal007.retrofitcrudoperation.Model.RetriveAllModel;
import com.ppal007.retrofitcrudoperation.Retrofit.ApiClient;
import com.ppal007.retrofitcrudoperation.Retrofit.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ApiInterface apiInterface;
    private List<RetriveAllModel> studentList;
    private StudentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
//        initialize recycler view................................................................
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerViewId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<RetriveAllModel>> listCall = apiInterface.getAllData();
        listCall.enqueue(new Callback<List<RetriveAllModel>>() {
            @Override
            public void onResponse(Call<List<RetriveAllModel>> call, Response<List<RetriveAllModel>> response) {
                if (response.body() != null){
                    studentList = response.body();
                    adapter = new StudentAdapter(studentList,ProfileActivity.this);
                    recyclerView.setAdapter(adapter);
                }else {
                    Toast.makeText(ProfileActivity.this, "Something Wrong!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<RetriveAllModel>> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, ""+t.toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
