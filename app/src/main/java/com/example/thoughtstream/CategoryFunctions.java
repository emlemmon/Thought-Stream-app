package com.example.thoughtstream;

import com.google.gson.Gson;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.Map;
import java.util.Vector;

public class CategoryFunctions{

    private static Gson gson;
    private Vector<String> names;
    private Map<String, LinkedList<String>> categoryList;
    private static WeakReference<MainActivity> app;

    CategoryFunctions(WeakReference<MainActivity> app){
        gson = new Gson();
        names = new Vector<>();
        CategoryFunctions.app = app;
        getCategoryList();
    }

    private void getCategoryList(){
        String categoryConvert = "";

    }

    public Vector<String> load(int number, String category, boolean isBackward){

        return names;
    }

    public boolean save(String name){

        return true;
    }

    public boolean update(String category, String newName){

        return true;
    }

    public boolean delete(String category) {

        return true;
    }
}
