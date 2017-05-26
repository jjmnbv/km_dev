package com.kmzyc.b2b.model;

public class Categorys {
    // 物理类目唯一性标示
    private Long categoryId;
    // 类目父id
    private Long parentId;
    // 编码规则：父编码+该父类目4位唯一编码
    private String categoryCode;
    // 类目名称
    private String categoryName;
    // 类目状态（0:无效,1:有效,2:删除）
    private Integer status;
    // 排序号（越小越靠前）
    private Integer sortno;
    // 类目说明
    private String categoryDesc;
    // 创建时间
    private String createTime;
    // 修改时间
    private String modifTime;
    // 维护人（创建、修改）
    private Long modifUser;
    // 所属渠道
    private String channel;
    // 运营映射关系sql
    private String execSql;
    // 1：物理类目；2：运营类目
    private Integer isPhy;
    // 运营映射关系sql文字描述
    private String sqlString;

    // 图标
    private String filePath;

    public Long getCategoryId() {
        return categoryId;
    }



    public String getFilePath() {
        return filePath;
    }



    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }



    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSortno() {
        return sortno;
    }

    public void setSortno(Integer sortno) {
        this.sortno = sortno;
    }

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifTime() {
        return modifTime;
    }

    public void setModifTime(String modifTime) {
        this.modifTime = modifTime;
    }

    public Long getModifUser() {
        return modifUser;
    }

    public void setModifUser(Long modifUser) {
        this.modifUser = modifUser;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getExecSql() {
        return execSql;
    }

    public void setExecSql(String execSql) {
        this.execSql = execSql;
    }

    public Integer getIsPhy() {
        return isPhy;
    }

    public void setIsPhy(Integer isPhy) {
        this.isPhy = isPhy;
    }

    public String getSqlString() {
        return sqlString;
    }

    public void setSqlString(String sqlString) {
        this.sqlString = sqlString;
    }

    @Override
    public String toString() {
        return "Categorys [categoryId=" + categoryId + ", parentId=" + parentId + ", categoryCode="
                + categoryCode + ", categoryName=" + categoryName + ", status=" + status
                + ", sortno=" + sortno + ", categoryDesc=" + categoryDesc + ", createTime="
                + createTime + ", modifTime=" + modifTime + ", modifUser=" + modifUser
                + ", channel=" + channel + ", execSql=" + execSql + ", isPhy=" + isPhy
                + ", sqlString=" + sqlString + "]";
    }

}
