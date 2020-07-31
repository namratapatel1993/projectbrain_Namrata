package com.lasalle.projectbrain.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lasalle.projectbrain.DashboardActivity;
import com.lasalle.projectbrain.R;
import com.lasalle.projectbrain.StoreManager;
import com.lasalle.projectbrain.View.Fragment.CitePostFragment;
import com.lasalle.projectbrain.View.Fragment.IdeaListFragment;
import com.lasalle.projectbrain.View.Fragment.OriginalPostFragment;
import com.lasalle.projectbrain.models.PostModel;
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

public class IdeasListAdapter extends RecyclerView.Adapter<IdeasListAdapter.ViewHolder> {

    private ArrayList<PostModel.Datum> arrayUserIdeas;
    private Context context;
    private String username;

    public IdeasListAdapter(Context context, ArrayList<PostModel.Datum> arrayUserIdeas, String username) {
        this.arrayUserIdeas = arrayUserIdeas;
        this.context = context;
        this.username = username;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.idea_list_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final PostModel.Datum userIdeaModel = arrayUserIdeas.get(position);

        holder.txtTitle.setText("" + userIdeaModel.getTitle());
        holder.txtContext.setText("" + userIdeaModel.getContext());
        holder.txtContent.setText("" + userIdeaModel.getContent());

        String creator = "";
        if (userIdeaModel.getCreator() != null && userIdeaModel.getCreator().getUsername() != null) {
            creator = "" + userIdeaModel.getCreator().getUsername();
        }

        holder.txtPostedBy.setText("Contributed By: " + creator);

        if (creator.equals("" + username)) {
            holder.txtCite.setVisibility(View.GONE);
            holder.txtToDo.setVisibility(View.GONE);
            holder.txtFollow.setVisibility(View.GONE);
        } else {
            holder.txtCite.setVisibility(View.VISIBLE);
            holder.txtToDo.setVisibility(View.VISIBLE);
            holder.txtFollow.setVisibility(View.VISIBLE);
        }

        if (!("" + userIdeaModel.getCiteId()).equals("null")) {
            holder.txtContext.setTextColor(((DashboardActivity) context).getResources().getColor(R.color.colorAccent));
        }

        holder.txtContext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!("" + userIdeaModel.getCiteId()).equals("null")) {
                    ((DashboardActivity) context).getSupportFragmentManager().beginTransaction().add(R.id.container,
                            OriginalPostFragment.newInstance(""+userIdeaModel.getCiteId()), OriginalPostFragment.class.getSimpleName()).commit();
                }
            }
        });

        holder.txtCite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DashboardActivity) context).getSupportFragmentManager().beginTransaction().add(R.id.container,
                        CitePostFragment.newInstance(""+userIdeaModel.getId(), userIdeaModel.getTitle()), CitePostFragment.class.getSimpleName()).commit();
            }
        });

        holder.txtToDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTodo("" + userIdeaModel.getId());
            }
        });


        final String finalCreator = creator;
        holder.txtFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFollow(finalCreator);
            }
        });
    }


    @Override
    public int getItemCount() {
        return arrayUserIdeas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTitle;
        public TextView txtContext;
        public TextView txtContent;
        public TextView txtPostedBy;
        public TextView txtCite;
        public TextView txtToDo;
        public TextView txtFollow;

        public ViewHolder(View itemView) {
            super(itemView);
            this.txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            this.txtContext = (TextView) itemView.findViewById(R.id.txtContext);
            this.txtContent = (TextView) itemView.findViewById(R.id.txtContent);
            this.txtPostedBy = (TextView) itemView.findViewById(R.id.txtPostedBy);
            this.txtCite = (TextView) itemView.findViewById(R.id.txtCite);
            this.txtToDo = (TextView) itemView.findViewById(R.id.txtToDo);
            this.txtFollow = (TextView) itemView.findViewById(R.id.txtFollow);
        }
    }

    public void addFollow(final String usernameToBeFollowed){
        AsyncHttpClient client = new AsyncHttpClient();
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("usernameToBeFollowed", usernameToBeFollowed);
            jsonParams.put("username","" + username);

            StringEntity entity = new StringEntity(jsonParams.toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

            client.post(context, "http://192.168.2.100:8080/contributor/follow", entity, "application/json", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        JSONObject json = new JSONObject(new String(responseBody));
                        Log.i("Register","responseBody: " + json.toString());

                        Toast.makeText(context, "you have followed " + usernameToBeFollowed, Toast.LENGTH_SHORT).show();
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

    public void addTodo(String id){

        AsyncHttpClient client = new AsyncHttpClient();
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("ideaId", id);
            jsonParams.put("username","" + username);

            StringEntity entity = new StringEntity(jsonParams.toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

            client.post(context, "http://192.168.2.100:8080/add/todo", entity, "application/json", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        JSONObject json = new JSONObject(new String(responseBody));
                        Log.i("Register","responseBody: " + json.toString());

                        Toast.makeText(context, "idea added in todo list.", Toast.LENGTH_SHORT).show();
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