package com.pltfm.app.action;

import java.io.PrintWriter;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.Action;
import com.pltfm.app.maps.IsValidMap;
import com.pltfm.app.service.OperationAttrService;
import com.pltfm.app.vobject.OperationAttr;
import com.kmzyc.commons.page.Page;

@Controller("operationAttrAction")
@Scope(value = "prototype")
public class OperationAttrAction extends BaseAction {

    String rtnMsg; // 返回的信息

    private String[] delId; // 删除时的brandId

    private Long operationAttrId;

    private OperationAttr attr = new OperationAttr();

    private String repeatName;

    @Resource
    private OperationAttrService operationAttrService;

    public String showList() {
        try {
            super.getRequest().setAttribute("isValidMap", IsValidMap.getMap());
            operationAttrService.searchPage(page, attr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Action.SUCCESS;
    }

    public String delete() {
        try {
            String msg = operationAttrService.delete(delId);
            if (msg == null) {
                rtnMsg = "操作成功！";
            } else {
                rtnMsg = msg + "与产品有关联，操作失败！";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.showList();
    }

    public String gotoAdd() {
        super.getRequest().setAttribute("isValidMap", IsValidMap.getMap());
        return Action.SUCCESS;
    }

    public String add() {
        try {
            operationAttrService.saveOperationAttr(attr);
            rtnMsg = "操作成功！";
        } catch (Exception e) {
            e.printStackTrace();
            rtnMsg = "系统发生错误，操作失败！";
        }
        attr = new OperationAttr();
        return this.showList();
    }

    public String gotoUpdate() {
        super.getRequest().setAttribute("isValidMap", IsValidMap.getMap());
        try {
            attr = operationAttrService.queryOperationAttr(operationAttrId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Action.SUCCESS;
    }

    public String update() {
        try {
            operationAttrService.updateOperationAttr(attr);
            rtnMsg = "操作成功！";
        } catch (Exception e) {
            e.printStackTrace();
            rtnMsg = "系统发生错误，操作失败！";
        }
        attr = new OperationAttr();
        return this.showList();
    }

    public String checkRepeatName() {
        try {
            if (operationAttrService.checkRepeatName(repeatName, operationAttrId)) {
                PrintWriter out = super.getResponse().getWriter();
                out.print("repeat");
                out.flush();
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String[] getDelId() {
        return delId;
    }

    public void setDelId(String[] delId) {
        this.delId = delId;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public OperationAttr getAttr() {
        return attr;
    }

    public void setAttr(OperationAttr attr) {
        this.attr = attr;
    }

    public String getRtnMsg() {
        return rtnMsg;
    }

    public void setRtnMsg(String rtnMsg) {
        this.rtnMsg = rtnMsg;
    }

    public Long getOperationAttrId() {
        return operationAttrId;
    }

    public void setOperationAttrId(Long operationAttrId) {
        this.operationAttrId = operationAttrId;
    }

    public String getRepeatName() {
        return repeatName;
    }

    public void setRepeatName(String repeatName) {
        this.repeatName = repeatName;
    }

}
