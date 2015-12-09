package com.nongziwang.db;

import java.util.List;

import com.nongziwang.entity.Area;
import com.nongziwang.entity.City;
import com.nongziwang.entity.Province;

/**
 * 
 * @author Mersens
 * 数据库操作接口
 *
 */
public interface NongziDao {
	//查询所有省份
	public List<Province> findAllProvinces();
	
    //查询所有城市
	public List<City> findAllCitys();

	//查询通过省份id查找附属城市
	public List<City> findAllCitysByProvincesId(String provinceid);

	//查找所有区域
	public List<Area> findAllAreas();

	//通过城市id查找所有附属区域
	public List<Area> findAllAreasByCityId(String cityid);

	//添加省份
	public void addAllProvinces(List<Province> list);

	//添加城市
	public void addAllCitys(List<City> list);

	//添加区域
	public void addAllAreas(List<Area> list);
	
	//添加一条搜索记录
	public void addSearchHistory(String userid,String sname);
	
	//查询全部搜索记录
	public List<String> selectAllHistory(String userid);
	
	//清除查询历史记录
	public void delAllHistory(String userid);
}
