package com.lwxf.newstore.webapp.common.model;

import java.util.List;
import java.util.Map;

import com.lwxf.mybatis.utils.MapContext;


/**
 * 功能：分页查询过滤条件
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 18:32:20
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class PaginatedFilter {
	/** [搜索用]的关键字符串 */
	private String keywords;
	/** 过滤条件项 */
	private MapContext filters = MapContext.newOne();
	/** 排序项 */
	private List<Map<String, String>> sorts;
	/** 分页信息 */
	private Pagination pagination = Pagination.newOne();

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public MapContext getFilters() {
		return filters;
	}

	public void setFilters(MapContext filters) {
		this.filters = filters;
	}

	public List<Map<String, String>> getSorts() {
		return sorts;
	}

	public void setSorts(List<Map<String, String>> sorts) {
		this.sorts = sorts;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public static PaginatedFilter newOne() {
		return new PaginatedFilter();
	}

	@Override
	public String toString() {
		return "PaginatedFilter [keywords=" + keywords + ", filters=" + filters + ", sortItems=" + sorts + ", pagination=" + pagination + "]";
	}
}
