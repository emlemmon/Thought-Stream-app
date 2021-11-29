package com.example.thoughtstream.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.thoughtstream.R;

public class ThoughtsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thoughts);

        ImageView plusImg = findViewById(R.id.imageViewAddButton);
        plusImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThoughtsActivity.this, NewThoughtActivity.class);
                startActivity(intent);
            }
        });
    }
}
