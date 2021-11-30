package com.example.thoughtstream.ui;

import com.example.thoughtstream.ui.fragments.AppView;
import com.example.thoughtstream.utils.ModelClass;

// This will act as the Presenter
public class AppPresenter {

    // Link between Presenter and the View through an Interface.
    AppView appView;

    public AppPresenter(AppView appView){
        this.appView = appView;
    }

    // Link between Presenter and the Model.
    public ModelClass GetAppFromModel(){

        // Here we are declaring an instance of our ModelClass.
        // Still need to add more parameters.

        return new ModelClass(/*Add a map here.*/)
    }

    // Link between Presenter and MainActivity
    public void getAppDetails(){
        appView.onGetCategoriesMap( GetAppFromModel().getCategoriesMap() );
    }
}
