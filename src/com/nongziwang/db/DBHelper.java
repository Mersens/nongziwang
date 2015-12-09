package com.nongziwang.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	private static final int VERSION = 1;
	private static final String NAME = "nongzi.db";
	private static final String SQL_QUYU_CREAT = "create table areatb(areaid text primary key ,name text,cityid text references  citytb(cityid) on delete cascade on update cascade)";
	private static final String SQL_CHENGSHI_CREAT = "create table citytb(cityid text primary key  ,name text,provinceid text references  provincetb(provinceid) on delete cascade on update cascade )";
	private static final String SQL_SHENGFEN_CREAT = "create table provincetb(provinceid text primary key ,name text)";
	private static final String SQL_SEARCH_HISTORY_CREAT = "create table search_historytb(_id integer primary key autoincrement,userid text ,name text)";
	private static final String SQL_QUYU_DROP = "drop table if exists areatb";
	private static final String SQL_CHENGSHI_DROP = "drop table if exists citytb";
	private static final String SQL_SHENGFEN_DROP = "drop table if exists provincetb";
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
		db.execSQL(SQL_SHENGFEN_CREAT);
		db.execSQL(SQL_CHENGSHI_CREAT);
		db.execSQL(SQL_QUYU_CREAT);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(SQL_SHENGFEN_DROP);
		db.execSQL(SQL_CHENGSHI_DROP);
		db.execSQL(SQL_QUYU_DROP);
		db.execSQL(SQL_SEARCH_HISTORY_DROP);

		db.execSQL(SQL_SHENGFEN_CREAT);
		db.execSQL(SQL_CHENGSHI_CREAT);
		db.execSQL(SQL_QUYU_CREAT);
	}
}
