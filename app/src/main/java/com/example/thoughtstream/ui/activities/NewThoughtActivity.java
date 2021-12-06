package com.example.thoughtstream.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.thoughtstream.R;
import com.example.thoughtstream.utils.ThoughtFunctions;

import java.io.IOException;

public class NewThoughtActivity extends AppCompatActivity {

    private String category;
    private String title;
    private ThoughtFunctions tf;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_thought);

        String filename = "";
        Boolean existing = false;
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
            tf = new ThoughtFunctions(category, getApplicationContext());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        TextView categoryTitle = findViewById(R.id.textViewSelectCategory);
        categoryTitle.setText(category);

        if(existing) {
            TextView textTitle = findViewById(R.id.editTextTitle);
            TextView textContent = findViewById(R.id.editTextMultiLineThought);
            int titleIndexBegin = filename.indexOf("{");
            Log.i("IndexBegin", String.valueOf(titleIndexBegin));
            int titleIndexEnd = filename.indexOf("_");
            Log.i("IndexEnd", String.valueOf(titleIndexEnd));
            title = filename.substring(titleIndexBegin, titleIndexEnd);
            textTitle.setText(title);
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
