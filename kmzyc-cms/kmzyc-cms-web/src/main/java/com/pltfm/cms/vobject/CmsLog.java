package com.pltfm.cms.vobject;

import java.util.Date;
import java.util.List;

/**
 * 日志实体类
 */
public class CmsLog {
    private static final long serialVersionUID = 209820429595675997L;
    /**
     * 记录日志主键
     */
    private Integer cmsLogId;

    /**
     * 模块名称
     */
    private String moduleName;

    /**
     * 模块内容
     */
    private String moduleContent;

    /**
     * 变更记录
     */
    private String changeRecord;

    /**
     * 操作类型
     */
    private Integer type;

    /**
     * r操作人
     */
    private Integer consoleOperator;
    /**
     * 操作人姓名
     */
    private String userName;

    /**
     * 操作日期
     */
    private Date consoleOperatorDate;
    /**
     * 结束日期
     */
    private Date endDate;


    /**
     * 多行删除id集合
     */
    private String ids;

    /**
     * 数据Id
     */
    private Integer dataId;

    /**
     * 数据Id集合
     */
    private List<Integer> dataIds;
    /**
     * 数据类型
     */
    private Integer dataType;
    /**
     * 数据集合
     */
    private List listObject;


    /**
     * 窗口Id
     */
    private Integer windowId;
    /**
     * 开始索引值
     */
    private Integer startIndex;
    /**
     * 结束索引值
     */
    private Integer endIndex;

    /**
     * 数据Id集合
     */
    public List<Integer> getDataIds() {
        return dataIds;
    }

    /**
     * 数据Id集合
     */
    public void setDataIds(List<Integer> dataIds) {
        this.dataIds = dataIds;
    }

    /**
     * 多行删除id集合
     */
    public String getIds() {
        return ids;
    }

    /**
     * 多行删除id集合
     */

    public void setIds(String ids) {
        this.ids = ids;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * 数据集合
     */
    public List getListObject() {
        return listObject;
    }

    /**
     * 数据集合
     */
    public void setListObject(List listObject) {
        this.listObject = listObject;
    }

    public Integer getStartIndex() {
        return startIndex;
    }

    /**
     * 窗口Id
     */
    public Integer getWindowId() {
        return windowId;
    }

    /**
     * 窗口Id
     */
    public void setWindowId(Integer windowId) {
        this.windowId = windowId;
    }

    /**
     * 数据类型
     */
    public Integer getDataType() {
        return dataType;
    }

    /**
     * 数据类型
     */
    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    /**
     * 数据Id
     */
    public Integer getDataId() {
        return dataId;
    }

    /**
     * 数据Id
     */
    public void setDataId(Integer dataId) {
        this.dataId = dataId;
    }

    public Integer getEndIndex() {
        return endIndex;
    }


    public void setEndIndex(Integer endIndex) {
        this.endIndex = endIndex;
    }


    public Integer getCmsLogId() {
        return cmsLogId;
    }

    /**
     * r操作人
     */
    public Integer getConsoleOperator() {
        return consoleOperator;
    }

    /**
     * r操作人
     */
    public void setConsoleOperator(Integer consoleOperator) {
        this.consoleOperator = consoleOperator;
    }


    public String getUserName() {
        return userName;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }


    public Date getConsoleOperatorDate() {
        return consoleOperatorDate;
    }


    public void setConsoleOperatorDate(Date consoleOperatorDate) {
        this.consoleOperatorDate = consoleOperatorDate;
    }

    public void setCmsLogId(Integer cmsLogId) {
        this.cmsLogId = cmsLogId;
    }

    /**
     * 模块名称
     */
    public String getModuleName() {
        return moduleName;
    }

    /**
     * 模块名称
     */
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    /**
     * /**
     * 模块内容
     */
    public String getModuleContent() {
        return moduleContent;
    }

    /**
     * 模块内容
     */
    public void setModuleContent(String moduleContent) {
        this.moduleContent = moduleContent;
    }

    /**
     * 变更记录
     */
    public String getChangeRecord() {
        return changeRecord;
    }

    /**
     * 变更记录
     */
    public void setChangeRecord(String changeRecord) {
        this.changeRecord = changeRecord;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column CMS_LOG.TYPE
     *
     * @return the value of CMS_LOG.TYPE
     * @ibatorgenerated Mon Oct 28 17:24:43 CST 2013
     */
    public Integer getType() {
        return type;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column CMS_LOG.TYPE
     *
     * @param type the value for CMS_LOG.TYPE
     * @ibatorgenerated Mon Oct 28 17:24:43 CST 2013
     */
    public void setType(Integer type) {
        this.type = type;
    }


}