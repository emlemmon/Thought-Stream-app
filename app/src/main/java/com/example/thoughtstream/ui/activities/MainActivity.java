package com.example.thoughtstream.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.thoughtstream.R;
import com.example.thoughtstream.ui.AppPresenter;
import com.example.thoughtstream.ui.fragments.AppView;

import java.util.LinkedList;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AppView {

    // Linking Activity with Presenter
    AppPresenter appPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiate the Presenter
        appPresenter = new AppPresenter(this);
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

    @Override
    public void onGetCategoriesMap(Map<String, LinkedList<String>> categories) {

    }
}