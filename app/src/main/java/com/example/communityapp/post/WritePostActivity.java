package com.example.communityapp.post;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.communityapp.R;

public class WritePostActivity extends AppCompatActivity {

    private AutoCompleteTextView autoCompleteTextView;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_post);
        initialized();
        autoCompleteTextView.setAdapter(adapter);
    }


    private void initialized() {
        autoCompleteTextView = findViewById(R.id.dept_spinner);
        String[] items = new String[]{"Computer", "Electronics", "Electrical", "Mechanical", "Civil", "Automobile", "Information Tech."};
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
    }
}