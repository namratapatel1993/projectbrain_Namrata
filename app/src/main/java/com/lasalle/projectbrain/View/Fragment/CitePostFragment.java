package com.lasalle.projectbrain.View.Fragment;

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
import android.widget.Toast;

import com.lasalle.projectbrain.R;
import com.lasalle.projectbrain.StoreManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;


public class CitePostFragment extends Fragment implements View.OnClickListener {

    private EditText edtTitle;
    private EditText edtContext;
    private EditText edtContent;

    private Button btnAddNewIdea;

    private String citeId;
    private String title;

    public static CitePostFragment newInstance(String citeId, String title) {
        CitePostFragment fragment = new CitePostFragment();
        fragment.citeId = citeId;
        fragment.title = title;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_idea,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialization(view);
    }

    public void initialization(View view){
        edtTitle = view.findViewById(R.id.edtTitle);
        edtContext = view.findViewById(R.id.edtContext);
        edtContent = view.findViewById(R.id.edtContent);
        btnAddNewIdea = view.findViewById(R.id.btnAddNewIdea);
        btnAddNewIdea.setOnClickListener(this);

        edtContext.setText("" + title);
        edtContext.setEnabled(false);
        edtContext.setTextColor(getActivity().getResources().getColor(R.color.colorAccent));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddNewIdea:
                addPost();
                break;
        }
    }

    public void addPost(){
        AsyncHttpClient client = new AsyncHttpClient();
        JSONObject jsonParams = new JSONObject();
        try {
            String title = edtTitle.getText().toString();
            String content = edtContent.getText().toString();
            String context = edtContext.getText().toString();

            jsonParams.put("title", title);
            jsonParams.put("citeId", citeId);
            jsonParams.put("context", context);
            jsonParams.put("content", content);
            jsonParams.put("username","" + new StoreManager(getActivity()).getUsername());

            StringEntity entity = new StringEntity(jsonParams.toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

            client.post(getActivity(), "http://192.168.2.100:8080/add/newpost", entity, "application/json", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        JSONObject json = new JSONObject(new String(responseBody));
                        Log.i("Register","responseBody: " + json.toString());

                        edtTitle.setText("");
                        edtContent.setText("");
                        edtContext.setText("");

                        Toast.makeText(getActivity(), "Submited", Toast.LENGTH_SHORT).show();
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
