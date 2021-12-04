package com.example.thoughtstream.utils;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.TreeMap;

public class ThoughtFunctions extends AppCompatActivity {

    private final String defaultCategory;
    private final WeakReference<Context> appContext;
    private final TreeMap<String, LinkedList<String>> direct;
    CategoryFunctions cf;

    public ThoughtFunctions(String category, Context context) throws ClassNotFoundException {
        defaultCategory = category;
        appContext = new WeakReference<>(context);
        cf = new CategoryFunctions(context);
        direct = cf.getMap();
    }

    private LinkedList<String> getThoughtList(){
        Log.i("Category", defaultCategory);
        return direct.get(defaultCategory);
    }

    public String[] loadDirectory(){
        Object[] temp = getThoughtList().toArray();
        String[] strings = Arrays.copyOf(temp, temp.length, String[].class);
        for (String string : strings) {
            Log.i("Directory Contents", string);
        }
        return strings;
    }

    public String loadFile(String filename) throws IOException {
        String result = "";
        Log.i("Accessing File", filename);
        InputStream inputStream = openFileInput(filename);
        if(inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String temp;
            StringBuilder stringBuilder = new StringBuilder();

            while((temp = bufferedReader.readLine()) != null){
                stringBuilder.append(temp);
            }

            inputStream.close();
            result = stringBuilder.toString();
        }
        return result;
    }

    public boolean save(String name, String content) throws IOException, ClassNotFoundException {
        String filename = (appContext.get().getFilesDir().getPath()) + "/{" + defaultCategory + "_" + name + "" + "}.txt";
        FileOutputStream file = new FileOutputStream(filename);

        if(getThoughtList().contains(filename)){
            return false;
        }

        try{
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(file);
            outputStreamWriter.write(content);
            outputStreamWriter.close();
        } catch(IOException e)
        {
            e.printStackTrace();
            return false;
        }

        LinkedList<String> thoughts = getThoughtList();
        thoughts.add(filename);
        cf.saveList(defaultCategory, thoughts);

        return true;
    }

    public boolean update(String filename, String newName) throws IOException, ClassNotFoundException {

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

