package com.example.communityapp.my_account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.communityapp.R;

public class VerifyEmailFragment extends Fragment {

    private View view ;

    public VerifyEmailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_verify_email, container, false);

        return view ;
    }
}