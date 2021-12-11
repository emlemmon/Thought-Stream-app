package com.example.thoughtstream.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.thoughtstream.R;
import com.example.thoughtstream.ui.fragments.NewCategoryFragment;
import com.example.thoughtstream.utils.CategoryPresenter;
import com.example.thoughtstream.utils.ThoughtPresenter;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class NewThoughtActivity extends AppCompatActivity {

    private String category;
    private ThoughtPresenter tf;
    private CategoryPresenter cf;
    private String title;

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
            title = filename;
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
            loadCategories();
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
        Button categoryButton = findViewById(R.id.buttonNewCategory);
        categoryButton.setOnClickListener(view -> {
            FragmentManager fragmentManager = getSupportFragmentManager();
            NewCategoryFragment dialogFragment = new NewCategoryFragment();
            Bundle args = new Bundle();
            args.putBoolean("LoadedFromDirectory", false);
            dialogFragment.setArguments(args);
            dialogFragment.show(fragmentManager, "Sample Fragment");
        });

        AtomicBoolean success = new AtomicBoolean(false);
        Button btnCreate = findViewById(R.id.buttonCreate);
        btnCreate.setOnClickListener(view -> {
            try {
                success.set(saveThought());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            if(success.get()) {
                Intent intent = new Intent(NewThoughtActivity.this, ThoughtsActivity.class);
                intent.putExtra("Category", category);
                startActivity(intent);
            }
        });

    }

    public void loadCategories() throws ClassNotFoundException {
        String[] categoriesArray = cf.load();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.dropdown_item, categoriesArray);
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        autoCompleteTextView.setAdapter(arrayAdapter);
    }

    private boolean saveThought() throws IOException, ClassNotFoundException {
        EditText titleText = findViewById(R.id.editTextTitle);
        String titleName = titleText.getText().toString();
        EditText thoughtText = findViewById(R.id.editTextMultiLineThought);
        String thoughtContents = thoughtText.getText().toString();
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        String newCategory = autoCompleteTextView.getText().toString();
        Log.i("Selected Category", newCategory);
        if(newCategory.equals("Choose a Category")){
            Toast.makeText(getApplicationContext(), "No Category Selected!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!newCategory.equals(category))
        {
            ThoughtPresenter ntf = new ThoughtPresenter(newCategory, getApplicationContext());
            if(ntf.save(titleName, thoughtContents)){
                tf.delete(title);
                Toast.makeText(getApplicationContext(), "Thought Saved!", Toast.LENGTH_SHORT).show();
                return true;
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Cannot Save Duplicate Title!", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if(tf.save(titleName, thoughtContents)){
            if(!titleName.equals(title))
            {
                tf.delete(title);
            }
            Toast.makeText(getApplicationContext(), "Thought Saved!", Toast.LENGTH_SHORT).show();
            return true;
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Cannot Save Duplicate Title", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
