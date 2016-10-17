package com.ysm.weibo.showphoto.utils;

import java.util.List;

public class ListUtils {

	/**
	 * @description 【判断集合是否为空,如果每个元素都为空，就认为集合为空】
	 * @param list
	 * @return
	 */
	public static boolean isBlank(List<?> list){
		boolean isEmpty = true;
		if (!isEmpty(list)){
			isEmpty = false;
		}
		else{
			for(Object temp : list){
				if(temp != null){
					isEmpty = false;
					break;
				}
			}
		}
		return isEmpty;
	}
	/**
	 * @description 【判断集合是否为空】
	 * @return
	 */
	public static boolean isEmpty(List<?> list){
		boolean isEmpty = false;
		if (null == list || list.isEmpty()) {
			isEmpty = true;
		}
		return isEmpty;
	}
	
	/**
	 * @Description:集合不为空返回为真。
	 * @param list
	 * @return
	 */
	public static boolean isNotEmpty(List<?> list){
		return !isEmpty(list);
	}
}
