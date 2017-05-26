package com.kmzyc.b2b.service.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kmzyc.b2b.service.MemberComplaintService;
import com.kmzyc.user.remote.service.AccountInfoRemoteService;
import com.pltfm.app.vobject.BnesAcctAppealInfo;

// import com.km.framework.common.util.RemoteTool;

/**
 * 建议投诉Service接口
 * 
 * @author luoyi
 * @createDate 2013/10/10
 */
@Service("memberComplaintService")
public class MemberComplaintServiceImpl implements MemberComplaintService {
  // private Logger logger = Logger.getLogger(MemberComplaintServiceImpl.class);
  private static Logger logger = LoggerFactory.getLogger(MemberComplaintServiceImpl.class);

  @Resource
  private AccountInfoRemoteService accountInfoRemoteService;

  /**
   * 保存用户的建议投诉
   * 
   * @param bnesAcctAppealInfo
   * @return
   * @throws SQLException
   */
  @Override
public void saveComplaintsByUser(BnesAcctAppealInfo bnesAcctAppealInfo) throws SQLException {
    try {
//      if (StringUtils.isBlank(bnesAcctAppealInfo.getAppealTitle().trim())) {
//        logger.error("投诉与建议反馈的主题不能为空");
//        throw new RuntimeException("投诉与建议反馈的主题不能为空,保存建议投诉失败.");
//      } else 
      if (StringUtils.isBlank(bnesAcctAppealInfo.getAppealContent())) {
        logger.error("投诉与建议的内容不能为空");
        throw new RuntimeException("投诉与建议的内容不能为空,保存建议投诉失败.");
      }
      // 调用后台客户系统的接口
      // AccountInfoRemoteService accountInfoRemoteService =
      // (AccountInfoRemoteService) RemoteTool.getRemote(Constants.REMOTE_SERVICE_CUSTOMER,
      // "/accountInfoRemoteService");
      int rows = accountInfoRemoteService.insertAcctAppealInfo(bnesAcctAppealInfo);
      if (rows == 0) {
        logger.error("保存用户建议与投诉失败");
        throw new RuntimeException("保存建议投诉失败.");
      }
    } catch (Exception e) {
      logger.error("保存用户建议与投诉失败" + e.getMessage(), e);
      throw new RuntimeException("保存用户的建议投诉失败.");
    }
  }

}
