package com.example.thoughtstream.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.thoughtstream.ui.fragments.NewCategoryFragment;
import com.example.thoughtstream.R;

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
                FragmentManager fragmentManager = getSupportFragmentManager();
                NewCategoryFragment dialogFragment = new NewCategoryFragment();
                dialogFragment.show(fragmentManager, "Sample Fragment");
            }
        });
    }

    private void loadCategories() {

    }
}
