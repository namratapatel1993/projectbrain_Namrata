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
import com.lasalle.projectbrain.View.Fragment.CitePostFragment;
import com.lasalle.projectbrain.View.Fragment.OriginalPostFragment;
import com.lasalle.projectbrain.models.PostModel;
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

public class IdeasAdapter extends RecyclerView.Adapter<IdeasAdapter.ViewHolder> {

    private ArrayList<PostModel.Datum> arrayUserIdeas;
    private Context context;

    public IdeasAdapter(Context context, ArrayList<PostModel.Datum> arrayUserIdeas, String s) {
        this.arrayUserIdeas = arrayUserIdeas;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.idea_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final PostModel.Datum userIdeaModel = arrayUserIdeas.get(position);

        holder.txtTitle.setText("" + userIdeaModel.getTitle());
        holder.txtContext.setText("" + userIdeaModel.getContext());
        holder.txtContent.setText("" + userIdeaModel.getContent());
        holder.txtPostedBy.setText("Posted By: " + userIdeaModel.getCreator().getUsername());

        if (!("" + userIdeaModel.getCiteId()).equals("null")) {
            holder.txtContext.setTextColor(((DashboardActivity) context).getColor(R.color.colorAccent));
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

        holder.txtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removePost("" + userIdeaModel.getId(), position);
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
        public TextView txtDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            this.txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            this.txtContext = (TextView) itemView.findViewById(R.id.txtContext);
            this.txtContent = (TextView) itemView.findViewById(R.id.txtContent);
            this.txtPostedBy = (TextView) itemView.findViewById(R.id.txtPostedBy);
            this.txtCite = (TextView) itemView.findViewById(R.id.txtCite);
            this.txtToDo = (TextView) itemView.findViewById(R.id.txtToDo);
            this.txtFollow = (TextView) itemView.findViewById(R.id.txtFollow);
            this.txtDelete = (TextView) itemView.findViewById(R.id.txtDelete);
        }
    }

    public void removePost(String id, final int position){
        AsyncHttpClient client = new AsyncHttpClient();
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("id","" + id);

            StringEntity entity = new StringEntity(jsonParams.toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

            client.delete(context, "http://192.168.2.100:8080/post/remove?id=" + id, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Toast.makeText(context, "Removed", Toast.LENGTH_SHORT).show();

                    arrayUserIdeas.remove(position);
                    notifyDataSetChanged();
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