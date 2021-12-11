package com.example.thoughtstream.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.thoughtstream.R;
import com.example.thoughtstream.ui.activities.NewThoughtActivity;
import com.example.thoughtstream.ui.activities.ThoughtCategoriesActivity;
import com.example.thoughtstream.utils.CategoryPresenter;

import java.io.IOException;
import java.util.Objects;

public class NewCategoryFragment extends DialogFragment {

    private Context mContext;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_new_category, container, false);
        Objects.requireNonNull(getDialog()).setTitle("Create A Category");
        Bundle args = getArguments();
        boolean loadedFromDirectory = args.getBoolean("LoadedFromDirectory");
        Button saveButton = rootView.findViewById(R.id.buttonCreate);
        saveButton.setOnClickListener(view -> {
            CategoryPresenter directory = new CategoryPresenter(mContext);
            EditText nameContents = rootView.findViewById(R.id.editTextCategoryTitle);
            String newCategoryName = nameContents.getText().toString();
            try {
                if(directory.saveNew(newCategoryName)){
                    Toast.makeText(mContext, "Category Saved!", Toast.LENGTH_SHORT).show();
                    if(loadedFromDirectory){
                        ((ThoughtCategoriesActivity) getActivity()).loadDirectory();
                    }
                    else
                    {
                        ((NewThoughtActivity) getActivity()).loadCategories();
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        return rootView;
    }
}

