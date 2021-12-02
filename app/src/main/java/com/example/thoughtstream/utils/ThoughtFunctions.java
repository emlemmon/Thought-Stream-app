package com.example.thoughtstream.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;

public class ThoughtFunctions extends AppCompatActivity {

    private static Gson gson;
    private String defaultCategory;

    ThoughtFunctions(String category){
        gson = new Gson();
        defaultCategory = category;
    }

    private LinkedList<String> getThoughtList(){
        SharedPreferences pref = getSharedPreferences("categories", Context.MODE_PRIVATE);
        String encrypted = pref.toString();
        Directory collection = gson.fromJson(encrypted, Directory.class);

        return collection.categories.get(defaultCategory);
    }

    public Object[] loadDirectory(){
        return getThoughtList().toArray();
    }

    public String loadFile(String filename) throws IOException {
        String result = "";
        InputStream inputStream = openFileInput(filename);
        if(inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String temp;
            StringBuilder stringBuilder = new StringBuilder();

            while((temp = bufferedReader.readLine()) != null){
                stringBuilder.append(temp);
                stringBuilder.append("\n");
            }

            inputStream.close();
            result = stringBuilder.toString();
        }
        return result;
    }

    public boolean save(String name, String content){
        String filename = defaultCategory + "_" + name + ".txt";

        if(getThoughtList().contains(filename)){
            return false;
        }

        boolean success = true;
        try{
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(filename, Context.MODE_PRIVATE));
            outputStreamWriter.write(content);
            outputStreamWriter.close();
        } catch(IOException e)
        {
            e.printStackTrace();
            success = false;
        }

        if(success && !getThoughtList().contains(filename))
        {
            LinkedList<String> thoughts = getThoughtList();
            thoughts.add(filename);
        }

        return success;
    }

    public boolean update(String filename, String newName) throws IOException {

        LinkedList<String> thoughts = getThoughtList();
        int fileIndex = thoughts.indexOf(filename);
        if(fileIndex > -1)
        {
            save(newName, loadFile(filename));
            delete(filename);
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean delete(String filename) {
        LinkedList<String> thoughts = getThoughtList();
        int fileIndex = thoughts.indexOf(filename);
        if(fileIndex > -1)
        {
            File dir = getFilesDir();
            File file = new File(dir, filename);
            thoughts.remove(fileIndex);
            return (file.delete());
        }
        return false;
    }
}

