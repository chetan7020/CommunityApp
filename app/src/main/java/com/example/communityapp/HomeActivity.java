package com.example.communityapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.communityapp.department.* ;
import com.example.communityapp.my_account.MyAccountFragment;
import com.example.communityapp.logs.SignInActivity;
import com.example.communityapp.post.* ;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private View headerView;
    private TextView tvUserName;
    private FirebaseUser firebaseUser;
    private FirebaseAuth auth;

    private void initialize() {
        drawerLayout = findViewById(R.id.drawer_layout) ;
        navigationView = findViewById(R.id.navigationView) ;
        toolbar = findViewById(R.id.tool_bar) ;
        headerView = navigationView.getHeaderView(0) ;
        tvUserName = headerView.findViewById(R.id.tvUserName) ;
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        auth = FirebaseAuth.getInstance() ;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initialize();

        tvUserName.setText(firebaseUser.getEmail());

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.openDrawer,
                R.string.closeDrawer
        );

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        loadFrag(new DefaultFragment());
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.deptCO) {
                loadFrag(new ComputerFragment());
            } else if (id == R.id.deptEJ) {
                loadFrag(new ElectronicsFragment());
            } else if (id == R.id.deptIF) {
                loadFrag(new InformationFragment());
            } else if (id == R.id.deptEE) {
                loadFrag(new ElectricalFragment());
            } else if (id == R.id.deptME) {
                loadFrag(new MechanicalFragment());
            } else if (id == R.id.deptCE) {
                loadFrag(new CivilFragment());
            } else if (id == R.id.deptAE) {
                loadFrag(new AutomobileFragment());
            } else if (id == R.id.defaultHome) {
                loadFrag(new DefaultFragment());
            } else if (id == R.id.writePost) {
                loadFrag(new WritePostFragment());
            } else if (id == R.id.yourPost) {
                loadFrag(new YourPostFragment());
            } else if (id == R.id.user) {
                loadFrag(new MyAccountFragment());
            } else if (id == R.id.logout) {
                auth.signOut();
                startActivity(new Intent(HomeActivity.this, SignInActivity.class));
                finish();
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    private void makeToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void loadFrag(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


}