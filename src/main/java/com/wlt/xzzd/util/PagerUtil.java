package com.wlt.xzzd.util;

/**
 * @ClassName: PagerUtil
 * @Description: 分页查询工具类
 * @author liulong03@chinasoftinc.com
 *
 */
public class PagerUtil
{
    /**
     * 每页显示数量
     */
    private Integer pageSize = 10;

    /**
     * 页面
     */
    private Integer pageNo;

    /**
     * 总数
     */
    private Integer totalRecords;

    /**
     * 总页数
     */
    private Integer totalPage;

    public Integer getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }

    public Integer getPageNo()
    {
        return pageNo;
    }

    public void setPageNo(Integer pageNo)
    {
        this.pageNo = pageNo;
    }

    public Integer getTotalRecords()
    {
        return totalRecords;
    }

    public void setTotalRecords(Integer totalRecords)
    {
        this.totalRecords = totalRecords;
    }

    public Integer getTotalPage()
    {
        totalPage = this.totalRecords % this.pageSize == 0 ? this.totalRecords / this.pageSize : this.totalRecords
            / this.pageSize + 1;
        return totalPage;
    }

    public void setTotalPage(Integer totalPage)
    {
        this.totalPage = totalPage;
    }

}
