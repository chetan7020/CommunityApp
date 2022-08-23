package com.example.communityapp.post;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.communityapp.R;
import com.google.android.material.textfield.TextInputLayout;

public class WritePostFragment extends Fragment {

    private View view ;
    private AutoCompleteTextView actvDepartment ;
    private ImageView ivImagePreview ;
    private Button btnImagePreview , btnCreatePost ;
    private TextInputLayout etHeader , etDepartment , etDescription ;

    private void initialized() {
        actvDepartment = view.findViewById(R.id.dept_spinner) ;
        ivImagePreview = view.findViewById(R.id.ivImagePreview) ;
        btnImagePreview = view.findViewById(R.id.btnImagePreview) ;
        btnCreatePost = view.findViewById(R.id.btnCreatePost) ;

        etHeader = view.findViewById(R.id.etHeader) ;
        etDepartment = view.findViewById(R.id.etDepartment) ;
        etDescription = view.findViewById(R.id.etDescription) ;

    }

    public WritePostFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_write_post, container, false) ;

        initialized() ;


        spinnerSettings() ;

        btnCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPost() ;
            }
        });

        btnImagePreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage() ;
            }
        });

        return view ;

    }


    private void spinnerSettings() {
        String deptNames[] = {"Choose Department" , "Electronics" , "Computer" , "Information Tech." , "Electrical" , "Civil" , "Mechanical" , "Automobile"} ;
        ArrayAdapter stringArrayAdapter = new ArrayAdapter<String>(getActivity() , android.R.layout.simple_spinner_dropdown_item , deptNames) ;
        actvDepartment.setAdapter(stringArrayAdapter);
    }

    private void createPost() {
        String header , department , description = "";
        header = etHeader.getEditText().getText().toString() ;
        department = etDepartment.getEditText().getText().toString() ;
        description = etDescription.getEditText().getText().toString() ;
        if (header.equals("") ||
                department.equals("Choose Department") ||
                description.equals("") ) {

            if (header.equals("")) {
                etHeader.setError("*Required");
            }else {
                etHeader.setErrorEnabled(false);
                etHeader.setError(null);
            }

            if (department.equals("Choose Department")) {
                etDepartment.setError("*Required");
            }else {
                etDepartment.setErrorEnabled(false);
                etDepartment.setError(null);
            }

            if (description.equals("")) {
                etDescription.setError("*Required");
            }else {
                etDescription.setErrorEnabled(false);
                etDescription.setError(null);
            }
        }
    }

    private void makeToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    private void chooseImage() {
        Intent intent = new Intent() ;
        intent.setAction(Intent.ACTION_PICK) ;
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI , "image/*") ;
        startActivityForResult(intent , 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 100) {
                Uri uri = data.getData() ;
                if (null != uri) {
                    Log.d("tag" , "hello") ;
                    ivImagePreview.setImageURI(uri);
                }
            }
        }
    }
}