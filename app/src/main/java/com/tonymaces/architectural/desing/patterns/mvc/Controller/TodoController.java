package com.tonymaces.architectural.desing.patterns.mvc.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.tonymaces.architectural.desing.patterns.mvc.Model.TodoModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tonym on 08/07/2016.
 The Controller provides data from the Model for the View
 */
public class TodoController {

    private TodoModel db_model;
    private List<String> tasks;

    public TodoController(Context app_context) {
        tasks = new ArrayList<>();
        db_model = new TodoModel(app_context);
    }

    public  void  addTask(final  String title){
        final ContentValues data = new ContentValues();
        data.put("title", title);
        db_model.addEntry(data);
    }

    //Overrides to handle View specifics and keep Model  straightforward
    public  void deleteTask(final String title){
        db_model.deleteEntry("title=" + title + "");
    }

    public  void deleteTask(final long id){
        db_model.deleteEntry("id=" + id + "");
    }

    public  void deleteAll(){
        db_model.deleteEntry(null);
    }

    public  List<String> getTasks(){
        Cursor c = db_model.findAll();
        tasks.clear();

        if (c != null){
            c.moveToFirst();
            while (c.isAfterLast() == false){
                tasks.add(c.getString(0));
                c.moveToNext();
            }
            c.close();
        }
        return  tasks;
    }
}
