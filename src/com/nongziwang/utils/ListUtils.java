package com.nongziwang.utils;

import java.util.List;

import com.nongziwang.entity.MyRegion;

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
