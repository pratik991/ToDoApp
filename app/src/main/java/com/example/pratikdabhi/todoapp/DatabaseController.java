package com.example.pratikdabhi.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.util.ArrayList;
import java.util.List;

public class DatabaseController {
	private MySQLiteOpenHelper dbhelper;
	private SQLiteDatabase database;
	private String columns[] = {
			MySQLiteOpenHelper.COLUMN_ID,
			MySQLiteOpenHelper.COLUMN_TASK
	};

	public DatabaseController(Context context){
		dbhelper = new MySQLiteOpenHelper(context);
	}

	public void open() throws SQLiteException {
		database = dbhelper.getWritableDatabase();
	}

	public void close() {
		dbhelper.close();
	}

	public Action insert(String action){
		ContentValues values = new ContentValues();
		values.put(dbhelper.COLUMN_TASK, action);
		long rowId = database.insert(dbhelper.TABLE_NAME,null, values);
		Cursor cursor = database.query(dbhelper.TABLE_NAME,
				columns, dbhelper.COLUMN_ID + "=" + rowId,
				null, null, null, null);
		cursor.moveToFirst();
		return cursorToAction(cursor);
	}

	public int update(Action oldAction, Action newAction){
		ContentValues values = new ContentValues();
		values.put(dbhelper.COLUMN_TASK, newAction.getAction());
		//	return database.update(dbhelper.TABLE_NAME, values,
//				dbhelper.COLUMN_TASK + " = ?", new String[] { action.getAction() } );
		return database.update(dbhelper.TABLE_NAME, values,
				dbhelper.COLUMN_TASK + " = ?", new String[] { oldAction.getAction() } );

	}

	public List<Action> getAll() {
		Cursor cursor = database.query(dbhelper.TABLE_NAME,
				columns, null, null, null, null, null );
		cursor.moveToFirst();
		List<Action> actions = new ArrayList<Action>();
		while (!cursor.isAfterLast()) {
			actions.add(cursorToAction(cursor));
			cursor.moveToNext();
		}
		return actions;
	}

	public void delete(Action action) {
		database.delete(dbhelper.TABLE_NAME, dbhelper.COLUMN_TASK + "=\'" + action.getAction() +"\'", null);
	}

	public Action cursorToAction(Cursor cursor) {
		long row = cursor.getLong(0);
		String actionString = cursor.getString(1);
		Action action = new Action(row,actionString);
		return action;
	}
}
