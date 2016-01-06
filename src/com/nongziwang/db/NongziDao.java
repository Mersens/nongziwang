package com.nongziwang.db;

import java.util.List;
import com.nongziwang.entity.MyRegion;

/**
 * 
 * @author Mersens
 *  数据库操作接口--历史搜索记录，城市和地区查询
 *
 */
public interface NongziDao {
	// 查询所有省份
	public List<MyRegion> findAllProvinces();

	// 查询通过省份id查找附属城市
	public List<MyRegion> findAllCitysByProvincesId(String provinceid);

	// 通过城市id查找所有附属区域
	public List<MyRegion> findAllAreasByCityId(String cityid);

	// 添加一条搜索记录
	public void addSearchHistory(String userid, String sname);

	// 查询全部搜索记录
	public List<String> selectAllHistory(String userid);

	// 清除查询历史记录
	public void delAllHistory(String userid);
	
	public boolean findHistoryIsExist(String name);

}
