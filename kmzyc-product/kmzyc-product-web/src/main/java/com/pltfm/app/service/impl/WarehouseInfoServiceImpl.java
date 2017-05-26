package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.dao.WarehouseInfoDAO;
import com.pltfm.app.enums.WarehouseInfoStatus;
import com.pltfm.app.maps.WarehouseInfoMap;
import com.pltfm.app.maps.WarehouseStatusMap;
import com.pltfm.app.service.StockOutService;
import com.pltfm.app.service.WarehouseInfoService;
import com.pltfm.app.vobject.WarehouseInfo;
import com.pltfm.app.vobject.WarehouseInfoExample;
import com.pltfm.app.vobject.WarehouseInfoExample.Criteria;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;

@Service("warehouseInfoService")
public class WarehouseInfoServiceImpl implements WarehouseInfoService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private WarehouseInfoDAO warehouseInfoDao;

    @Resource
    private StockOutService stockOutService;

    @Override
    public void searchPage(Page page, WarehouseInfo warehouseInfo) throws ServiceException {
        int pageIndex = page.getPageNo();
        if (pageIndex == 0) pageIndex = 1;
        int pageSize = page.getPageSize();
        int skip = (pageIndex - 1) * pageSize;
        int max = pageSize;

        WarehouseInfoExample wie = new WarehouseInfoExample();
        Criteria criteria = wie.createCriteria();
        if (StringUtils.isNotBlank(warehouseInfo.getWarehouseName()))
            criteria.andWarehouseNameLike("%" + warehouseInfo.getWarehouseName().trim() + "%");
        if (StringUtils.isNotBlank(warehouseInfo.getWarehouseNo()))
            criteria.andWarehouseNoLike("%" + warehouseInfo.getWarehouseNo().trim() + "%");
        if (StringUtils.isNotBlank(warehouseInfo.getOverlayAreaId()))
            criteria.andOverlayAreaIdLike("%|" + warehouseInfo.getOverlayAreaId() + "|%");
        if (StringUtils.isNotBlank(warehouseInfo.getMerchantCode())) //所属商家
            criteria.andMerchantIdEqualTo(warehouseInfo.getMerchantCode());
        if (warehouseInfo.getAreaId() != null && warehouseInfo.getAreaId() != 0) //所在区域
            criteria.andAreaIdEqualTo(warehouseInfo.getAreaId());
        if (StringUtils.isNotBlank(warehouseInfo.getStatus())) //仓库状态
            criteria.andStatusEqualTo(warehouseInfo.getStatus());
        wie.setOrderByClause("WAREHOUSE_ID DESC");

        try {
            int count = warehouseInfoDao.countByExample(wie);
            List<WarehouseInfo> warehouseInfoList = warehouseInfoDao.selectByExample(wie, skip, max);
            page.setDataList(warehouseInfoList);
            page.setRecordCount(count);
        } catch (SQLException e) {
            logger.error(" warehouseId:" + warehouseInfo.getWarehouseId() + " searchPage方法出错", e);
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public Long insert(WarehouseInfo warehouseInfo) throws ServiceException {
        Long count = 0l;
        try {
            WarehouseInfoExample wie = new WarehouseInfoExample();
            Criteria criteria = wie.createCriteria();
            if (StringUtils.isNotBlank(warehouseInfo.getWarehouseName()))
                criteria.andWarehouseNameEqualTo(warehouseInfo.getWarehouseName());
            if (warehouseInfo.getAreaId() != null && warehouseInfo.getAreaId() != 0) //所在区域
                criteria.andAreaIdEqualTo(warehouseInfo.getAreaId());

            int num = warehouseInfoDao.countByExample(wie);
            if (num > 0) return 0l;
            count = warehouseInfoDao.insert(warehouseInfo);
        } catch (SQLException e) {
            logger.error(" insert方法出错", e);
            e.printStackTrace();
        }
        setWarehouseMapByKey(count);
        return count;
    }

    @Override
    public WarehouseInfo findWarehouseInfoById(Long id) throws ServiceException {
        try {
            return warehouseInfoDao.selectByPrimaryKey(id);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public boolean updateWarehouseInfoById(WarehouseInfo warehouseInfo) throws ServiceException {
        try {
            int count = warehouseInfoDao.updateByPrimaryKey(warehouseInfo);
            if (count > 0) return true;
        } catch (SQLException e) {
            logger.error(" warehouseId:" + warehouseInfo.getWarehouseId() + " updateWarehouseInfoById方法出错", e);
            e.printStackTrace();
            throw new ServiceException(e);
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public boolean startWarehouse(List<Object> warehouseInfoList) throws ServiceException {
        try {
            int count = warehouseInfoDao.batchUpdateForWarehouseInfo("WAREHOUSE_INFO.startWarehouse",
                    warehouseInfoList);
            if (count < 0) return false;
        } catch (Exception e) {
            logger.error(" warehouseInfoList:" + warehouseInfoList + " startWarehouse方法出错", e);
            throw new ServiceException(e);
        }
        return true;
    }

    public synchronized void setWarehouseMapByEntity(WarehouseInfo warehouseInfo) throws ServiceException {
        if (warehouseInfo == null) return;
        if (warehouseInfo.getWarehouseId() == null
                || StringUtils.isBlank(warehouseInfo.getWarehouseName())
                || StringUtils.isBlank(warehouseInfo.getStatus())) return;

        if (WarehouseInfoStatus.START.getStatus().equals(warehouseInfo.getStatus())) {
            WarehouseInfoMap.setValue(warehouseInfo.getWarehouseId(), warehouseInfo.getWarehouseName() + "["
                    + WarehouseStatusMap.getValue(warehouseInfo.getStatus()) + "]");
            WarehouseInfoMap.setStatusValue(warehouseInfo.getWarehouseId(), warehouseInfo.getWarehouseName());
        } else {
            WarehouseInfoMap.setValue(warehouseInfo.getWarehouseId(), warehouseInfo.getWarehouseName() + "["
                    + WarehouseStatusMap.getValue(warehouseInfo.getStatus()) + "]");
            WarehouseInfoMap.removeStatusValue(warehouseInfo.getWarehouseId());
        }

    }

    @Override
    public synchronized void setWarehouseMap(List<Object> warehouseInfoList, String status)
            throws ServiceException {
        List<WarehouseInfo> warehouseInfoListTmp = null;
        try {
            warehouseInfoListTmp = warehouseInfoDao.getWarehouseInfoByList(warehouseInfoList);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
        if (WarehouseInfoStatus.START.getStatus().equals(status)) {
            for (WarehouseInfo warehouse : warehouseInfoListTmp) {
                WarehouseInfoMap.setValue(warehouse.getWarehouseId(), warehouse.getWarehouseName() + "["
                        + WarehouseStatusMap.getValue(warehouse.getStatus()) + "]");
                WarehouseInfoMap.setStatusValue(warehouse.getWarehouseId(), warehouse.getWarehouseName());
            }
        } else {
            for (WarehouseInfo warehouse : warehouseInfoListTmp) {
                WarehouseInfoMap.setValue(warehouse.getWarehouseId(), warehouse.getWarehouseName() + "["
                        + WarehouseStatusMap.getValue(warehouse.getStatus()) + "]");
                WarehouseInfoMap.removeStatusValue(warehouse.getWarehouseId());
            }
        }
    }

    @Override
    public void setWarehouseMapByKey(Long id) throws ServiceException {
        WarehouseInfo warehouseInfo = null;
        try {
            warehouseInfo = warehouseInfoDao.selectByPrimaryKey(id);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
        if (warehouseInfo != null && WarehouseInfoStatus.START.getStatus().equals(warehouseInfo.getStatus())) {
            WarehouseInfoMap.setValue(warehouseInfo.getWarehouseId(), warehouseInfo.getWarehouseName());
            WarehouseInfoMap.setStatusValue(warehouseInfo.getWarehouseId(), warehouseInfo.getWarehouseName());
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public boolean stopWarehouse(List<Object> warehouseInfoList) throws ServiceException {
        try {
            int count = warehouseInfoDao.batchUpdateForWarehouseInfo("WAREHOUSE_INFO.stopWarehouse", warehouseInfoList);
            if (count < 0) return false;
        } catch (SQLException e) {
            logger.error(" warehouseInfoList:" + warehouseInfoList + " stopWarehouse方法出错", e);
            throw new ServiceException(e);
        }
        return true;
    }

    @Override
    public List<WarehouseInfo> findAllWarehouseInfo(String status) throws ServiceException {
        WarehouseInfoExample example = null;
        if (StringUtils.isNotBlank(status)) {
            example = new WarehouseInfoExample();
            example.createCriteria().andStatusEqualTo(status);
        }
        try {
            return warehouseInfoDao.selectByExample(example);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public ResultMessage checkWarehouseInfoByName(String name) throws ServiceException {
        ResultMessage result = new ResultMessage();
        result.setIsSuccess(true);
        try {
            int count = warehouseInfoDao.checkWarehouseInfoByName(name);
            if (count > 0) {
                result.setIsSuccess(false);
                result.setMessage("仓库名为：" + name + "重名，请修改!");
                return result;
            }
            return result;
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public ResultMessage checkWarehouseInfoForStop(List<Long> warehouseIdList) throws ServiceException {
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.setIsSuccess(true);
        try {
            resultMessage = stockOutService.checkStockOutByWarehouse(warehouseIdList);
            if (!resultMessage.getIsSuccess()) return resultMessage;
        } catch (Exception e) {
            logger.error("停用仓库时，检查与单据的关联关系失败，", e);
            resultMessage.setIsSuccess(false);
            resultMessage.setMessage("系统错误!");
            return resultMessage;
        }
        return resultMessage;
    }

    @Override
    public ResultMessage checkWarehouseNameByModify(String name, Long warehouseId) throws ServiceException {
        ResultMessage result = new ResultMessage();
        result.setIsSuccess(true);
        try {
            List<Long> warehouseIdList = warehouseInfoDao.checkWarehouseNameByModify(name);
            if (warehouseIdList != null && warehouseIdList.size() == 1) {
                //有一个相同名的仓库，但是又不是自己，则重复(此情况在有数据错误时可能出现)
                if (warehouseId.longValue() != warehouseIdList.get(0).longValue()) {
                    result.setIsSuccess(false);
                    result.setMessage("仓库名为：" + name + "重名，请修改!");
                    return result;
                }
            } else if (warehouseIdList != null && warehouseIdList.size() > 1) {
                result.setIsSuccess(false);
                result.setMessage("仓库名为：" + name + "重名，请修改!");
                return result;
            }

            return result;
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

}