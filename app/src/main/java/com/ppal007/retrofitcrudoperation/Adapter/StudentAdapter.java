package com.ppal007.retrofitcrudoperation.Adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.view.MenuItem;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ppal007.retrofitcrudoperation.Dialog.CustomDialog;
import com.ppal007.retrofitcrudoperation.MainActivity;
import com.ppal007.retrofitcrudoperation.Model.RetriveAllModel;
import com.ppal007.retrofitcrudoperation.Model.StudentModel;
import com.ppal007.retrofitcrudoperation.R;
import com.ppal007.retrofitcrudoperation.Retrofit.ApiClient;
import com.ppal007.retrofitcrudoperation.Retrofit.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.MyViewHolder> {

    private List<RetriveAllModel> studentList;
    private Context context;


    public StudentAdapter(List<RetriveAllModel> studentList, Context context) {
        this.studentList = studentList;
        this.context = context;
    }

    @NonNull
    @Override
    public StudentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_sample_layout,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.MyViewHolder holder, int position) {

        RetriveAllModel retriveAllModel = studentList.get(position);

        holder.userName.setText(retriveAllModel.getUserName());
        holder.userEmail.setText(retriveAllModel.getUserEmail());
        holder.userPhone.setText(retriveAllModel.getUserPhone());
        holder.id.setText(retriveAllModel.getId());

    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            PopupMenu.OnMenuItemClickListener {

        private TextView userName,userEmail,userPhone,id;
        private ImageButton imageButtonPopUp;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            userName  = itemView.findViewById(R.id.tvRVNameId);
            userEmail = itemView.findViewById(R.id.tvRVEmailId);
            userPhone = itemView.findViewById(R.id.tvRVPhoneId);
            id = itemView.findViewById(R.id.tvRVID_id);
            imageButtonPopUp = itemView.findViewById(R.id.imageButtonRVId);

            imageButtonPopUp.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            showPopUp(v);
        }

        private void showPopUp(View v) {
            android.widget.PopupMenu popupMenu = new PopupMenu(v.getContext(),v);
            popupMenu.inflate(R.menu.popup_menu);
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {

            int id = item.getItemId();

            if (id == R.id.edit_menuId){
//                Toast.makeText(context, ""+studentList.get(getAdapterPosition()).getId(), Toast.LENGTH_SHORT).show();
                CustomDialog dialog = new CustomDialog();
                Bundle arg = new Bundle();
                arg.putString("ex_id", studentList.get(getAdapterPosition()).getId());
                arg.putString("ex_name",studentList.get(getAdapterPosition()).getUserName());
                arg.putString("ex_mail",studentList.get(getAdapterPosition()).getUserEmail());
                arg.putString("ex_ph",studentList.get(getAdapterPosition()).getUserPhone());
                dialog.showDialog(context,arg);

            }if (id == R.id.delete_menuId){
//                Toast.makeText(context, "delete", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Are You Sure?");
                builder.setMessage("This Action is permanently delete item!");
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        String _id = studentList.get(getAdapterPosition()).getId();

                        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
                        Call<StudentModel> studentModelCall = apiInterface.deleteUser(_id);
                        studentModelCall.enqueue(new Callback<StudentModel>() {
                            @Override
                            public void onResponse(Call<StudentModel> call, Response<StudentModel> response) {
                                if (response.body() != null && response.isSuccessful()){
                                    StudentModel studentModel = response.body();
                                    if (studentModel.isSuccess()){
                                        Toast.makeText(context, ""+studentModel.getMessage(), Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }else {
                                        Toast.makeText(context, ""+studentModel.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }else {
                                    Toast.makeText(context, "Can't delete!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<StudentModel> call, Throwable t) {
                                Toast.makeText(context, ""+t.toString(), Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }

            return true;
        }
    }
}
