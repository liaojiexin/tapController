package com.vcmy.entity;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

public class PageDomain {
	/** 每页条数 */
	@ApiModelProperty(value = "每页条数")
	private Integer count;
	/** 页数 */
	@ApiModelProperty(value = "当前页数")
	private Integer page;
	/** 排序 */
	private String sort;
	/** asc desc */
	private String isAsc;
	/** 搜索值 */
	@ApiModelProperty(value = "搜索内容")
	private String searchValue;
	/** 初始排序 */
	private String order;
	/** 初始升序或降序*/
	private String desc;

	/**
	 * @return the count
	 */
	public Integer getCount() {
		return count;
	}
	/**
	 * @param count the count to set
	 */
	public void setCount(Integer count) {
		this.count = count;
	}
	/**
	 * @return the page
	 */
	public Integer getPage() {
		return page;
	}
	/**
	 * @param page the page to set
	 */
	public void setPage(Integer page) {
		this.page = page;
	}
	/**
	 * @return the sort
	 */
	public String getSort() {
		if (StringUtils.isEmpty(sort)) {
			return "";
		}
		return sort + " " + isAsc;
	}
	/**
	 * @param sort the sort to set
	 */
	public void setSort(String sort) {
		this.sort = sort;
	}
	/**
	 * @return the isAsc
	 */
	public String getIsAsc() {
		return isAsc;
	}
	/**
	 * @param isAsc the isAsc to set
	 */
	public void setIsAsc(String isAsc) {
		this.isAsc = isAsc;
	}
	/**
	 * @return the searchValue
	 */
	public String getSearchValue() {
		return searchValue;
	}
	/**
	 * @param searchValue the searchValue to set
	 */
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
	/**
	 * @return the order
	 */
	public String getOrder() {
		if (StringUtils.isEmpty(order)) {
			return "";
		}
		if (StringUtils.isNotBlank(desc)) {
			return order + " " + desc;
		}
		return order;
	}
	/**
	 * @param order the order to set
	 */
	public void setOrder(String order) {
		this.order = order;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}
	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getOrderBy(){
		if(StringUtils.isNoneBlank(order) && StringUtils.isNotBlank(sort)){
			return this.getSort() +"," + this.getOrder();
		}
		return this.getSort() + this.getOrder();
	}
}
