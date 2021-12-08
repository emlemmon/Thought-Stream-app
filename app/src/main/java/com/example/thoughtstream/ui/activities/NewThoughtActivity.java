package com.example.thoughtstream.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.thoughtstream.R;
import com.example.thoughtstream.utils.CategoryPresenter;
import com.example.thoughtstream.utils.ThoughtPresenter;

import java.io.IOException;

public class NewThoughtActivity extends AppCompatActivity {

    private String category;
    private ThoughtPresenter tf;
    private CategoryPresenter cf;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_thought);

        String filename = "";
        boolean existing = false;
        if(getIntent().getExtras() != null) {
            category = getIntent().getStringExtra("Category");
            filename = getIntent().getStringExtra("Filename");
            existing = getIntent().getBooleanExtra("Existing", false);

        }
        else
        {
            category = "default";
        }

        try {
            tf = new ThoughtPresenter(category, getApplicationContext());
            cf = new CategoryPresenter(this.getApplicationContext());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            String[] categoriesArray = cf.load();
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.dropdown_item, categoriesArray);
            AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
            autoCompleteTextView.setAdapter(arrayAdapter);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if(existing) {
            TextView textTitle = findViewById(R.id.editTextTitle);
            TextView textContent = findViewById(R.id.editTextMultiLineThought);
            textTitle.setText(filename);
            String contents = "";
            try {
                contents = tf.loadFile(filename);
            } catch (IOException e) {
                e.printStackTrace();
            }
            textContent.setText(contents);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Button btnCreate = findViewById(R.id.buttonCreate);
        btnCreate.setOnClickListener(view -> {
            try {
                saveThought();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(NewThoughtActivity.this, ThoughtsActivity.class);
            intent.putExtra("Category", category);
            startActivity(intent);
        });
    }

    private void saveThought() throws IOException, ClassNotFoundException {
        EditText titleText = findViewById(R.id.editTextTitle);
        String titleName = titleText.getText().toString();
        EditText thoughtText = findViewById(R.id.editTextMultiLineThought);
        String thoughtContents = thoughtText.getText().toString();
        if(tf.save(titleName, thoughtContents)){
            Toast.makeText(getApplicationContext(), "Thought Saved!", Toast.LENGTH_SHORT).show();
        }
    }
}
