package com.lwxf.newstore.webapp.common.result;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

import com.lwxf.newstore.webapp.common.model.PaginatedList;
import com.lwxf.newstore.webapp.common.model.Pagination;

/**
 * 功能：请求返回的对象（json序列化时，为null的属性过滤掉）
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 9:43:49
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class RequestResult extends HashMap {
	private static final String KEY_DATA="data";
	private static final String KEY_PAGINATION="pagination";
	private static final String KEY_ERROR="error";
	private static final String KEY_PAGE_NUM="pageNum";
	private static final String KEY_PAGE_SIZE="pageSize";
	private static final String KEY_PAGE_TOTAL="total";
	private static final String KEY_PAGE_PAGES="pages";
	private static final String KEY_PAGE_NEXT_PAGE="nextPage";
	private RequestResult(){
	}

	public Object getData() {
		return this.get(KEY_DATA);
	}

	/**
	 * 生成错误结果
	 * @param errorCode
	 * @param error
	 * @return
	 */
	static RequestResult newInstance(String errorCode,Object error){
		RequestResult result = new RequestResult();
		result.put(KEY_ERROR,new HashedMap(){{
			put("code" , errorCode);
			put("message", error);
		}});
		return result;
	}

	/**
	 * 返回正常的返回结果
	 * @param obj：分页对象PaginatedList，其他对象任意
	 * @return
	 */
	static RequestResult newInstance(Object obj){
		RequestResult result = new RequestResult();
		if(!(obj instanceof PaginatedList)){
			result.put(KEY_DATA,obj);
			return result;
		}
		PaginatedList paginatedList = (PaginatedList)obj;
		result.put(KEY_DATA,paginatedList.getRows());
		result.put(KEY_PAGINATION,doPaginationToMap(paginatedList.getPagination()));
		return result;
	}

	/**
	 * 分页查询返回多个数据集的情况
	 * @param pagination
	 * @return
	 */
	static RequestResult newInstance(Map<String,Object> datas,Pagination pagination){
		RequestResult result = new RequestResult();
		result.put(KEY_DATA,datas);
		result.put(KEY_PAGINATION,doPaginationToMap(pagination));
		return result;
	}

	static Map<String,Object> doPaginationToMap(Pagination pagination){
		Map<String,Object> map = new HashedMap();
		map.put(KEY_PAGE_NUM,  pagination.getPageNum());
		map.put(KEY_PAGE_SIZE, pagination.getPageSize());
		map.put(KEY_PAGE_TOTAL, pagination.getTotalCount());
		map.put(KEY_PAGE_PAGES,pagination.getPages());
		map.put(KEY_PAGE_NEXT_PAGE,pagination.getNextPage());
		return map;
	}



	/**
	 * 返回正常的返回结果(空的json对象)
	 * @param
	 * @return
	 */
	static RequestResult newInstance(){
		return new RequestResult();
	}
}

