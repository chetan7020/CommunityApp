package com.example.communityapp.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.communityapp.R;
import com.example.communityapp.adapter.AdminTabAdapter;
import com.google.android.material.tabs.TabLayout;

public class AdminActivity extends AppCompatActivity {

    private TabLayout tabLayout ;
    private ViewPager viewPager ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        initialize() ;

        tabLayout.setupWithViewPager(viewPager) ;
        AdminTabAdapter adminTabAdapter = new AdminTabAdapter(getSupportFragmentManager() , FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) ;
        adminTabAdapter.addFragment(new AuthorizedFragment() , "Authorized") ;
        adminTabAdapter.addFragment(new ReportBugFragment() , "Report Bug") ;
        adminTabAdapter.addFragment(new SuggestionsFragment() , "Suggestions") ;
        viewPager.setAdapter(adminTabAdapter);

    }

    private void initialize() {
        tabLayout = findViewById(R.id.tab_layout) ;
        viewPager = findViewById(R.id.view_pager) ;
    }
}