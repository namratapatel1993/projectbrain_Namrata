package com.lasalle.projectbrain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lasalle.projectbrain.models.LoginModel;
import com.lasalle.projectbrain.models.RegistrationModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogIn;
    private Button btnRegisterView;
    private EditText edtEmail;
    private EditText edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialization();
    }

    public void initialization(){
        btnLogIn = findViewById(R.id.btnLogIn);
        btnRegisterView = findViewById(R.id.btnRegisterView);

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);

        btnLogIn.setOnClickListener(this);
        btnRegisterView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogIn:
                loginInApp();
                break;

            case R.id.btnRegisterView:
                startActivity(new Intent(this, RegisterActivity.class));
                finish();
                break;
        }
    }

    public void loginInApp(){
        AsyncHttpClient client = new AsyncHttpClient();
        JSONObject jsonParams = new JSONObject();
        try {
            String email = edtEmail.getText().toString();
            String password = edtPassword.getText().toString();
            jsonParams.put("email", email);
            jsonParams.put("password", password);

            StringEntity entity = new StringEntity(jsonParams.toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

            client.post(LoginActivity.this, "http://192.168.0.100:8080/contributor/login", entity, "application/json", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        JSONObject json = new JSONObject(new String(responseBody));
                        Log.i("Login","responseBody: " + json.toString());

                        Gson gson = new GsonBuilder().create();
                        LoginModel loginModel = gson.fromJson(new String(responseBody), LoginModel.class);
                        new StoreManager(LoginActivity.this).saveLoginData(loginModel.getUsername(),
                                loginModel.getFirstname(), loginModel.getFirstname(), loginModel.getEmail(),
                                loginModel.getCity());

                        startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                }
            });

        } catch (JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
