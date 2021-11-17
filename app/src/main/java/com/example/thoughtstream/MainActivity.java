package com.example.thoughtstream;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Button timerBtn = findViewById(R.id.button_thought_timer);
        timerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TimerActivity.class);
                startActivity(intent);
            }
        });

        Button newThoughtBtn = findViewById(R.id.button_new_thought);
        newThoughtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewThoughtActivity.class);
                startActivity(intent);
            }
        });

        Button thoughtCategoriesBtn = findViewById(R.id.button_thought_categories);
        thoughtCategoriesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ThoughtCategoriesActivity.class);
                startActivity(intent);
            }
        });
    }

    /* Heyyo  I'm nathan */
}