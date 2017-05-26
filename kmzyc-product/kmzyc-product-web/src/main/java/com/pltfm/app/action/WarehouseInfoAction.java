package com.pltfm.app.action;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.pltfm.app.vobject.WarehouseRelation;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.enums.WarehouseInfoStatus;
import com.pltfm.app.maps.WarehouseInfoMap;
import com.pltfm.app.maps.WarehouseStatusMap;
import com.pltfm.app.service.AreaDictService;
import com.pltfm.app.service.ProductStockService;
import com.pltfm.app.service.WarehouseInfoService;
import com.pltfm.app.service.WarehouseRelationService;
import com.pltfm.app.vobject.AreaDict;
import com.pltfm.app.vobject.ProductStock;
import com.pltfm.app.vobject.WarehouseInfo;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;

/**
 * 仓库
 *
 * @author xkj
 */
@Controller(value = "warehouseAction")
@Scope(value = "prototype")
public class WarehouseInfoAction extends BaseAction {

    private Page page;

    private WarehouseInfo warehouseInfo;

    private WarehouseInfo warehouseForSelectPara;

    private Long[] upWarehouseId;

    private String supperArea;

    private Integer areaId1;

    private String msg;

    @Resource
    private WarehouseInfoService warehouseInfoService;

    @Resource
    private WarehouseRelationService warehouseRelationService;

    @Resource
    private ProductStockService productStockService;

    @Resource
    private AreaDictService areaDictService;

    //仓库列表
    public String showList() {
        try {
            if (page == null) page = new Page();
            if (warehouseForSelectPara == null) {
                warehouseForSelectPara = new WarehouseInfo();
            }
            warehouseInfoService.searchPage(page, warehouseForSelectPara);
            getAreaList();
            if (warehouseForSelectPara.getPAreaId() != null && warehouseForSelectPara.getPAreaId() != 0) {
                getCreateList(warehouseForSelectPara.getPAreaId());
            }
            setWarehouseStatusMap();
        } catch (Exception e) {
            logger.error("仓库列表失败，", e);
            return ERROR;
        }
        return SUCCESS;
    }

    public String toWarehouseAdd() {
        getAreaList();
        setWarehouseStatusMap();
        return SUCCESS;
    }

    public String toWarehouseUpdate() {
        try {
            setCheckedId(warehouseInfo.getWarehouseId());
            warehouseInfo = warehouseInfoService.findWarehouseInfoById(warehouseInfo.getWarehouseId());
            areaId1 = areaDictService.findProvinceBySupplierId(warehouseInfo.getAreaId()).getAreaId();
        } catch (Exception e) {
            logger.error("修改仓库失败，", e);
            return ERROR;
        }
        getAreaList();
        setWarehouseStatusMap();
        setCorrespondingDataStatusMap();
        setSystemCodeMap();
        return SUCCESS;
    }

    public String toWarehouseView() {
        try {
            setCheckedId(warehouseInfo.getWarehouseId());
            warehouseInfo = warehouseInfoService.findWarehouseInfoById(warehouseInfo.getWarehouseId());
            supperArea = areaDictService.findProvinceBySupplierId(warehouseInfo.getAreaId()).getAreaName();
        } catch (Exception e) {
            logger.error("查看仓库失败，", e);
            return ERROR;
        }
        getAreaList();
        setWarehouseStatusMap();
        setWarehouseStatusMap();
        setCorrespondingDataStatusMap();
        setSystemCodeMap();
        return SUCCESS;
    }

    //新增仓库信息
    public String addWarehouse() {
        warehouseInfo.setCreateUser(getLoginUserId());
        warehouseInfo.setCreateUserName(getLoginUserName());
        warehouseInfo.setCreateDate(new Date());

        try {
            Long count = warehouseInfoService.insert(warehouseInfo);
            if (count.intValue() < 1) {
                return ERROR;
            }

            WarehouseInfo warehouseInfo = warehouseInfoService.findWarehouseInfoById(count);
            if (warehouseInfo != null) {
                WarehouseInfoMap.setValue(warehouseInfo.getWarehouseId(), warehouseInfo.getWarehouseName()+
                        "["+WarehouseStatusMap.getValue(warehouseInfo.getStatus())+"]");
                if (WarehouseInfoStatus.START.getStatus().equals(warehouseInfo.getStatus())) {
                    WarehouseInfoMap.setStatusValue(warehouseInfo.getWarehouseId(), warehouseInfo.getWarehouseName());
                } else {
                    WarehouseInfoMap.removeStatusValue(warehouseInfo.getWarehouseId());
                }
            }
            this.setMsg("仓库新建成功!");
        } catch (Exception e) {
            logger.error("仓库新建失败，", e);
            return ERROR;
        }
        warehouseInfo = null;
        return showList();
    }

    //修改仓库信息
    public String updateWarehouse() {
        List<Long> warehouseIdList = new ArrayList<Long>();
        StringBuffer msgBuffer = new StringBuffer();
        try {
            if (WarehouseInfoStatus.STOP.getStatus().equals(warehouseInfo.getStatus())) {
                List<ProductStock> stockList = new ArrayList<ProductStock>();
                ProductStock stock = new ProductStock();
                stock.setWarehouseId(warehouseInfo.getWarehouseId());
                stockList.add(stock);
                warehouseIdList.add(warehouseInfo.getWarehouseId());

                //检查仓库与单据的关联
                ResultMessage resultMessage = warehouseInfoService.checkWarehouseInfoForStop(warehouseIdList);
                if (!resultMessage.getIsSuccess()) {
                    warehouseInfo.setStatus(WarehouseInfoStatus.START.getStatus());
                    msgBuffer.append(resultMessage.getMessage());
                }

                if (!productStockService.checkStockByWarehouseList(stockList)) {
                    warehouseInfo.setStatus(WarehouseInfoStatus.START.getStatus());
                    msgBuffer.append(" 仓库关联库存，不能停用!");
                }
            }

            boolean flag = warehouseInfoService.updateWarehouseInfoById(warehouseInfo);
            if (!flag) {
                return ERROR;
            }

            WarehouseRelation warehouseRelation = warehouseInfo.getWarehouseRelation();
            if (warehouseRelation != null) {
                if (warehouseRelation.getWarehouseRelationId() == null) {
                    warehouseRelation.setWarehouseId(warehouseInfo.getWarehouseId());
                    warehouseRelationService.insert(warehouseRelation);
                } else {
                    warehouseRelationService.update(warehouseRelation);
                }
            }
            warehouseInfoService.setWarehouseMapByEntity(warehouseInfo);
            msgBuffer.append(" 仓库信息修改成功!");
            setMsg(msgBuffer.toString());
        } catch (Exception e) {
            logger.error("仓库修改失败，", e);
            return ERROR;
        }

        warehouseInfo = null;
        return showList();
    }

    //启用仓库
    public String startWarehouse() {
        PrintWriter out = null;
        WarehouseInfo infoTmp = null;
        Map jsonMap = new HashMap();
        List<Object> list = new ArrayList<Object>();
        for (Long id : upWarehouseId) {
            infoTmp = new WarehouseInfo();
            infoTmp.setWarehouseId(id);
            list.add(infoTmp);
        }
        boolean flag = false;
        //上架之前需要判断价格是否添加，属性是否添加，SKU是否添加
        try {
            flag = warehouseInfoService.startWarehouse(list);
        } catch (Exception e) {
            logger.error("启用仓库失败，", e);
            jsonMap.put("result", false);
            jsonMap.put("msg", "启用失败！");
            writeJson(jsonMap);
            return null;
        }

        String msg = null;
        if (flag) {
            warehouseInfoService.setWarehouseMap(list, WarehouseInfoStatus.START.getStatus());
            msg = "启用成功！";
        } else {
            msg = "启用失败！";
        }
        jsonMap.put("result", flag);
        jsonMap.put("msg", msg);
        writeJson(jsonMap);
        return null;
    }

    //停用仓库
    public String stopWarehouse() {
        WarehouseInfo infoTmp = null;
        ProductStock stock = null;
        Map jsonMap = new HashMap();
        List<Object> list = new ArrayList<Object>();
        List<ProductStock> stockList = new ArrayList<ProductStock>();
        List<Long> warehouseIdList = new ArrayList<Long>();
        for (Long id : upWarehouseId) {
            infoTmp = new WarehouseInfo();
            infoTmp.setWarehouseId(id);
            list.add(infoTmp);
            stock = new ProductStock();
            stock.setWarehouseId(id);
            stockList.add(stock);
            warehouseIdList.add(id);
        }
        String msg = null;

        try {
            //检查仓库与单据的关联
            ResultMessage resultMessage = warehouseInfoService.checkWarehouseInfoForStop(warehouseIdList);
            if (!resultMessage.getIsSuccess()) {
                msg = resultMessage.getMessage();
                jsonMap.put("result", resultMessage.getIsSuccess());
                jsonMap.put("msg", msg);
                writeJson(jsonMap);
                return null;
            }

            boolean haveDone = productStockService.checkStockByWarehouseList(stockList);
            if (haveDone) {
                haveDone = warehouseInfoService.stopWarehouse(list);
            }
            if (haveDone) {
                warehouseInfoService.setWarehouseMap(list, WarehouseInfoStatus.STOP.getStatus());
                msg = "停用成功！";
            } else {
                msg = "仓库有库存，停用失败！";
            }
            jsonMap.put("result", haveDone);
            jsonMap.put("msg", msg);
        } catch (Exception e) {
            logger.error("关闭仓库失败，", e);
            jsonMap.put("result", false);
            jsonMap.put("msg", "系统错误,停用失败！");
        }
        writeJson(jsonMap);
        return null;
    }

    public String choiceArea() {
        Map jsonMap = new HashMap();
        try {
            Integer id = Integer.valueOf(getRequest().getParameter("id"));
            List<AreaDict> areaList = areaDictService.findCityByProvince(id);
            jsonMap.put("areaList", areaList);
            writeJson(jsonMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String checkWarehouseInfoName() {
        Map jsonMap = new HashMap();
        try {
            String name = getRequest().getParameter("name");
            ResultMessage result = warehouseInfoService.checkWarehouseInfoByName(name);
            jsonMap.put("result", result.getIsSuccess());
            jsonMap.put("msg", result.getMessage());
            writeJson(jsonMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String checkWarehouseNameByModify() {
        Map jsonMap = new HashMap();
        try {
            String name = getRequest().getParameter("name");
            Long id = Long.valueOf(getRequest().getParameter("id"));
            ResultMessage result = warehouseInfoService.checkWarehouseNameByModify(name, id);
            jsonMap.put("result", result.getIsSuccess());
            jsonMap.put("msg", result.getMessage());
            writeJson(jsonMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public WarehouseInfo getWarehouseInfo() {
        return warehouseInfo;
    }

    public void setWarehouseInfo(WarehouseInfo warehouseInfo) {
        this.warehouseInfo = warehouseInfo;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Long[] getUpWarehouseId() {
        return upWarehouseId;
    }

    public void setUpWarehouseId(Long[] upWarehouseId) {
        this.upWarehouseId = upWarehouseId;
    }

    public String getSupperArea() {
        return supperArea;
    }

    public void setSupperArea(String supperArea) {
        this.supperArea = supperArea;
    }

    public Integer getAreaId1() {
        return areaId1;
    }

    public void setAreaId1(Integer areaId1) {
        this.areaId1 = areaId1;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public WarehouseInfo getWarehouseForSelectPara() {
        return warehouseForSelectPara;
    }

    public void setWarehouseForSelectPara(WarehouseInfo warehouseForSelectPara) {
        this.warehouseForSelectPara = warehouseForSelectPara;
    }

}