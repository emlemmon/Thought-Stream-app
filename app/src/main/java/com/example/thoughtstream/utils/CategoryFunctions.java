package com.example.thoughtstream.utils;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.TreeMap;

public class CategoryFunctions extends AppCompatActivity {

    private WeakReference<Context> appContext;

    public CategoryFunctions(Context context){
        appContext = new WeakReference<>(context);
    }

    protected TreeMap<String, LinkedList<String>> getMap() throws ClassNotFoundException {
        String filename = appContext.get().getFilesDir().getPath() + "/directory.tmp";
        Log.i("File Location", filename);
        TreeMap<String, LinkedList<String>> direct = null;
        FileInputStream fis;
        try {
            fis = new FileInputStream(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            direct = new TreeMap<>();
            try {
                saveMap(direct);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            return direct;
        }
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            assert ois != null;
            direct = (TreeMap<String, LinkedList<String>>) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return direct;

    }
    private void saveMap(TreeMap<String, LinkedList<String>> direct) throws IOException {
        String filename = appContext.get().getFilesDir().getPath() + "/directory.tmp";
        FileOutputStream fos = new FileOutputStream(filename);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(direct);
        oos.close();
    }

    public String[] load() throws ClassNotFoundException {
        Object[] temp = getMap().keySet().toArray();
        return Arrays.copyOf(temp, temp.length, String[].class);

    }

    public boolean save(String name) throws IOException, ClassNotFoundException {
        TreeMap<String, LinkedList<String>> direct = getMap();
        direct.put(name, new LinkedList<>());
        saveMap(direct);
        return true;
    }

    public boolean update(String oldName, String newName) throws IOException, ClassNotFoundException {
        TreeMap<String, LinkedList<String>> direct = getMap();

        if(direct.containsKey(oldName)){
            direct.put(newName, direct.get(oldName));
            direct.remove(oldName);
            saveMap(direct);
            return true;
        }

        return false;
    }

    public boolean delete(String name) throws IOException, ClassNotFoundException {
        TreeMap<String, LinkedList<String>> direct = getMap();

        if(direct.containsKey(name)){
            direct.remove(name);
            saveMap(direct);
            return true;
        }

        return false;
    }
}
