package com.vcmy.util;

import java.util.List;

/**
 * 开始分页
 * @param list
 * @param pageNum 页码
 * @param pageSize 每页多少条数据
 * @return
 */
public class PageUtils {
	
	public static <T> List<T> startPage(List<T> list, Integer pageNum,
            Integer pageSize) {
        if (list == null) {
            return null;
        }
        if (list.size() == 0) {
            return null;
        }
        if (pageNum == null || pageNum == 0 ) {
        	pageNum = 1;
        }
        if (pageSize == null || pageSize == 0) {
        	pageSize = 10;
        }
        Integer count = list.size(); // 记录总数
        Integer pageCount = 0; // 页数
        if (count % pageSize == 0) {
            pageCount = count / pageSize;
        } else {
            pageCount = count / pageSize + 1;
        }

        int fromIndex = 0; // 开始索引
        int toIndex = 0; // 结束索引

        int pageNum_int = pageNum.intValue();
        int pageCount_int = pageCount.intValue();
        if (pageNum_int != pageCount_int) {
            fromIndex = (pageNum - 1) * pageSize;
            toIndex = fromIndex + pageSize;
        } else {
            fromIndex = (pageNum - 1) * pageSize;
            toIndex = count;
        }
        List<T> pageList = list.subList(fromIndex,toIndex);

        return pageList;
    } 

}
