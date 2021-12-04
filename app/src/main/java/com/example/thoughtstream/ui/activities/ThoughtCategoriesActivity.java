package com.example.thoughtstream.ui.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.thoughtstream.R;
import com.example.thoughtstream.ui.fragments.NewCategoryFragment;
import com.example.thoughtstream.utils.CategoryFunctions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThoughtCategoriesActivity extends AppCompatActivity {

    CategoryFunctions cf;
    String[] directory;
    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thought_categories);

        listView = findViewById(R.id.category_listview);

        cf = new CategoryFunctions(getApplicationContext());
        try {
            directory = cf.load();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        List<String> ListElementsArrayList = new ArrayList<>(Arrays.asList(directory));
        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (ThoughtCategoriesActivity.this, android.R.layout.simple_list_item_1, ListElementsArrayList);
        listView.setAdapter(adapter);

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
