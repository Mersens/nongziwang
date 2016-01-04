package com.nongziwang.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	private static final int VERSION = 1;
	private static final String NAME = "nongzi.db";
	private static final String SQL_SEARCH_HISTORY_CREAT = "create table search_historytb(_id integer primary key autoincrement,userid text ,name text)";
	private static final String SQL_SEARCH_HISTORY_DROP = "drop table if exists search_historytb";
	public static DBHelper helper = null;
	
	public static DBHelper getInstance(Context context) {
		if (helper == null) {
			synchronized (DBHelper.class) {
				if (helper == null) {
					helper = new DBHelper(context.getApplicationContext());
				}
			}
		}
		return helper;
	}

	private DBHelper(Context context) {
		super(context, NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_SEARCH_HISTORY_CREAT);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(SQL_SEARCH_HISTORY_DROP);
		db.execSQL(SQL_SEARCH_HISTORY_CREAT);

	}
}
