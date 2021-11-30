package com.example.thoughtstream.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.LinkedList;
import java.util.Set;

public class CategoryFunctions extends AppCompatActivity {

    private static Gson gson;


    CategoryFunctions(){
        gson = new Gson();
    }

    private Directory getMap(){
        SharedPreferences pref = getSharedPreferences("categories", Context.MODE_PRIVATE);
        String encrypted = pref.toString();

        return gson.fromJson(encrypted, Directory.class);
    }
    private void saveMap(Directory cat){
        SharedPreferences pref = getSharedPreferences("categories", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        String encrypted = gson.toJson(cat);
        edit.putString("categories", encrypted);
        edit.apply();
    }

    public Set<String> load(){

        return getMap().categories.keySet();
    }

    public boolean save(String name){
        Directory cat = getMap();

        if(cat.categories.containsKey(name)) {
            return false;
        }
        else {
            cat.categories.put(name, new LinkedList<>());
            saveMap(cat);
            return true;
        }

    }

    public boolean update(String oldName, String newName){
        Directory cat = getMap();

        if(cat.categories.containsKey(oldName)){
            cat.categories.put(newName, cat.categories.get(oldName));
            cat.categories.remove(oldName);
            saveMap(cat);
            return true;
        }

        return false;
    }

    public boolean delete(String name) {
        Directory cat = getMap();

        if(cat.categories.containsKey(name)){
            cat.categories.remove(name);
            saveMap(cat);
            return true;
        }

        return false;
    }
}
