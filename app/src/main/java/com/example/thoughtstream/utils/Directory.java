package com.example.thoughtstream.utils;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

public class Directory implements Serializable {

    Map<String, LinkedList<String>> categories;

    // Constructors
    public Directory(){
        //LinkedList notHere = new LinkedList<String>();
        //categories.put("default", notHere);
    }

    public Directory(TreeMap<String, LinkedList<String>> categories){
        this.categories = categories;
    }

    // Getters and Setters
    public Map<String, LinkedList<String>> getCategories() {
        return categories;
    }

    public void setCategories(Map<String, LinkedList<String>> categories) {
        this.categories = categories;
    }

    private void writeObject(java.io.ObjectOutputStream out){

    }

    private void readObject(java.io.ObjectInputStream in){

    }
    private void readObjectNoData(){

    }
}
