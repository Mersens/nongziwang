package com.nongziwang.db;

import java.util.List;

import com.nongziwang.entity.MyRegion;
import com.nongziwang.entity.UserBean;

/**
 * 
 * @title NongziDao
 * @description:���ݿ�����ӿ�--��ʷ������¼�����к͵�����ѯ
 * @author Mersens
 * @time 2016��1��14��
 */
public interface NongziDao {
	// ��ѯ����ʡ��
	public List<MyRegion> findAllProvinces();

	// ��ѯͨ��ʡ��id���Ҹ�������
	public List<MyRegion> findAllCitysByProvincesId(String provinceid);

	// ͨ������id�������и�������
	public List<MyRegion> findAllAreasByCityId(String cityid);

	// ���һ��������¼
	public void addSearchHistory(String userid, String sname);

	// ��ѯȫ��������¼
	public List<String> selectAllHistory(String userid);

	// �����ѯ��ʷ��¼
	public void delAllHistory(String userid);
	
	// ��ѯ��¼�Ƿ����
	public boolean findHistoryIsExist(String name);
	
	// ����û���Ϣ
	public void addUserInfo(UserBean user);
	
	//�����û���Ϣ
	public void updateUserInfo(UserBean user,String userid);
	
	// ��ѯ�û�����Ϣ
	public UserBean findUserInfoById(String userid);
	
	//ɾ���û���Ϣ
	public void delUserInfoById(String userid);
	
	//�޸��û�ͷ��
	public void updateUserHeadById(String userid,String touxiang);
	
	//�޸���ʵ������QQ
	public void updateNameAndQq(String userid,String name,String qq);
	
	//�޸��û��Ĺ�˾id
	public void updateCompanyId(String userid,String companyid);
		
	

}
