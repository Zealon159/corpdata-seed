package com.corpdata.common.result;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 分页数据封装
 *
 * @param <T> :实体对象
 */
public class DataGrid<T> implements Serializable {
	
    private static final long serialVersionUID = 2252240868205663450L;

    /**
     * 总数量
     */
    private long total;
    
    /**
     * 搜索条件
     */
    private Map<String, Object> searchParams;

    /**
     * 返回数据列表
     */
    private List<T> rows = new ArrayList<>();

    /**
     * 排序字段
     */
    String sort;
    
    /**
     * 排序顺序
     */
    String order;

    /**
     * 当前页码
     */
    private int page;

    /**
     * 页码大小
     */
    private int size;


    public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    /**
     * 获取查询参数
     * @return
     */
    public Map<String, Object> getSearchParams() {
        return searchParams;
    }

    public void setSearchParams(Map<String, Object> searchParams) {
        this.searchParams = searchParams;
    }

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
	
	/**
	 * 赋值查询总数 以及 查询数据
	 * @param total
	 * @param rows
	 */
	public void setTotalAndRows(long total,List<T> rows){
		this.total = total;
		this.rows = rows;
	}
	
}
