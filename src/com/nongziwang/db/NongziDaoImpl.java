package com.nongziwang.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nongziwang.entity.Area;
import com.nongziwang.entity.City;
import com.nongziwang.entity.Province;

public class NongziDaoImpl implements NongziDao{
	private DBHelper helper;
	public NongziDaoImpl(Context  context){
		helper=DBHelper.getInstance(context);
	}

	@Override
	public List<Province> findAllProvinces() {
		List<Province> list=new ArrayList<Province>();
		SQLiteDatabase db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from provincetb ", null);
				
		while (cursor.moveToNext()) {
			Province bean=new Province();
			bean.setProvinceid(cursor.getString(cursor.getColumnIndex("provinceid")));
			bean.setName(cursor.getString(cursor.getColumnIndex("name")));
			list.add(bean);
		}
		cursor.close();
		db.close();
		return list;
	}

	@Override
	public List<City> findAllCitys() {
		List<City> list=new ArrayList<City>();
		SQLiteDatabase db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from citytb ", null);
				
		while (cursor.moveToNext()) {
			City bean=new City();
			bean.setCityid(cursor.getString(cursor.getColumnIndex("cityid")));
			bean.setProvinceid(cursor.getString(cursor.getColumnIndex("provinceid")));
			bean.setName(cursor.getString(cursor.getColumnIndex("name")));
			list.add(bean);
		}
		cursor.close();
		db.close();
		return list;
	}

	@Override
	public List<City> findAllCitysByProvincesId(String provinceid) {
		List<City> list=new ArrayList<City>();
		SQLiteDatabase db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from citytb where provinceid=? ", new String[]{provinceid});
				
		while (cursor.moveToNext()) {
			City bean=new City();
			bean.setCityid(cursor.getString(cursor.getColumnIndex("cityid")));
			bean.setProvinceid(cursor.getString(cursor.getColumnIndex("provinceid")));
			bean.setName(cursor.getString(cursor.getColumnIndex("name")));
			list.add(bean);
		}
		cursor.close();
		db.close();
		return list;
	}

	@Override
	public List<Area> findAllAreas() {
		List<Area> list=new ArrayList<Area>();
		SQLiteDatabase db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from areatb ", null);
				
		while (cursor.moveToNext()) {
			Area bean=new Area();
			bean.setCityid(cursor.getString(cursor.getColumnIndex("cityid")));
			bean.setAreaid(cursor.getString(cursor.getColumnIndex("areaid")));
			bean.setName(cursor.getString(cursor.getColumnIndex("name")));
			list.add(bean);
		}
		cursor.close();
		db.close();
		return list;
	}

	@Override
	public List<Area> findAllAreasByCityId(String cityid) {
		List<Area> list=new ArrayList<Area>();
		SQLiteDatabase db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from areatb where cityid=? ", new String[]{cityid});
		while (cursor.moveToNext()) {
			Area bean=new Area();
			bean.setCityid(cursor.getString(cursor.getColumnIndex("cityid")));
			bean.setAreaid(cursor.getString(cursor.getColumnIndex("areaid")));
			bean.setName(cursor.getString(cursor.getColumnIndex("name")));
			list.add(bean);
		}
		cursor.close();
		db.close();
		return list;
	}

	@Override
	public void addAllProvinces(List<Province> list) {
		if(list==null || list.size()==0){
			return;
		}
		SQLiteDatabase db = helper.getWritableDatabase();
		for(Province province:list){
			db.execSQL("insert into provincetb(provinceid,name) values(?,?)",
					 new Object[] {province.getProvinceid(),province.getName()});
		}
		db.close();
		
	}

	@Override
	public void addAllCitys(List<City> list) {
		if(list==null || list.size()==0){
			return;
		}
		SQLiteDatabase db = helper.getWritableDatabase();
		for(City city:list){
			db.execSQL("insert into citytb(cityid,name,provinceid) values(?,?,?)",
					 new Object[] {city.getCityid(),city.getName(),city.getProvinceid()});
		}
		db.close();		
	}

	@Override
	public void addAllAreas(List<Area> list) {
		if(list==null || list.size()==0){
			return;
		}
		SQLiteDatabase db = helper.getWritableDatabase();
		for(Area area:list){
			db.execSQL("insert into areatb(areaid,name,cityid) values(?,?,?)",
					 new Object[] {area.getAreaid(),area.getName(),area.getCityid()});
		}
		db.close();				
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
