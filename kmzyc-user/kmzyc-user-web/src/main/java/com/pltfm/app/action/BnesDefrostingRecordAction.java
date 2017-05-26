package com.pltfm.app.action;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.service.BnesFrozenRecordService;
import com.pltfm.app.util.ConfigureUtils;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.app.util.Token;
import com.pltfm.app.vobject.BnesFrozenRecord;
import com.pltfm.app.vobject.LoginInfo;
import com.pltfm.sys.model.SysUser;

/**
 * 解冻登录帐户ACTION
 * 
 */
@Component(value = "bnesDefrostingRecordAction")
@Scope(value = "prototype")
public class BnesDefrostingRecordAction extends BaseAction implements ModelDriven {
    private static final long serialVersionUID = -579941341825593085L;
    @Resource(name = "bnesFrozenRecordService")
    private BnesFrozenRecordService bnesFrozenRecordService;
    /** 冻结帐户信息实体 */
    private BnesFrozenRecord bnesFrozenRecord;
    /** 分页类 */
    private Page page;
    /** 冻结帐户主键ID集合 **/
    private List<Integer> frozenRecordIds;
    /** 冻结帐户主键ID **/
    private Integer FRId;
    /** 登录帐户* */
    private List<LoginInfo> liginList;
    /** 账号状态 **/
    private Integer status;
    /** 修改_冻结 **/
    private Integer updateType;
    /** 公共页面参数 **/
    private Integer showType;

    /**
     * 登录帐户解冻信息列表
     * 
     * @return
     */
    public String pageThawList() {
        try {
            String isMenu = this.getRequest().getParameter("isMenu");
            if ("true".equals(isMenu)) {
                return "pageThawListMenu";
            }
            // bnesFrozenRecord.setStatus(1);
            page = bnesFrozenRecordService.searchPageByVo(page, bnesFrozenRecord);
            return "pageThawList";
        } catch (Exception e) {
            this.addActionError(ConfigureUtils.getMessageConfig("thaw.query.fail"));
            return "queryThawFail";
        }
    }

    /**
     * 删除解冻登录帐户信息
     * 
     * @return
     */
    @Token
    public String detele() {
        try {
            bnesFrozenRecordService.delete(frozenRecordIds);
            this.addActionMessage(ConfigureUtils.getMessageConfig("frozen.delete.success"));
            return this.pageThawList();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            this.addActionError(ConfigureUtils.getMessageConfig("frozen.delete.fail"));
            return this.pageThawList();
        }
    }

    /**
     * 更新冻结登录帐户信息
     * 
     * @return
     */
    @Token
    public String update() {
        try {
            SysUser sysuser = (SysUser) session.get("sysUser");
            bnesFrozenRecord.setModified(sysuser.getUserId());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String ly_time = sdf.format(new java.util.Date());
            bnesFrozenRecord.setOperatorDate(DateTimeUtils.parseTimestamp(ly_time));
            bnesFrozenRecordService.update(bnesFrozenRecord);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "saveSuccess";
    }

    /**
     * 解冻结登录账号
     */
    @Token
    public String frozen() {
        try {
            SysUser sysuser = (SysUser) session.get("sysUser");
            bnesFrozenRecord.setOperator(sysuser.getUserId());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String ly_time = sdf.format(new java.util.Date());
            bnesFrozenRecord.setOperatorDate(DateTimeUtils.parseTimestamp(ly_time));
            bnesFrozenRecord.setStatus(1);
            bnesFrozenRecordService.updateStatus(bnesFrozenRecord, 1);
            String name = bnesFrozenRecord.getUsername();
            this.addActionMessage(ConfigureUtils.getMessageConfig("frozen.thaw.success"));
            bnesFrozenRecord = new BnesFrozenRecord();
            if (showType != null) {
                bnesFrozenRecord.setUsername(name);
            }
            return this.pageThawList();
        } catch (SQLException e) {
            e.printStackTrace();
            this.addActionError(ConfigureUtils.getMessageConfig("frozen.thaw.fail"));
            return pageThawList();
        }
    }

    public Integer getFRId() {
        return FRId;
    }

    public void setFRId(Integer fRId) {
        FRId = fRId;
    }

    public Integer getUpdateType() {
        return updateType;
    }

    public void setUpdateType(Integer updateType) {
        this.updateType = updateType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<LoginInfo> getLiginList() {
        return liginList;
    }

    public void setLiginList(List<LoginInfo> liginList) {
        this.liginList = liginList;
    }

    public BnesFrozenRecordService getBnesFrozenRecordService() {
        return bnesFrozenRecordService;
    }

    public void setBnesFrozenRecordService(BnesFrozenRecordService bnesFrozenRecordService) {
        this.bnesFrozenRecordService = bnesFrozenRecordService;
    }

    public BnesFrozenRecord getBnesFrozenRecord() {
        return bnesFrozenRecord;
    }

    public void setBnesFrozenRecord(BnesFrozenRecord bnesFrozenRecord) {
        this.bnesFrozenRecord = bnesFrozenRecord;
    }

    @Override
    public Page getPage() {
        return page;
    }

    @Override
    public void setPage(Page page) {
        this.page = page;
    }

    public List<Integer> getFrozenRecordIds() {
        return frozenRecordIds;
    }

    public void setFrozenRecordIds(List<Integer> frozenRecordIds) {
        this.frozenRecordIds = frozenRecordIds;
    }

    @Override
    public Object getModel() {
        bnesFrozenRecord = new BnesFrozenRecord();
        return bnesFrozenRecord;
    }

    public Integer getShowType() {
        return showType;
    }

    public void setShowType(Integer showType) {
        this.showType = showType;
    }
}
