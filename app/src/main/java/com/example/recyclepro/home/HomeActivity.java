package com.example.recyclepro.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.recyclepro.R;
import com.example.recyclepro.callback.UserCallBack;
import com.example.recyclepro.utils.Database;
import com.example.recyclepro.utils.User;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity {


    private FrameLayout fHome_FL_home;
    private FrameLayout fHome_FL_recycle;
    private FrameLayout fHome_FL_add;
    private FrameLayout fHome_FL_profile;
    private BottomNavigationView home_BN;
    private HomeFragment homeFragment;
    private RecycleFragment recycleFragment;
    private ProfileFragment profileFragment;
    private AddFragment addFragment;

    private Database database;
    private User currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        findViews();
        initVars();
        connectFragments();
    }

    private void connectFragments() {
        homeFragment = new HomeFragment(this);
        getSupportFragmentManager().beginTransaction().add(R.id.fHome_FL_home, homeFragment).commit();

        addFragment = new AddFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fHome_FL_add, addFragment).commit();

        profileFragment = new ProfileFragment(this);
        getSupportFragmentManager().beginTransaction().add(R.id.fHome_FL_profile, profileFragment).commit();

        recycleFragment = new RecycleFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fHome_FL_recycle, recycleFragment).commit();

    }

    private void initVars() {

        database = new Database();
        database.setUserCallBack(new UserCallBack() {
            @Override
            public void onUserFetchDataComplete(User user) {
                profileFragment.setCurrentUser(user);
            }

            @Override
            public void onUpdateComplete(Task<Void> task) {

            }
        });

        database.fetchUserData(database.getCurrentUser().getUid());

        fHome_FL_home.setVisibility(View.VISIBLE);
        fHome_FL_recycle.setVisibility(View.INVISIBLE);
        fHome_FL_add.setVisibility(View.INVISIBLE);
        fHome_FL_profile.setVisibility(View.INVISIBLE);
        home_BN.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.home){
                    fHome_FL_home.setVisibility(View.VISIBLE);
                    fHome_FL_recycle.setVisibility(View.INVISIBLE);
                    fHome_FL_add.setVisibility(View.INVISIBLE);
                    fHome_FL_profile.setVisibility(View.INVISIBLE);
                }else if(item.getItemId() == R.id.recycle){
                    fHome_FL_home.setVisibility(View.INVISIBLE);
                    fHome_FL_recycle.setVisibility(View.VISIBLE);
                    fHome_FL_add.setVisibility(View.INVISIBLE);
                    fHome_FL_profile.setVisibility(View.INVISIBLE);
                }else if(item.getItemId() == R.id.add){
                    fHome_FL_home.setVisibility(View.INVISIBLE);
                    fHome_FL_recycle.setVisibility(View.INVISIBLE);
                    fHome_FL_add.setVisibility(View.VISIBLE);
                    fHome_FL_profile.setVisibility(View.INVISIBLE);
                }else {
                    // profile
                    fHome_FL_home.setVisibility(View.INVISIBLE);
                    fHome_FL_recycle.setVisibility(View.INVISIBLE);
                    fHome_FL_add.setVisibility(View.INVISIBLE);
                    fHome_FL_profile.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });
    }

    private void findViews() {
        fHome_FL_home = findViewById(R.id.fHome_FL_home);
        fHome_FL_recycle = findViewById(R.id.fHome_FL_recycle);
        fHome_FL_add = findViewById(R.id.fHome_FL_add);
        fHome_FL_profile = findViewById(R.id.fHome_FL_profile);
        home_BN = findViewById(R.id.home_BN);

    }
}