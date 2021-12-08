package com.example.thoughtstream.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.thoughtstream.R;
import com.example.thoughtstream.utils.ThoughtPresenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThoughtsActivity extends AppCompatActivity {

    ThoughtPresenter tf;
    ListView listView;
    String categorySelect;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thoughts);

        if(getIntent().getExtras() != null) {
            categorySelect = getIntent().getStringExtra("Category");
        }

        try {
            loadDirectory();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        ImageView plusImg = findViewById(R.id.imageViewAddButton);
        plusImg.setOnClickListener(view -> {
            Intent intent = new Intent(ThoughtsActivity.this, NewThoughtActivity.class);
            intent.putExtra("Category", categorySelect);
            intent.putExtra("Existing", false);
            startActivity(intent);
        });
    }

    private void loadDirectory() throws ClassNotFoundException {
        listView = findViewById(R.id.thoughts_listview);

        tf = new ThoughtPresenter(categorySelect, getApplicationContext());

        List<String> ListElementsArrayList = new ArrayList<>(Arrays.asList(tf.loadDirectory()));
        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (ThoughtsActivity.this, android.R.layout.simple_list_item_1, ListElementsArrayList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItem = (String) parent.getItemAtPosition(position);
            Log.i("SelectedItem", selectedItem);
            Intent intent = new Intent(ThoughtsActivity.this, NewThoughtActivity.class);
            intent.putExtra("Category", categorySelect);
            intent.putExtra("Filename", selectedItem);
            intent.putExtra("Existing", true);
            startActivity(intent);
        });
    }
}
