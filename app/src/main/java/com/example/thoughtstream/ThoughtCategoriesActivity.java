package com.example.thoughtstream;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class ThoughtCategoriesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thought_categories);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ImageView plusImg = findViewById(R.id.imageViewAddButton);
        plusImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.fragment_container_view_tag, new NewCategoryFragment(), "NewCategoryFragment").addToBackStack(null).commit();
            }
        });
    }
}
