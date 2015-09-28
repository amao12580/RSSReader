package per.rss.core.nosql.page;

import java.util.ArrayList;
import java.util.List;

import per.rss.core.base.util.StringUtils;

/**
 * 分页基础类
 * @author cifpay
 *
 */
public class Pagination<T> {
	/** 每页显示条数 */
	public  Integer pageSize = 10;
	
	/** 当前页 */
	private Integer currentPage = 1;

	/** 总页数 */
	private Integer totalPage = 1;

	/** 查询到的总数据量 */
	private Integer totalNumber = 0;

	/** 数据集 */
	private List<T> items;

	public Pagination(Pagination<?> p) {
		this.pageSize = p.getPageSize();
		this.currentPage = p.getCurrentPage();
		this.totalPage = p.getTotalPage();
		this.totalNumber = p.getTotalNumber();
	}
	
	public Pagination(int currentPage,int pageSize, int totalPage, int totalNumber) {
		this.pageSize = pageSize;
		this.currentPage = currentPage;
		this.totalPage = totalPage;
		this.totalNumber = totalNumber;
	}
	
	public Pagination() {//一个空的分页
		this.pageSize = 0;
		this.currentPage = 0;
		this.totalPage = 0;
		this.totalNumber = 0;
		this.items = new ArrayList<T>(0);
	}

	/**
	 * 处理查询后的结果数据
	 * 
	 * @param items
	 *            查询结果集
	 * @param count
	 *            总数
	 */
	public void build(List<T> items) {
		this.setItems(items);
		int count =  this.getTotalNumber();
		int divisor = count / this.getPageSize();
		int remainder = count % this.getPageSize();
		this.setTotalPage(remainder == 0 ? divisor == 0 ? 1 : divisor : divisor + 1);
	}
	public Integer getPageSize() {

		return pageSize;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Integer getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(Integer totalNumber) {
		this.totalNumber = totalNumber;
	}

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	@Override
	public String toString() {
		return StringUtils.toJSONString(this);
	}
}