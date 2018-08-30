package com.lwxf.newstore.webapp.common.model;

import com.lwxf.newstore.webapp.common.json.JsonNullDeserializer;
import com.lwxf.newstore.webapp.common.json.JsonZeroDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * 功能：分页基本信息
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 18:32:20
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class Pagination {
	/** 结果记录总数 */
	@JsonDeserialize(using = JsonZeroDeserializer.class)
	private Integer totalCount = 0;
	/** 分页大小（每页结果记录数） */
	private Integer pageSize = 10;
	/** 目标页码（从1开始） */
	private Integer pageNum = 1;

	private Integer pages = null;
	//
	@JsonDeserialize(using = JsonNullDeserializer.class)
	private Range<Integer> pageIndexRange = Range.newOne(-1, -1);

	private void adjustPageIndexRange() {
		int fromIndex = -1;
		int toIndex = -1;
		if (totalCount > 0) {
			fromIndex = (pageNum - 1) * pageSize;
			toIndex = Math.min(totalCount - 1, pageNum * pageSize - 1);
		}
		pageIndexRange.setFrom(fromIndex);
		pageIndexRange.setTo(toIndex);
	}

	public void reset() {
		this.setPageNum(1);
	}

	//

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		//
		this.adjustPageIndexRange();
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
		//
		this.adjustPageIndexRange();
	}

	/** 【根据结果总数量,获取获取在当前分页大小和页码下第一条和最后一条记录在总记录中的索引位置】 */
	public Range<Integer> getPageIndexRange() {
		return pageIndexRange;
	}

	/** 【忽略结果总数量】获取在当前分页大小和页码下的第一条记录的索引位置 */
	public Integer getPageIndexFrom() {
		return (pageNum - 1) * pageSize;
	}

	public void setPageIndexRange(Range<Integer> pageIndexRange) {
		this.pageIndexRange = pageIndexRange;
	}

	public int getPages(){
		if(this.pages != null){
			return this.pages;
		}
		if(totalCount==0){
			this.pages = 0;
			return this.pages;
		}
		if(pageSize == 0){
			this.pages = 1;
			return this.pages;
		}

		this.pages = totalCount / pageSize;
		int m = totalCount % pageSize;
		if (m > 0) {
			this.pages += 1;
		}
		return this.pages;
		/*if (totalCount > 0) {
			if (pageSize > 0) {
				this.pages = totalCount / pageSize;
				int m = totalCount % pageSize;
				if (m > 0) {
					this.pages += 1;
				}
			}else{
				this.pages = 1;
			}
		}
		this.pages = 0;
		return this.pages;*/
	}

	public int getNextPage(){
		int pagesTmp = this.getPages();
		if(this.pageNum<pagesTmp){
			return this.pageNum+1;
		}
		return 0;
	}

	public static Pagination newOne() {
		return new Pagination();
	}
}
