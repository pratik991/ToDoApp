package com.example.pratikdabhi.todoapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
	public static final String TABLE_NAME="todo";
	public static final String DATABASE_NAME="todo.db";
	public static final String COLUMN_ID="_id";
	public static final String COLUMN_TASK="task";
	public static final int DATABASE_VERSION=1;

	public static final String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME +	"("
			+ COLUMN_ID + " integer primary key autoincrement," + COLUMN_TASK +
			" text no null )";

	public MySQLiteOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_TABLE_QUERY);
		System.out.println("**************");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
	}
}
