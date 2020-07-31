package com.lasalle.projectbrain.View.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lasalle.projectbrain.R;
import com.lasalle.projectbrain.StoreManager;
import com.lasalle.projectbrain.adapters.IdeasListAdapter;
import com.lasalle.projectbrain.models.PostModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SearchFragment extends Fragment implements TextWatcher, View.OnClickListener {

    private Button btnSearch;

    private EditText edtSearch;

    private RecyclerView recyclerView;
    private IdeasListAdapter ideasAdapter;

    private ArrayList<PostModel.Datum> arrayUserIdeas = new ArrayList<>();

    private boolean isFirstTime = true;

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);

        btnSearch = view.findViewById(R.id.btnSearch);

        edtSearch = view.findViewById(R.id.edtSearch);

        btnSearch.setOnClickListener(this);
        edtSearch.addTextChangedListener(this);

        initList();

        recyclerView.setVisibility(View.GONE);
    }

    private void initList() {
        ideasAdapter = new IdeasListAdapter(getActivity(), arrayUserIdeas , "" + new StoreManager(getActivity()).getUsername());

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(ideasAdapter);
    }

    public void initializationByTitle(String title){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://192.168.2.100:8080/post/"+ title + "/posts", new AsyncHttpResponseHandler() {

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
                    PostModel postModel = gson.fromJson(new String(responseBody), PostModel.class);

                    arrayUserIdeas = new ArrayList<>();

                    arrayUserIdeas.addAll(postModel.getData());

                    if (arrayUserIdeas.size() <= 0) {
                        recyclerView.setVisibility(View.GONE);
                    } else {
                        recyclerView.setVisibility(View.VISIBLE);
                    }

                    initList();
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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!isFirstTime && s.toString().trim().length() == 0) {
            recyclerView.setVisibility(View.GONE);
        }

        isFirstTime = false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSearch:
                if (!edtSearch.getText().toString().trim().equals("")) {
                    initializationByTitle("" + edtSearch.getText().toString().trim());
                }
                break;
        }
    }
}
