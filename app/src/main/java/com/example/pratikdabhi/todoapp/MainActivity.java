package com.example.pratikdabhi.todoapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements EditItemDialogFragment.EditItemDialogListener{

    List<Action> items;
    ArrayAdapter<Action> itemsAdaptor;
    ListView lvItems;
    private DatabaseController dbController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbController = new DatabaseController(this);
        dbController.open();

        //get the list view and add the adaptop
        lvItems = (ListView) findViewById(R.id.lvItems);

        readItems();
        //configure the adaptor here
        itemsAdaptor = new ArrayAdapter<Action>(this,
                android.R.layout.simple_list_item_1,
                items
        );
        lvItems.setAdapter(itemsAdaptor);

        setupListViewListener();
    }

    private void setupListViewListener(){
        //lvItems.setLongClickable(true);
        lvItems.setOnItemLongClickListener( new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick (AdapterView<?> parent,
                                            View view, int position, long rowId){
                dbController.delete(items.get(position));
                items.remove(position);
                itemsAdaptor.notifyDataSetChanged();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowID){
                //open the edit window

                FragmentManager fm = getSupportFragmentManager();
                String taskName = items.get(position).getAction();
                EditItemDialogFragment editNameDialogFragment = EditItemDialogFragment.newInstance(taskName,position);
                editNameDialogFragment.show(fm, "fragment_edit_name");

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.todo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addTodoItem(View v){
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        Action action = new Action(items.size(),  etNewItem.getText().toString());
        itemsAdaptor.add(action);
        etNewItem.setText("");
        dbController.insert(action.getAction());
    }

    private void readItems(){
        items = (ArrayList<Action>)dbController.getAll();
    }

    @Override
    public void onFinishEditDialog(int position, String newTaskName, String oldTaskName) {

        items.set(position, new Action(position, newTaskName));
        itemsAdaptor.notifyDataSetChanged();

        Action NewAction = new Action(position, newTaskName);
        Action OldAction = new Action(position, oldTaskName);

        dbController.update(OldAction, NewAction);

    }
}
