package com.nongziwang.utils;

import java.util.List;

import com.nongziwang.entity.MyRegion;
/**
 * 
 * @title ListUtils
 * @description:区域选择器的List操作类
 * @author Mersens
 * @time 2016年1月12日
 */
public class ListUtils {
	
	public static int getInitIndex(List<MyRegion> list,String key){
		for(int i=0;i<list.size();i++){
			MyRegion myregion=list.get(i);
			if(key.equals(myregion.getName())){
				return i;
			}
		}
		return 0;
	}
	
    public static String getMyRegionId(List<MyRegion> list,String key){
    	for(int i=0;i<list.size();i++){
    		MyRegion myregion=list.get(i);
    		if(key.equals(myregion.getName())){
    			return myregion.getId();
    		}
    	}
		return null;
    	
    }

}
