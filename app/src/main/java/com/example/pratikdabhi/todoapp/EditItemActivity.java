package com.example.pratikdabhi.todoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditItemActivity extends Activity {
	private EditText editText1;
	private Button button1;
	private int position;
	private String oldAction;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		oldAction = new String("");

		editText1 = (EditText) findViewById(R.id.editText1);
		button1 = (Button) findViewById(R.id.button1);

		//get the values from parent
		position = getIntent().getIntExtra("position", 0);
		String action = getIntent().getStringExtra("action");

		editText1.setText(action);
		oldAction = action;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_item, menu);
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

	public void onEditSave(View v){
		Intent parent = new Intent();
		parent.putExtra("action", editText1.getText().toString());
		parent.putExtra("position", position);
		parent.putExtra("oldAction", oldAction);
		setResult (RESULT_OK, parent);
		//close the activity and return to the mail screen
		finish();
	}
}
