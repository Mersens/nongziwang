package com.nongziwang.db;

import java.util.List;

import com.nongziwang.entity.MyRegion;
import com.nongziwang.entity.UserBean;

/**
 * 
 * @title NongziDao
 * @description:数据库操作接口--历史搜索记录，城市和地区查询
 * @author Mersens
 * @time 2016年1月14日
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
	
	// 查询记录是否存在
	public boolean findHistoryIsExist(String name);
	
	// 添加用户信息
	public void addUserInfo(UserBean user);
	
	//更新用户信息
	public void updateUserInfo(UserBean user,String userid);
	
	// 查询用户的信息
	public UserBean findUserInfoById(String userid);
	
	//删除用户信息
	public void delUserInfoById(String userid);
	
	//修改用户头像
	public void updateUserHeadById(String userid,String touxiang);
	
	//修改真实姓名和QQ
	public void updateNameAndQq(String userid,String name,String qq);
	
	//修改用户的公司id
	public void updateCompanyId(String userid,String companyid);
		
	

}
