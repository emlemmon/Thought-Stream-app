package com.example.thoughtstream.ui;

import java.util.LinkedList;
import java.util.Map;

public interface AppView {

    // This is the link between the presenter and the view.
    void onGetCategoriesMap(Map<String, LinkedList<String>> categories);
}
