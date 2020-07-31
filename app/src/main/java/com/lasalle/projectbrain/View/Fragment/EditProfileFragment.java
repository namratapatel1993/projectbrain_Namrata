package com.lasalle.projectbrain.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lasalle.projectbrain.DashboardActivity;
import com.lasalle.projectbrain.LoginActivity;
import com.lasalle.projectbrain.R;
import com.lasalle.projectbrain.RegisterActivity;
import com.lasalle.projectbrain.StoreManager;
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

public class EditProfileFragment extends Fragment implements View.OnClickListener{

    private Button btnRegister;
    private EditText edtUsername;
    private EditText edtEmail;
    private EditText edtFirstname;
    private EditText edtLastname;
    private EditText edtCity;

    public static EditProfileFragment newInstance() {
        EditProfileFragment fragment = new EditProfileFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_edit_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialization(view);
    }

    public void initialization(View view){
        edtUsername = view.findViewById(R.id.edtUsername);
        edtEmail = view.findViewById(R.id.edtEmail);
        edtFirstname = view.findViewById(R.id.edtFirstName);
        edtLastname = view.findViewById(R.id.edtLastName);
        edtCity = view.findViewById(R.id.edtCity);
        btnRegister = view.findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);

        StoreManager storeManager = new StoreManager(getActivity());

        edtUsername.setText("" + storeManager.getUsername());
        edtEmail.setText("" + storeManager.get("email"));
        edtFirstname.setText("" + storeManager.get("firstname"));
        edtLastname.setText("" + storeManager.get("lastname"));
        edtCity.setText("" + storeManager.get("city"));
    }

    @Override
    public void onClick(View v) {
        AsyncHttpClient client = new AsyncHttpClient();
        JSONObject jsonParams = new JSONObject();
        try {
            String username = edtUsername.getText().toString();
            String firstname = edtFirstname.getText().toString();
            String lastname = edtLastname.getText().toString();
            String city = edtCity.getText().toString();
            jsonParams.put("username", username);
            jsonParams.put("firstname", firstname);
            jsonParams.put("lastname", lastname);
            jsonParams.put("city", city);


            StringEntity entity = new StringEntity(jsonParams.toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

            client.post(getActivity(), "http://192.168.2.100:8080/contributor/update", entity, "application/json", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        JSONObject json = new JSONObject(new String(responseBody));
                        Log.i("Update Profile","responseBody: " + json.toString());

                        Gson gson = new GsonBuilder().create();
                        RegistrationModel registrationModel = gson.fromJson(new String(responseBody), RegistrationModel.class);

                        Log.i("Update Profile","responseBody: " + registrationModel.getUsername());

                        new StoreManager(getActivity()).saveLoginData(registrationModel.getUsername(),
                                registrationModel.getFirstname(), registrationModel.getLastname(), registrationModel.getEmail(),
                                registrationModel.getCity());

                        Toast.makeText(getActivity(), "Updated", Toast.LENGTH_SHORT).show();

                        getActivity().onBackPressed();

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

