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

public class CategoryPresenter extends AppCompatActivity {

    private WeakReference<Context> appContext;

    // Constructor initializes weak reference to context for file access.
    public CategoryPresenter(Context context){
        appContext = new WeakReference<>(context);
    }

    /* Function: getMap()
       Purpose: Retrieves the Map used for storing the basic filesystem of the app.
       Returns: TreeMap<String, LinkedList<String>> */
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

    /* Function: saveMap(TreeMap<String, LinkedList<String>> direct)
       Parameter (direct): The map that will be saved.
       Purpose: Saves a map in place of the pre-existing map used for the filesystem. */
    private void saveMap(TreeMap<String, LinkedList<String>> direct) throws IOException {
        String filename = appContext.get().getFilesDir().getPath() + "/directory.tmp";
        FileOutputStream fos = new FileOutputStream(filename);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(direct);
        oos.close();
    }

    /* Function: load()
       Purpose: Loads the filesystem and returns an array of all category names (keys) present.
       Returns (String[]): List of category names.*/
    public String[] load() throws ClassNotFoundException {
        Object[] temp = getMap().keySet().toArray();
        return Arrays.copyOf(temp, temp.length, String[].class);
    }

    /* Function: saveNew(String name)
       Purpose: Loads the filesystem and puts a new category(key) based on the received name.
       Parameter (name): The name of the category to be added.
       Returns (Boolean): Indicates successful operation. */
    public Boolean saveNew(String name) throws IOException, ClassNotFoundException {
        TreeMap<String, LinkedList<String>> direct = getMap();
        direct.put(name, new LinkedList<>());
        saveMap(direct);
        return true;
    }

    /* Function: saveList(String name, LinkedList<String> contents)
       Purpose: Loads the filesystem and saves the passed 'contents' in place of the pre-existing contents in the respective category(key)
       Parameter (name): The name of the category to be accessed.
       Parameter (contents): The new LinkedList to be saved with the category.
       Returns (Boolean): Indicates successful operation. */
    public boolean saveList(String name, LinkedList<String> contents) throws ClassNotFoundException, IOException {
        TreeMap<String, LinkedList<String>> direct = getMap();
        direct.put(name, contents);
        saveMap(direct);
        return true;
    }

    /* Function: update(String oldName, String newName)
       Purpose: Replaces an existing category(key) with a new one that is linked to a copy of the old's contents.
       Parameter (oldName): The category(key) to be replaced.
       Parameter (newName): The new category(key) to be saved in oldName's place.
       Returns (Boolean): Indicates successful operation. It will return false if oldName is not a valid key. */
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

    /* Function: delete(String name)
       Purpose: Delete the respective category(key) and its contents.
       Parameter (name): The category(key) to be deleted.
       Returns (Boolean): Indicates successful operation. It will return false if name is not a valid key.*/
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
