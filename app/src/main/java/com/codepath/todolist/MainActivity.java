package com.codepath.todolist;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    protected ToDoItemDbHelper db;
    ArrayList<ToDoItem> toDoItems;
    ToDoListAdapter aToDoAdapter;
    ListView lvItems;
    EditText etEditText;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new ToDoItemDbHelper(this);
        populateArrayItems();
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(aToDoAdapter);
        etEditText = (EditText) findViewById(R.id.etEditText);
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ToDoItem toDoItem = aToDoAdapter.getItem(i);
                Intent intent = new Intent(MainActivity.this, EditItemActivity.class);
                intent.putExtra("task", toDoItem.getTask());
                intent.putExtra("position",i);
                int id = toDoItem.getId();
                if(id == 0){
                    id = (int)l;
                    toDoItem.setId(id);
                }
                intent.putExtra("id", toDoItem.getId());
                intent.putExtra("duedate", toDoItem.getDueDate());
                intent.putExtra("status", toDoItem.getStatus());
                startActivityForResult(intent, 1);
            }
        });

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                int id = toDoItems.get(i).getId();
                if(id == 0){
                    id = (int)l;
                }
                db.deleteTask(id);
                toDoItems.remove(i);
                aToDoAdapter.notifyDataSetChanged();
                return true;
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void populateArrayItems() {
        //toDoItems = new ArrayList<ToDoItem>();

        toDoItems = db.getAllTasks();
        aToDoAdapter = new ToDoListAdapter(this,R.layout.item_todo,toDoItems);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_view_task, menu);
        return true;
    }

    public void onAddItem(View view) {
        String item = etEditText.getText().toString();
        if(item.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "enter the task description first!!",
                    Toast.LENGTH_LONG);
        }
        else{
            ToDoItem toDoItem = new ToDoItem(item, "TO-DO");
            db.addTask(toDoItem);
            etEditText.setText("");
            toDoItems.add(toDoItem);
            aToDoAdapter.notifyDataSetChanged();
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == 1) {
            // Extract name value from result extras
            String task = data.getStringExtra("task");
            int position = (int) data.getExtras().get("position");
            int id = (int)data.getExtras().get("id");
            Date date = (Date)data.getExtras().get("duedate");
            String status = data.getStringExtra("status");
            ToDoItem toDoItem = new ToDoItem(task, date, status);
            toDoItem.setId(id);
            db.updateTask(toDoItem);
            toDoItems.remove(position);
            toDoItems.add(position,toDoItem);
            aToDoAdapter.notifyDataSetChanged();


        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.codepath.todolist/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.codepath.todolist/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
