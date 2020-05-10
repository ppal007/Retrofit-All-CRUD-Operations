package com.ppal007.retrofitcrudoperation.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.ppal007.retrofitcrudoperation.Model.StudentModel;
import com.ppal007.retrofitcrudoperation.R;

import com.ppal007.retrofitcrudoperation.Retrofit.ApiClient;
import com.ppal007.retrofitcrudoperation.Retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomDialog {

    private EditText name,email,phone;
    private Button buttonSubmit,buttonCancel;

    private String bndl_val_id = "";
    private String bndl_val_name = "";
    private String bndl_val_mail = "";
    private String bndl_val_phone = "";

    private ApiInterface apiInterface;

    public void showDialog(Context context, Bundle arg){

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_edit_dialog);

        name = dialog.findViewById(R.id.etCustomEditDialogName_id);
        email = dialog.findViewById(R.id.etCustomEditDialogEmail_id);
        phone = dialog.findViewById(R.id.etCustomEditDialogPhone_id);
        buttonSubmit = dialog.findViewById(R.id.btnCustomEditDialogId);
        buttonCancel = dialog.findViewById(R.id.btnCustomEditDialogCancelId);

//        get bundle value from recycler adapter............................................
        if (arg!=null){
            bndl_val_id = arg.getString("ex_id");
            bndl_val_name = arg.getString("ex_name");
            bndl_val_mail = arg.getString("ex_mail");
            bndl_val_phone = arg.getString("ex_ph");
        }

//        set value in field...........................
        name.setText(bndl_val_name);
        email.setText(bndl_val_mail);
        phone.setText(bndl_val_phone);

//        cancel button click listener...........................................
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

//        update button click listener...............................................
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateInfoFunction(dialog);
            }
        });

        dialog.show();

    }

    private void updateInfoFunction(final Dialog dialog) {
        String _name = name.getText().toString();
        String _email = email.getText().toString();
        String _phone = phone.getText().toString().trim();

        if (_name.isEmpty()){
            name.requestFocus();
            name.setError("Fields can't empty!");
        }else if (_email.isEmpty()){
            email.requestFocus();
            email.setError("Fields can't empty!");
        }else if (!Patterns.EMAIL_ADDRESS.matcher(_email).matches()){
            email.requestFocus();
            email.setError("Email not valid!");
        }else if (_phone.isEmpty()){
            phone.requestFocus();
            phone.setError("Fields can't empty!");
        }else {
//            Toast.makeText(dialog.getContext(), "Update", Toast.LENGTH_SHORT).show();
            apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

            Call<StudentModel> studentModelCall = apiInterface.updateUser(bndl_val_id,_name,_email,_phone);
            studentModelCall.enqueue(new Callback<StudentModel>() {
                @Override
                public void onResponse(Call<StudentModel> call, Response<StudentModel> response) {
                    if (response.body() != null && response.isSuccessful()){
                        StudentModel model = response.body();
                        if (model.isSuccess()){
                            Toast.makeText(dialog.getContext(), ""+model.getMessage(), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();

                        }else {
                            Toast.makeText(dialog.getContext(), "not update", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(dialog.getContext(), "Can't update", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<StudentModel> call, Throwable t) {
                    Toast.makeText(dialog.getContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        }
    }



}
