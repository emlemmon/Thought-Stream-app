package com.example.thoughtstream.ui.activities;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.thoughtstream.R;
import com.example.thoughtstream.ui.fragments.NewCategoryFragment;
import com.example.thoughtstream.utils.CategoryFunctions;

public class ThoughtCategoriesActivity extends AppCompatActivity {

    CategoryFunctions cf;
    String[] directory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thought_categories);

        cf = new CategoryFunctions(getApplicationContext());
        Object[] temp = new Object[0];
        try {
            directory = cf.load();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < directory.length; i++)
        {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ImageView plusImg = findViewById(R.id.imageViewAddButton);
        plusImg.setOnClickListener(view -> {
            FragmentManager fragmentManager = getSupportFragmentManager();
            NewCategoryFragment dialogFragment = new NewCategoryFragment();
            dialogFragment.show(fragmentManager, "Sample Fragment");
        });
    }

    private void loadCategories() {

    }
}
