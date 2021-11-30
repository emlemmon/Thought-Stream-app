package com.example.thoughtstream.utils;

import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

public class Directory {

    Map<String, LinkedList<String>> categories;

    // Constructors
    public Directory(){

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
}
