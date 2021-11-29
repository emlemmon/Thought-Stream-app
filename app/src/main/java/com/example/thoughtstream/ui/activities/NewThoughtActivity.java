package com.example.thoughtstream.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.thoughtstream.R;

public class NewThoughtActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_thought);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Button btnCreate = findViewById(R.id.buttonCreate);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewThoughtActivity.this, ThoughtsActivity.class);
                startActivity(intent);
            }
        });
    }
}
