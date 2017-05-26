package com.kmzyc.framework.page;

import java.util.List;

public class Pagination {
    /**
     * 总记录数
     */
    private int totalRecords;
    /**
     * 分页结果
     */
    private List recordList;
    /**
     * 开始页码
     */
    private int page = 1;
    /**
     * 每页多少
     */
    private int numperpage = 12;
    /**
     * 开始记录
     */
    private int startindex;
    /**
     * 结束记录
     */
    private int endindex;
    /**
     * 查询条件
     */
    private Object objCondition;
    /**
     * 页码开始索引和结束索引
     **/
    private PageIndex pageindex;
    /**
     * 总页数
     **/
    private long totalpage = 1;
    /**
     * 页码数量
     **/
    private int pagecode = 10;

    /**
     * 分页构造函数
     *
     * @param page         　当前页
     * @param pagecode     　显示页码数量
     * @param numperpage   　每页显示多少页码
     * @param objCondition 　查询参数对象
     */
    public Pagination(int page, int pagecode, int numperpage, Object objCondition) {
        this.startindex = (page - 1) * numperpage + 1;
        this.endindex = (page - 1) * numperpage + numperpage;
        this.page = page;
        this.numperpage = numperpage;
        this.pagecode = pagecode;
        this.objCondition = objCondition;
    }

    /**
     * 分页构造函数
     *
     * @param page         　当前页
     * @param objCondition 　查询参数
     */
    public Pagination(int page, Object objCondition) {
        this.startindex = (page - 1) * numperpage + 1;
        this.endindex = (page - 1) * numperpage + numperpage;
        this.page = page;
        this.objCondition = objCondition;
    }

    public Pagination() {
    }

    public Object getObjCondition() {
        return objCondition;
    }

    public void setObjCondition(Object objCondition) {
        this.objCondition = objCondition;
    }


    public List getRecordList() {
        return recordList;
    }

    public void setRecordList(List recordList) {
        this.recordList = recordList;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getNumperpage() {
        return numperpage;
    }

    public void setNumperpage(int numperpage) {
        this.numperpage = numperpage;
    }

    public int getStartindex() {
        return startindex;
    }

    public void setStartindex(int startindex) {
        this.startindex = startindex;
    }

    public int getEndindex() {
        return endindex;
    }

    public void setEndindex(int endindex) {
        this.endindex = endindex;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
        setTotalpage(this.totalRecords % this.numperpage == 0 ? this.totalRecords / this.numperpage : this.totalRecords / this.numperpage + 1);
    }

    public PageIndex getPageindex() {
        return pageindex;
    }

    public long getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(long totalpage) {
        this.totalpage = totalpage;
        this.pageindex = WebTool.getPageIndex(pagecode, page, totalpage);
    }

    public int getPagecode() {
        return pagecode;
    }

    public void setPagecode(int pagecode) {
        this.pagecode = pagecode;
    }
}
