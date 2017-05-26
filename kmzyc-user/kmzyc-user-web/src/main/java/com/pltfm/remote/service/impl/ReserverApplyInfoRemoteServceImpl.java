package com.pltfm.remote.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.kmzyc.user.remote.service.ReserverApplyInfoRemoteServce;
import com.pltfm.app.dao.ReserverApplyInfoDAO;
import com.pltfm.app.dao.ReserverInfoDAO;
import com.pltfm.app.vobject.ReserverApplyInfo;
import com.pltfm.app.vobject.ReserverInfo;
import com.pltfm.sys.util.DatetimeUtil;

@Component(value = "reserverApplyInfoRemoteServce")
public class ReserverApplyInfoRemoteServceImpl implements ReserverApplyInfoRemoteServce {
  // 日志类
  Logger log = LoggerFactory.getLogger(this.getClass());
  // 预备金申请记录dao
  @Resource(name = "reserverApplyInfoDAO")
  private ReserverApplyInfoDAO reserverApplyInfoDAO;
  // 预备金账户dao
  @Resource(name = "reserverInfoDAO")
  private ReserverInfoDAO reserverInfoDAO;

  // 添加申请记录
  public void insertSelective(ReserverApplyInfo record) throws Exception {
    if (record != null) {
      // 1开通 直接添加记录
      // 2变更 根据预备金账户id获取申请变更当前的预备金情况
      // 添加创建时间
      Date date = DatetimeUtil.getCalendarInstance().getTime();
      record.setApplyDate(date);
      // 查询预备金申请记录
      List<ReserverApplyInfo> reserverApplyInfo = reserverApplyInfoDAO.selectByPrimaryKey(record);
      Boolean blean = false;
      for (ReserverApplyInfo ApplyInfo : reserverApplyInfo) {
        // 存在待审核的记录时修改
        if (ApplyInfo.getStatus() == 1) {
          record.setApplyNotesId(ApplyInfo.getApplyNotesId());
          record.setOriginalLimit(ApplyInfo.getOriginalLimit());
          reserverApplyInfoDAO.updateByPrimaryKey(record);
          blean = true;
          break;
        }
      }
      if (blean != true) {
        if (record.getApplyType() == 2) {
          ReserverInfo reserverInfo = new ReserverInfo();
          reserverInfo.setReserveId(record.getReserveId());
          reserverInfo = reserverInfoDAO.selectByPrimaryKey(reserverInfo);
          record.setOriginalLimit(reserverInfo.getTotalLimit());
          record.setReserveId(reserverInfo.getReserveId());
        }
        // 默认状态为待审核1
        record.setStatus((short) 1);
        reserverApplyInfoDAO.insertSelective(record);
      }
    } else {
      log.error("传入对象为空");
      throw new Exception("参数为空");
    }
  }

  // 根据申请记录id查询申请记录
  public ReserverApplyInfo selectByPrimaryKey(BigDecimal applyNotesId) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }


}
