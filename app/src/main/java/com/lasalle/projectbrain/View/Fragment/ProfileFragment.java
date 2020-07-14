package com.lasalle.projectbrain.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lasalle.projectbrain.LoginActivity;
import com.lasalle.projectbrain.R;
import com.lasalle.projectbrain.StoreManager;

public class ProfileFragment extends Fragment implements View.OnClickListener{

    private Button btnUserIdeas;
    private Button btnUserToDo;
    private Button btnEditProfile;
    private Button btnLogout;

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialization(view);
    }

    public void initialization(View view){
        btnUserIdeas = view.findViewById(R.id.btnUserIdeas);
        btnUserToDo = view.findViewById(R.id.btnUserToDo);
        btnEditProfile = view.findViewById(R.id.btnEditProfile);
        btnLogout = view.findViewById(R.id.btnLogout);
        btnUserIdeas.setOnClickListener(this);
        btnUserToDo.setOnClickListener(this);
        btnEditProfile.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnUserIdeas:
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container,
                        IdeaListFragment.newInstance(), IdeaListFragment.class.getSimpleName()).commit();
                break;

            case R.id.btnUserToDo:
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container,
                        TodoIdeaFragment.newInstance(), TodoIdeaFragment.class.getSimpleName()).commit();
                break;

            case R.id.btnEditProfile:
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container,
                        EditProfileFragment.newInstance(), EditProfileFragment.class.getSimpleName()).commit();
                break;

            case R.id.btnLogout:
                new StoreManager(getActivity()).clear();

                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
        }
    }
}
