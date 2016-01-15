package com.nongziwang.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	private static final int VERSION = 2;
	private static final String NAME = "nongzi.db";
	private static final String SQL_SEARCH_HISTORY_CREAT = "create table search_historytb(_id integer primary key autoincrement,userid text ,name text)";
	private static final String SQL_SEARCH_HISTORY_DROP = "drop table if exists search_historytb";
	private static final String SQL_USERTB_CREAT = "create table usertb(_id integer primary key autoincrement,userid text ,username text,userpwd text,userphone text,qq text,xingming text,addtime text,companyid text,htmlid text,touxiang text)";
	private static final String SQL_USERTB_DROP = "drop table if exists usertb";
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
		db.execSQL(SQL_USERTB_CREAT);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(SQL_SEARCH_HISTORY_DROP);
		db.execSQL(SQL_USERTB_DROP);
		db.execSQL(SQL_SEARCH_HISTORY_CREAT);
		db.execSQL(SQL_USERTB_CREAT);

	}
}
