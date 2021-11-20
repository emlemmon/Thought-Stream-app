package com.example.thoughtstream;

import com.google.gson.Gson;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.Vector;

public class ThoughtFunctions{

    private static Gson gson;
    private String defaultCategory;
    private Vector<String> names;
    private LinkedList<String> thoughtList;
    private static WeakReference<MainActivity> app;

    ThoughtFunctions(String category, WeakReference<MainActivity> app){
        gson = new Gson();
        defaultCategory = category;
        names = new Vector<>();
        ThoughtFunctions.app = app;
        getThoughtList();
    }

    private void getThoughtList(){
        String categoryConvert = "";

    }

    public Vector<String> load(int number, String filename, boolean isBackward){

        return names;
    }

    public boolean save(String name){

        return true;
    }

    public boolean update(String filename, String newName){

        return true;
    }

    public boolean delete(String filename) {

        return true;
    }
}

