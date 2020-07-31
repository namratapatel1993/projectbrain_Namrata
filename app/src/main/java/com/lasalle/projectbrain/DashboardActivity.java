package com.lasalle.projectbrain;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.lasalle.projectbrain.View.Fragment.CitePostFragment;
import com.lasalle.projectbrain.View.Fragment.ComposePostFragment;
import com.lasalle.projectbrain.View.Fragment.DashboardFragment;
import com.lasalle.projectbrain.View.Fragment.EditProfileFragment;
import com.lasalle.projectbrain.View.Fragment.IdeaListFragment;
import com.lasalle.projectbrain.View.Fragment.OriginalPostFragment;
import com.lasalle.projectbrain.View.Fragment.ProfileFragment;
import com.lasalle.projectbrain.View.Fragment.SearchFragment;
import com.lasalle.projectbrain.View.Fragment.TodoIdeaFragment;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnListIdeas;
    private Button btnComposeIdea;
    private Button btnSearchIdea;
    private Button btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        initialization();
    }

    public void initialization(){
        btnListIdeas = findViewById(R.id.btnListIdeas);
        btnComposeIdea = findViewById(R.id.btnComposeIdea);
        btnSearchIdea = findViewById(R.id.btnSearchIdea);
        btnSettings = findViewById(R.id.btnSettings);
        btnListIdeas.setOnClickListener(this);
        btnComposeIdea.setOnClickListener(this);
        btnSearchIdea.setOnClickListener(this);
        btnSettings.setOnClickListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.container,
                DashboardFragment.newInstance(), DashboardFragment.class.getSimpleName()).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnListIdeas:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        DashboardFragment.newInstance(), DashboardFragment.class.getSimpleName()).commit();
                break;

            case R.id.btnComposeIdea:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        ComposePostFragment.newInstance(), ComposePostFragment.class.getSimpleName()).commit();
                break;

                case R.id.btnSearchIdea:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        SearchFragment.newInstance(), SearchFragment.class.getSimpleName()).commit();
                break;

            case R.id.btnSettings:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        ProfileFragment.newInstance(), ProfileFragment.class.getSimpleName()).commit();
                break;

        }
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);

        if (fragment instanceof EditProfileFragment ||
        fragment instanceof TodoIdeaFragment ||
        fragment instanceof IdeaListFragment ||
        fragment instanceof OriginalPostFragment ||
                fragment instanceof CitePostFragment) {
            getSupportFragmentManager().beginTransaction().remove(fragment);
        } else {
            finish();
        }
    }
}
