package com.nongziwang.db;

import java.util.ArrayList;
import java.util.List;

import com.nongziwang.entity.MyRegion;
import com.nongziwang.entity.UserBean;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class NongziDaoImpl implements NongziDao {
	private DBHelper helper;
	private Context context;

	public NongziDaoImpl(Context context) {
		helper = DBHelper.getInstance(context);
		this.context = context;
	}

	@Override
	public List<MyRegion> findAllProvinces() {
		List<MyRegion> list = new ArrayList<MyRegion>();
		list.add(new MyRegion("1", "«Î—°‘Ò", ""));
		CityDBManager dbm = new CityDBManager(context);
		dbm.openDatabase();
		SQLiteDatabase db = dbm.getDatabase();
		Cursor cursor = db.rawQuery("select * from provincetb ", null);
		while (cursor.moveToNext()) {
			MyRegion bean = new MyRegion();
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

		List<MyRegion> list = new ArrayList<MyRegion>();
		list.add(new MyRegion("1", "«Î—°‘Ò", ""));
		CityDBManager dbm = new CityDBManager(context);
		dbm.openDatabase();
		SQLiteDatabase db = dbm.getDatabase();
		Cursor cursor = db.rawQuery("select * from citytb  where provinceid=?",
				new String[] { provinceid });
		while (cursor.moveToNext()) {
			MyRegion bean = new MyRegion();
			bean.setId(cursor.getString(cursor.getColumnIndex("cityid")));
			bean.setName(cursor.getString(cursor.getColumnIndex("name")));
			bean.setParentid(cursor.getString(cursor
					.getColumnIndex("provinceid")));
			list.add(bean);
		}
		cursor.close();
		db.close();
		dbm.closeDatabase();
		return list;
	}

	@Override
	public List<MyRegion> findAllAreasByCityId(String cityid) {
		List<MyRegion> list = new ArrayList<MyRegion>();
		list.add(new MyRegion("1", "«Î—°‘Ò", ""));
		CityDBManager dbm = new CityDBManager(context);
		dbm.openDatabase();
		SQLiteDatabase db = dbm.getDatabase();
		Cursor cursor = db.rawQuery("select * from area  where cityid=?",
				new String[] { cityid });
		while (cursor.moveToNext()) {
			MyRegion bean = new MyRegion();
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
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("insert into search_historytb(userid,name) values(?,?)",
				new Object[] { userid, sname });
		db.close();
	}

	@Override
	public List<String> selectAllHistory(String userid) {
		List<String> list = new ArrayList<String>();
		SQLiteDatabase db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery(
				"select * from search_historytb where userid=? ",
				new String[] { userid });

		while (cursor.moveToNext()) {
			String name = cursor.getString(cursor.getColumnIndex("name"));
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
				new Object[] { userid });
		db.close();
	}

	@Override
	public boolean findHistoryIsExist(String name) {
		boolean flag = false;
		SQLiteDatabase db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery(
				"select * from search_historytb where name=? ",
				new String[] { name });
		while (cursor.moveToNext()) {
			flag = true;
		}
		cursor.close();
		db.close();
		return flag;
	}

	@Override
	public void addUserInfo(UserBean user) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL(
				"insert into usertb(userid,username,userpwd,userphone,qq,xingming,addtime,companyid,htmlid,touxiang,dianpuid,gsstatic) values(?,?,?,?,?,?,?,?,?,?,?,?)",
				new Object[] { user.getUserid(), user.getUsername(),
						user.getUserpwd(), user.getUserphone(), user.getQq(),
						user.getXingming(), user.getAddtime(),
						user.getCompanyid(), user.getHtmlid(),
						user.getTouxiang(), user.getDianpuid() ,user.getGsstatic()});
		db.close();

	}

	@Override
	public UserBean findUserInfoById(String userid) {
		List<UserBean> list = new ArrayList<UserBean>();
		SQLiteDatabase db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from usertb where userid=? ",
				new String[] { userid });
		while (cursor.moveToNext()) {
			UserBean user = new UserBean();
			String id = cursor.getString(cursor.getColumnIndex("userid"));
			user.setUserid(id);
			String username = cursor.getString(cursor
					.getColumnIndex("username"));
			user.setUsername(username);
			String userpwd = cursor.getString(cursor.getColumnIndex("userpwd"));
			user.setUserpwd(userpwd);
			String userphone = cursor.getString(cursor
					.getColumnIndex("userphone"));
			user.setUserphone(userphone);
			String qq = cursor.getString(cursor.getColumnIndex("qq"));
			user.setQq(qq);
			String xingming = cursor.getString(cursor
					.getColumnIndex("xingming"));
			user.setXingming(xingming);
			String addtime = cursor.getString(cursor.getColumnIndex("addtime"));
			user.setAddtime(addtime);
			String companyid = cursor.getString(cursor
					.getColumnIndex("companyid"));
			user.setCompanyid(companyid);
			String htmlid = cursor.getString(cursor.getColumnIndex("htmlid"));
			user.setHtmlid(htmlid);
			String touxiang = cursor.getString(cursor
					.getColumnIndex("touxiang"));
			user.setTouxiang(touxiang);
			String dianpuid = cursor.getString(cursor
					.getColumnIndex("dianpuid"));
			user.setDianpuid(dianpuid);
			String gsstatic = cursor.getString(cursor
					.getColumnIndex("gsstatic"));
			user.setGsstatic(gsstatic);
			
			list.add(user);
		}
		cursor.close();
		db.close();
		if (list.size() > 0)
			return list.get(0);
		else
			return null;
	}

	@Override
	public void delUserInfoById(String userid) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("delete from usertb where userid=?", new Object[] { userid });
		db.close();
	}

	@Override
	public void updateUserHeadById(String userid, String touxiang) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("UPDATE usertb SET touxiang=? where userid=?", new Object[] {
				touxiang, userid });
		db.close();
	}

	@Override
	public void updateNameAndQq(String userid, String name, String qq) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("UPDATE usertb SET xingming=?, qq=? where userid=?",
				new Object[] { name, qq, userid });
		db.close();
	}

	@Override
	public void updateCompanyId(String userid, String companyid) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("UPDATE usertb SET companyid=? where userid=?",
				new Object[] { companyid, userid });
		db.close();
	}

	@Override
	public void updateUserInfo(UserBean user, String userid) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL(
				"UPDATE usertb SET username=?, userphone=?,qq=?,xingming=?,addtime=?,companyid=?,htmlid=?,touxiang=?,dianpuid=? where userid=?",
				new Object[] { user.getUsername(), user.getUserphone(),
						user.getQq(), user.getXingming(), user.getAddtime(),
						user.getCompanyid(), user.getHtmlid(),
						user.getTouxiang(), user.getDianpuid(), userid });
		db.close();
	}

	@Override
	public void updateGsStatic(String userid, String gsstatic) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("UPDATE usertb SET gsstatic=? where userid=?",
				new Object[] { gsstatic, userid });
		db.close();		
	}

}
