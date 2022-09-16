package com.example.communityapp.post;

import android.app.Activity;
import android.content.Intent;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.communityapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class WritePostFragment extends Fragment {

    private View view;
    private AutoCompleteTextView actvDepartment;
    private ImageView ivImagePreview;
    private Button btnImagePreview, btnCreatePost;
    private TextInputLayout etHeader, etDepartment, etDescription;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser firebaseUser;

    private void initialize() {
        actvDepartment = view.findViewById(R.id.dept_spinner);
        ivImagePreview = view.findViewById(R.id.ivImagePreview);
        btnImagePreview = view.findViewById(R.id.btnImagePreview);
        btnCreatePost = view.findViewById(R.id.btnCreatePost);

        etHeader = view.findViewById(R.id.etHeader);
        etDepartment = view.findViewById(R.id.etDepartment);
        etDescription = view.findViewById(R.id.etDescription);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    }

    public WritePostFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_write_post, container, false);

        initialize();

        spinnerSettings();

        btnCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPost();
            }
        });

        btnImagePreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

        return view;

    }


    private void spinnerSettings() {
        String deptNames[] = {"Choose Department", "Electronics", "Computer", "Information Tech.", "Electrical", "Civil", "Mechanical", "Automobile"};
        ArrayAdapter stringArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, deptNames);
        actvDepartment.setAdapter(stringArrayAdapter);
    }

    private void createPost() {
        String header, department, description, user;
        header = etHeader.getEditText().getText().toString();
        department = etDepartment.getEditText().getText().toString();
        description = etDescription.getEditText().getText().toString();
        user = firebaseUser.getEmail();

        if (header.equals("") ||
                department.equals("Choose Department") ||
                description.equals("")) {

            if (header.equals("")) {
                etHeader.setError("*Required");
            } else {
                etHeader.setErrorEnabled(false);
                etHeader.setError(null);
            }

            if (department.equals("Choose Department")) {
                etDepartment.setError("*Required");
            } else {
                etDepartment.setErrorEnabled(false);
                etDepartment.setError(null);
            }

            if (description.equals("")) {
                etDescription.setError("*Required");
            } else {
                etDescription.setErrorEnabled(false);
                etDescription.setError(null);
            }

        } else {
            String id = String.valueOf(Integer.parseInt(String.valueOf(System.currentTimeMillis() / 1000)));

            Log.d("id" , id);

            Map<String, Object> data = new HashMap<>();

            data.put("id", id);
            data.put("email", user);
            data.put("header", header);
            data.put("department", department);
            data.put("description", description);

            firebaseFirestore.collection("post")
                    .add(data)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            makeToast("Post uploaded");
                            etHeader.getEditText().setText("");
                            etDescription.getEditText().setText("");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            makeToast("Failed to upload");
                        }
                    });

        }
    }

    private void makeToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        try {
            startActivityForResult(intent, 100);
        } catch (Exception e) {
            makeToast(e.toString());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 100) {

                Uri uri = data.getData();
                if (null != uri) {
                    ivImagePreview.setImageURI(uri);
                }

            }
        }
    }
}