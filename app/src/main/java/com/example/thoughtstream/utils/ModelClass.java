package com.example.thoughtstream.utils;

import java.util.LinkedList;
import java.util.Map;

public class ModelClass {

    Map<String, LinkedList<String>> categoriesMap;

    // Constructor
    public ModelClass(Map<String, LinkedList<String>> categoriesMap){
        this.categoriesMap = categoriesMap;
    }



    // Getters and Setters
    public Map<String, LinkedList<String>> getCategoriesMap() {
        return categoriesMap;
    }

    public void setCategoriesMap(Map<String, LinkedList<String>> categoriesMap) {
        this.categoriesMap = categoriesMap;
    }

}
