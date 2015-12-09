package com.nongziwang.db;

import java.util.List;

import com.nongziwang.entity.Area;
import com.nongziwang.entity.City;
import com.nongziwang.entity.Province;

/**
 * 
 * @author Mersens
 * ���ݿ�����ӿ�
 *
 */
public interface NongziDao {
	//��ѯ����ʡ��
	public List<Province> findAllProvinces();
	
    //��ѯ���г���
	public List<City> findAllCitys();

	//��ѯͨ��ʡ��id���Ҹ�������
	public List<City> findAllCitysByProvincesId(String provinceid);

	//������������
	public List<Area> findAllAreas();

	//ͨ������id�������и�������
	public List<Area> findAllAreasByCityId(String cityid);

	//���ʡ��
	public void addAllProvinces(List<Province> list);

	//��ӳ���
	public void addAllCitys(List<City> list);

	//�������
	public void addAllAreas(List<Area> list);
	
	//���һ��������¼
	public void addSearchHistory(String userid,String sname);
	
	//��ѯȫ��������¼
	public List<String> selectAllHistory(String userid);
	
	//�����ѯ��ʷ��¼
	public void delAllHistory(String userid);
}
