package com.darsco.tjamich;

import android.content.Context;
import android.os.Bundle;
import android.os.UserManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.app.ProgressDialog;

import com.darsco.tjamich.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class forgot_jel extends AppCompatActivity {

    private static String TAG_RETROFIT_GET_POST = "RETROFIT_GET_POST";
    private EditText txtEmail = null;
    private Button recoveryButton = null;
    private ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_jel);

        initControls();

        recoveryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog();
                String emailValue = txtEmail.getText().toString();
                // Create Gson converter to convert returned JSON string.
                GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create();
                // Get call object.
                Call<UserDTO> call = com.darsco.tjamich.UserManager.getUserManagerService(gsonConverterFactory).getUserByName(emailValue);
                // Create a Callback object.
                retrofit2.Callback<UserDTO> callback = new Callback<UserDTO>() {
                    @Override
                    public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                        hideProgressDialog();
                        try {
                            if (response.isSuccessful()) {
                                UserDTO userDto = response.body();
                                Toast.makeText(getApplicationContext(), "Se ha enviado un correo a su bandeja con sus Credenciales", Toast.LENGTH_LONG).show();
                                List<UserDTO> userDtoList = new ArrayList<UserDTO>();
                                userDtoList.add(userDto);
                                //showUserInfoInListView(userDtoList);
                            } else {
                                String errorMessage = response.errorBody().string();
                                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
                            }
                        }catch(IOException ex)
                        {
                            Log.e(TAG_RETROFIT_GET_POST, ex.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<UserDTO> call, Throwable t) {
                        hideProgressDialog();
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                };
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(recoveryButton.getWindowToken(), 0);
                txtEmail.setText("");
                // Send request to web server and let callback to process the response.
                call.enqueue(callback);
            }
        });

    }

    /* Initialize all UI controls. */
    private void initControls()
    {
        if(txtEmail==null)
        {
            txtEmail = (EditText)findViewById(R.id.txtemail);
        }

        if(recoveryButton == null)
        {
            recoveryButton = (Button)findViewById(R.id.btnRecovery);
        }

        if(progressDialog == null) {
            progressDialog = new ProgressDialog(forgot_jel.this);
        }
    }


    /* Show progress dialog. */
    private void showProgressDialog()
    {
        // Set progress dialog display message.
        progressDialog.setMessage("Por favor espere");

        // The progress dialog can not be cancelled.
        progressDialog.setCancelable(false);

        // Show it.
        progressDialog.show();
    }

    /* Hide progress dialog. */
    private void hideProgressDialog()
    {
        // Close it.
        progressDialog.hide();
    }
}