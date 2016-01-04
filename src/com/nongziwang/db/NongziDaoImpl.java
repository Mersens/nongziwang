package com.nongziwang.db;

import java.util.ArrayList;
import java.util.List;
import com.nongziwang.entity.MyRegion;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class NongziDaoImpl implements NongziDao{
	private DBHelper helper;
	private Context  context;
	public NongziDaoImpl(Context  context){
		helper=DBHelper.getInstance(context);
		this.context=context;
	}

	@Override
	public List<MyRegion> findAllProvinces() {
		List<MyRegion> list=new ArrayList<MyRegion>();
		list.add(new MyRegion("1", "«Î—°‘Ò", ""));
		CityDBManager dbm = new CityDBManager(
				context);
		dbm.openDatabase();
		SQLiteDatabase db = dbm.getDatabase();
		Cursor cursor = db.rawQuery("select * from provincetb ", null);
		while (cursor.moveToNext()) {
			MyRegion bean=new MyRegion();
			bean.setId(cursor.getString(cursor.getColumnIndex("provinceid")));
			bean.setName(cursor.getString(cursor.getColumnIndex("name")));
			bean.setParentid("");
			list.add(bean);
		}
		cursor.close();
		db.close();
		dbm.closeDatabase();
		return list;
	}

	@Override
	public List<MyRegion> findAllCitysByProvincesId(String provinceid) {
		
		List<MyRegion> list=new ArrayList<MyRegion>();
		list.add(new MyRegion("1", "«Î—°‘Ò", ""));
		CityDBManager dbm = new CityDBManager(
				context);
		dbm.openDatabase();
		SQLiteDatabase db = dbm.getDatabase();
		Cursor cursor = db.rawQuery("select * from citytb  where provinceid=?", new String[]{provinceid});
		while (cursor.moveToNext()) {
			MyRegion bean=new MyRegion();
			bean.setId(cursor.getString(cursor.getColumnIndex("cityid")));
			bean.setName(cursor.getString(cursor.getColumnIndex("name")));
			bean.setParentid(cursor.getString(cursor.getColumnIndex("provinceid")));
			list.add(bean);
		}
		cursor.close();
		db.close();
		dbm.closeDatabase();
		return list;
	}

	@Override
	public List<MyRegion> findAllAreasByCityId(String cityid) {
		List<MyRegion> list=new ArrayList<MyRegion>();
		list.add(new MyRegion("1", "«Î—°‘Ò", ""));
		CityDBManager dbm = new CityDBManager(
				context);
		dbm.openDatabase();
		SQLiteDatabase db = dbm.getDatabase();
		Cursor cursor = db.rawQuery("select * from areatb  where cityid=?", new String[]{cityid});
		while (cursor.moveToNext()) {
			MyRegion bean=new MyRegion();
			bean.setId(cursor.getString(cursor.getColumnIndex("areaid")));
			bean.setName(cursor.getString(cursor.getColumnIndex("name")));
			bean.setParentid(cursor.getString(cursor.getColumnIndex("cityid")));
			list.add(bean);
		}
		cursor.close();
		db.close();
		dbm.closeDatabase();
		return list;
	}
	@Override
	public void addSearchHistory(String userid, String sname) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("insert into search_historytb(userid,name) values(?,?)",
				 new Object[] {userid,sname});
		db.close();
	}

	@Override
	public List<String> selectAllHistory(String userid) {
		List<String> list=new ArrayList<String>();
		SQLiteDatabase db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from search_historytb where userid=? ",new String[]{userid});
				
		while (cursor.moveToNext()) {
			String name=cursor.getString(cursor.getColumnIndex("name"));
			list.add(name);
		}
		cursor.close();
		db.close();
		return list;
	}

	@Override
	public void delAllHistory(String userid) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("delete from search_historytb where userid=?",
				 new Object[] {userid});
		db.close();
	}



}
