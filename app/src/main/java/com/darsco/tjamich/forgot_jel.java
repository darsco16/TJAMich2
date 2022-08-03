package com.darsco.tjamich;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ProgressDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class forgot_jel extends AppCompatActivity {

    private static String TAG_RETROFIT_GET_POST = "RETROFIT_GET_POST";
    private EditText txtEmail = null;
    private Button recoveryButton = null;
    private ProgressDialog progressDialog = null;
    TextView registro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_jel);

        initControls();
        registro = (EditText) findViewById(R.id.txtemail);

        registro.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View arg0, boolean gotfocus) {
                // TODO Auto-generated method stub
                if(gotfocus){
                    registro.setCompoundDrawables(null, null, null, null);
                }
                else if(!gotfocus){
                    if(registro.getText().length()==0)
                        registro.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_user, 0, 0, 0);
                }


            }
        });

        recoveryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!txtEmail.getText().toString().isEmpty() && isEmailValid(txtEmail.getText().toString())) {
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
                            UserDTO userDto = response.body();
                            try {
                                    if (response.isSuccessful()) {
                                            if (userDto.getData() != null) {
                                                Toast.makeText(getApplicationContext(), "Se ha enviado un correo a su bandeja con sus Credenciales", Toast.LENGTH_LONG).show();
                                                    /*List<UserDTO> userDtoList = new ArrayList<UserDTO>();
                                            userDtoList.add(userDto);
                                            showUserInfoInListView(userDtoList);*/
                                            } else {
                                                Toast.makeText(getApplicationContext(), "No se encontro algun registro relacionado al E-mail proporcionado.", Toast.LENGTH_LONG).show();
                                            }
                                    } else {
                                        String errorMessage = response.errorBody().string();
                                        Toast.makeText(getApplicationContext(), userDto.getToken(), Toast.LENGTH_LONG).show();
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
                } else {
                    Toast.makeText(getApplicationContext(), "Se requiere un Email válido para continuar.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
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