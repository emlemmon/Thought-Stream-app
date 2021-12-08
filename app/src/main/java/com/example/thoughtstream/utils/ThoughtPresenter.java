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

public class ThoughtPresenter extends AppCompatActivity {

    private final String defaultCategory;
    private final WeakReference<Context> appContext;
    private final TreeMap<String, LinkedList<String>> direct;
    CategoryPresenter cf;

    /* Constructor initializes with weak reference to app context for file access.
       Must be loaded with name of category to be accessed.
       Could be rewritten with category name being a requirement in all functions instead. */
    public ThoughtPresenter(String category, Context context) throws ClassNotFoundException {
        defaultCategory = category;
        appContext = new WeakReference<>(context);
        cf = new CategoryPresenter(context);
        direct = cf.getMap();
    }

    /* Function: getThoughtList()
       Purpose: Returns the LinkedList containing the filenames of all notes(files) within a category(folder).
       Returns (LinkedList<String>) The list of filenames in a category. */
    private LinkedList<String> getThoughtList(){
        Log.i("Category", defaultCategory);
        return direct.get(defaultCategory);
    }

    /* Function: constructFileName(String title)
       Purpose: Returns a prepared filename based on the title of the note.
       Returns (String): The full filename.*/
    private String constructFileName(String title){
        return (appContext.get().getFilesDir().getPath()) + "/{" + defaultCategory + "_" + title + "}.txt";
    }

    /* Function: loadDirectory()
       Purpose: Returns a string array containing all filenames present in the category (folder)
       Returns (String[]): An array containing filenames. */
    public String[] loadDirectory(){
        Object[] temp = getThoughtList().toArray();
        String[] strings = Arrays.copyOf(temp, temp.length, String[].class);
        for (String string : strings) {
            int titleBegin = string.indexOf("");
            Log.i("Directory Contents", string);
        }
        return strings;
    }

    /* Function: loadFile(String name)
       Purpose: Returns the contents of the given title
       Parameter (name): The name of the file.
       Returns (String): The contents of the respective file. It will be empty if no file of that name is present. */
    public String loadFile(String name) throws IOException {
        String result = "";
        String filename = constructFileName(name);
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

    /* Function: save(String name, String content)
       Purpose: Saves 'content' as a new file with a name made in part from the title of the note.
       Parameter (name): The title of the note. Used for constructing the filename.
       Parameter (content): The entire contents of the file to be saved.
       Returns (boolean): Indicates if operation was successful. Will return false if write operation fails. */
    public boolean save(String name, String content) throws IOException, ClassNotFoundException {
        String filename = constructFileName(name);
        FileOutputStream file = new FileOutputStream(filename);

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
        thoughts.add(name);
        cf.saveList(defaultCategory, thoughts);

        return true;
    }

    /* Function: update(String oldName, String newName)
       Purpose: Renames file by saving a new file with newName and deleting the pre-existing one.
       Parameter (oldName): The title of the file to be renamed.
       Parameter (newName): The title of the file to be saved with 'oldName''s contents.
       Returns (boolean): Indicates if operation was successful. Will return false if 'oldName' is not present in the directory. */
    public boolean update(String oldName, String newName) throws IOException, ClassNotFoundException {
        LinkedList<String> thoughts = getThoughtList();
        int fileIndex = thoughts.indexOf(constructFileName(oldName));
        if(fileIndex > -1)
        {
            save(newName, loadFile(oldName));
            delete(oldName);
            return true;
        }
        else
        {
            return false;
        }
    }

    /* Function: delete(String name)
       Purpose: Deletes the file of the note with the respective title.
       Parameter (name): The title of the note to be deleted.
       Returns (boolean): Indicates if operation was successful. Will return false if 'name' is not present in directory,
                          or if the delete operation failed. */
    public boolean delete(String name) {
        String filename = constructFileName(name);
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

