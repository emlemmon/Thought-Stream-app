package com.example.thoughtstream.utils;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.TreeMap;

public class ThoughtFunctions extends AppCompatActivity {

    private String defaultCategory;
    private WeakReference<Context> appContext;
    private TreeMap<String, LinkedList<String>> direct;

    ThoughtFunctions(String category, Context context) throws ClassNotFoundException {
        defaultCategory = category;
        appContext = new WeakReference<>(context);
        CategoryFunctions cf = new CategoryFunctions(context);
        direct = cf.getMap();
    }

    private LinkedList<String> getThoughtList(){

        return direct.get(defaultCategory);
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
        String filename = appContext.get().getFilesDir().getPath()+ defaultCategory + "_" + name + ".txt";

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

