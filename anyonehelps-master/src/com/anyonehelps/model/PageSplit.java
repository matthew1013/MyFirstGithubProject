package com.anyonehelps.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PageSplit<T> implements Serializable {

	private static final long serialVersionUID = -4620356393012335556L;
	private int rowCount;
	private int pageCount;
	private int pageSize;
	private int pageNow;
	private List<T> datas = new ArrayList<T>();

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNow() {
		return pageNow;
	}

	public void setPageNow(int pageNow) {
		this.pageNow = pageNow;
	}

	public List<T> getDatas() {
		return datas;
	}

	public void setDatas(List<T> datas) {
		if (datas != null && !datas.isEmpty()) {
			for (T t : datas) {
				this.datas.add(t);
			}
		}
	}

	public void addData(T data) {
		this.datas.add(data);
	}
}
