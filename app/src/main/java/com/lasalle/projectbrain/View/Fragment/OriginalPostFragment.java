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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lasalle.projectbrain.R;
import com.lasalle.projectbrain.StoreManager;
import com.lasalle.projectbrain.models.PostModel;
import com.lasalle.projectbrain.models.SinglePostModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;


public class OriginalPostFragment extends Fragment {

    public TextView txtTitle;
    public TextView txtContext;
    public TextView txtContent;
    public TextView txtPostedBy;
    public TextView txtCite;
    public TextView txtToDo;
    public TextView txtFollow;

    private String citeId;

    public static OriginalPostFragment newInstance(String citeId) {
        OriginalPostFragment fragment = new OriginalPostFragment();
        fragment.citeId = citeId;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_original_post,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialization(view);
    }

    public void initialization(View view){
        this.txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        this.txtContext = (TextView) view.findViewById(R.id.txtContext);
        this.txtContent = (TextView) view.findViewById(R.id.txtContent);
        this.txtPostedBy = (TextView) view.findViewById(R.id.txtPostedBy);
        this.txtCite = (TextView) view.findViewById(R.id.txtCite);
        this.txtToDo = (TextView) view.findViewById(R.id.txtToDo);
        this.txtFollow = (TextView) view.findViewById(R.id.txtFollow);

        getPost();
    }

    public void getPost(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://192.168.2.100:8080/post/?id="+ citeId, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                // called when response HTTP status is "200 OK"

                //Gson gson = new GsonBuilder().create();
                //PostModel postModel = gson.fromJson(new String(responseBody), PostModel.class);

                //Log.i("Register","responseBody: " + postModel.getTitle());
                try {
                    JSONObject json = new JSONObject(new String(responseBody));
                    Log.i("Get ideas","responseBody: " + json.toString());
                    Gson gson = new GsonBuilder().create();
                    SinglePostModel userIdeaModel = gson.fromJson(new String(responseBody), SinglePostModel.class);

                    txtTitle.setText("" + userIdeaModel.getTitle());
                    txtContext.setText("" + userIdeaModel.getContext());
                    txtContent.setText("" + userIdeaModel.getContent());

                    String creator = "";
                    if (userIdeaModel.getCreator() != null && userIdeaModel.getCreator().getUsername() != null) {
                        creator = "" + userIdeaModel.getCreator().getUsername();
                    }

                    txtPostedBy.setText("Contributed By: " + creator);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });
    }
}
