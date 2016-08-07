package com.tonymaces.architectural.desing.patterns.mvc.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.tonymaces.architectural.desing.patterns.mvc.Controller.TodoController;
import com.tonymaces.architectural.desing.patterns.mvc.R;

import java.util.List;

public class TodoActivity extends AppCompatActivity {

    public static String APP_TAG = TodoActivity.class.getName();

    private ListView taskView;
    private Button btNewTask;
    private EditText etNewTask;

    private TodoController provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        this.provider = new TodoController(this);
        this.taskView = (ListView) this.findViewById(R.id.tasklist);
        this.btNewTask= (Button) this.findViewById(R.id.btNewTask);
        this.etNewTask = (EditText) this.findViewById(R.id.etNewTask);
        this.btNewTask.setOnClickListener(this.handleNewTaskEvent);
    }

    private  void renderTodos(){
        final List<String> beans = this.provider.getTasks();

        Log.d(TodoActivity.APP_TAG, String.format("%d beans found", beans.size()));

        this.taskView.setAdapter(new ArrayAdapter<String>(this,
                                android.R.layout.simple_list_item_1,
                               beans.toArray(new String[]{})));

        this.taskView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TodoActivity.APP_TAG, String.format("item with id: %d and position: %d",id,position));
                final TextView v = (TextView) view;
                TodoActivity.this.provider.deleteTask(v.getText().toString());
                TodoActivity.this.renderTodos();
            }
        });
    }


    private  final View.OnClickListener handleNewTaskEvent = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            Log.d(APP_TAG, "add task click received");

            TodoActivity.this.provider.addTask(TodoActivity.this.etNewTask.getText().toString());
            TodoActivity.this.renderTodos();
        }
    };

}
