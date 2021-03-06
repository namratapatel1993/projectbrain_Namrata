package com.lasalle.projectbrain.View.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lasalle.projectbrain.R;
import com.lasalle.projectbrain.StoreManager;
import com.lasalle.projectbrain.adapters.IdeasAdapter;
import com.lasalle.projectbrain.adapters.TodoAdapter;
import com.lasalle.projectbrain.models.PostModel;
import com.lasalle.projectbrain.models.RegistrationModel;
import com.lasalle.projectbrain.models.TodoModel;
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

public class IdeaListFragment extends Fragment {

    public static IdeaListFragment newInstance() {
        IdeaListFragment fragment = new IdeaListFragment();
        return fragment;
    }

    private RecyclerView recyclerView;

    private IdeasAdapter todoAdapter;

    private ArrayList<PostModel.Datum> arrayUserTodos = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);

        initList();

        initialization();
    }

    private void initList() {
        todoAdapter = new IdeasAdapter(getActivity(), arrayUserTodos , "" + new StoreManager(getActivity()).getUsername());

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(todoAdapter);
    }

    public void initialization(){
        Log.i("TAG", "initialization: ");
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://192.168.2.100:8080/contributor/"+ new StoreManager(getActivity()).getUsername() + "/posts", new AsyncHttpResponseHandler() {

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

                    arrayUserTodos.addAll(postModel.getData());
                    todoAdapter.notifyDataSetChanged();

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
}
