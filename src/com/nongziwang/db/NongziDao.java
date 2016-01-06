package com.nongziwang.db;

import java.util.List;
import com.nongziwang.entity.MyRegion;

/**
 * 
 * @author Mersens
 *  ���ݿ�����ӿ�--��ʷ������¼�����к͵�����ѯ
 *
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
	
	public boolean findHistoryIsExist(String name);

}
