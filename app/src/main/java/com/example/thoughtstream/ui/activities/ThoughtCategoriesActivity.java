package com.example.thoughtstream.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.thoughtstream.R;
import com.example.thoughtstream.ui.fragments.NewCategoryFragment;
import com.example.thoughtstream.utils.CategoryPresenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThoughtCategoriesActivity extends AppCompatActivity {

    CategoryPresenter cf;
    String[] directory;
    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thought_categories);

        loadDirectory();

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
        loadDirectory();
    }

    private void loadDirectory(){
        listView = findViewById(R.id.category_listview);

        cf = new CategoryPresenter(getApplicationContext());
        try {
            directory = cf.load();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        List<String> ListElementsArrayList = new ArrayList<>(Arrays.asList(directory));
        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (ThoughtCategoriesActivity.this, android.R.layout.simple_list_item_1, ListElementsArrayList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItem = (String) parent.getItemAtPosition(position);
            Intent intent = new Intent(ThoughtCategoriesActivity.this, ThoughtsActivity.class);
            intent.putExtra("Category", selectedItem);
                startActivity(intent);
        });
    }
}
