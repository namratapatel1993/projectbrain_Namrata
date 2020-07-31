package com.lasalle.projectbrain.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lasalle.projectbrain.DashboardActivity;
import com.lasalle.projectbrain.R;
import com.lasalle.projectbrain.View.Fragment.CitePostFragment;
import com.lasalle.projectbrain.View.Fragment.OriginalPostFragment;
import com.lasalle.projectbrain.models.TodoModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

    private ArrayList<TodoModel.Datum> arrayUserTodos;
    private Context context;
    private String username;

    public TodoAdapter(Context context, ArrayList<TodoModel.Datum> arrayUserTodos, String username) {
        this.arrayUserTodos = arrayUserTodos;
        this.context = context;
        this.username = username;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.todo_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final TodoModel.Datum userIdeaModel = arrayUserTodos.get(position);

        holder.txtTitle.setText("" + userIdeaModel.getTitle());
        holder.txtContext.setText("" + userIdeaModel.getContext());
        holder.txtContent.setText("" + userIdeaModel.getContent());
        holder.txtPostedBy.setText("Contributed By: " + userIdeaModel.getCreator().getUsername());

        holder.txtToDo.setVisibility(View.GONE);

        if (!("" + userIdeaModel.getCiteId()).equals("null")) {
            holder.txtContext.setTextColor(((DashboardActivity) context).getColor(R.color.colorAccent));
        }

        holder.txtContext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!("" + userIdeaModel.getCiteId()).equals("null")) {
                    ((DashboardActivity) context).getSupportFragmentManager().beginTransaction().add(R.id.container,
                            OriginalPostFragment.newInstance(userIdeaModel.getCiteId()), OriginalPostFragment.class.getSimpleName()).commit();
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


        holder.txtFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }


    @Override
    public int getItemCount() {
        return arrayUserTodos.size();
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

}