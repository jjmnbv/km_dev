package com.pltfm.app.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.dao.MobileCodeInfDAO;
import com.pltfm.app.service.MobileCodeInfService;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.app.vobject.MobileCodeInf;

/**
 * 手机随机码信息业务逻辑类
 * 
 * @author cjm
 * @since 2013-7-10
 */
@Component(value = "mobileCodeInfService")
public class MobileCodeInfServiceImpl implements MobileCodeInfService {
  /**
   * 手机随机码信息DAO接口
   */
  @Resource(name = "mobileCodeInfDAO")
  private MobileCodeInfDAO mobileCodeInfDAO;


  /**
   * 添加手机随机码
   * 
   * @param record 手机随机码实体
   * @return 返回值
   * @throws Exception 异常
   */
  @Override
  public Integer addMobileCodeInf(MobileCodeInf mobileCodeInf) throws Exception {
    Random r = new Random();

    Integer ran = r.nextInt(9999) + 1000;
    String code = ran.toString();
    if (code.length() == 5) {
      code = code.substring(0, code.length() - 1);
    }

    mobileCodeInf.setTattedCode(code);

    Date d = DateTimeUtils.getCalendarInstance().getTime();

    mobileCodeInf.setD_CreateDate(d);
    mobileCodeInf.setN_FailureTimeValue(30);
    mobileCodeInf.setLastSendTattedcodeTime(DateTimeUtils.getDateTime(d));

    return mobileCodeInfDAO.insert(mobileCodeInf);
  }

  /**
   * 根据主键进行删除单条手机随机码
   * 
   * @param n_PersonalityId 手机随机码ID
   * @return 返回值
   * @throws Exception 异常
   */
  @Override
  public Integer deleteByPrimaryKey(List<Integer> nCellPhoneTattedCodeIds) throws Exception {

    return null;
  }



  /**
   * 根据vo条件查询分类信息page
   * 
   * @param pageParam 分页实体
   * @param vo 手机随机码实体
   * @return 返回值
   * @throws Exception
   */
  @Override
  public Page searchPageByVo(Page pageParam, MobileCodeInf vo) throws Exception {
    if (pageParam == null) {
      pageParam = new Page();
    }
    if (vo == null) {
      vo = new MobileCodeInf();
    }
    // 获取客户积分规则总数
    int totalNum = mobileCodeInfDAO.selectCountByVo(vo);
    pageParam.setRecordCount(totalNum);
    // 设置查询开始结束索引
    vo.setSkip(pageParam.getStartIndex());
    vo.setMax(pageParam.getStartIndex() + pageParam.getPageSize());
    pageParam.setDataList(mobileCodeInfDAO.selectPageByVo(vo));

    return pageParam;
  }

  /**
   * 根据主键查询单条手机随机码信息
   * 
   * @param n_PersonalityId 手机随机码ID
   * @return 返回值
   * @throws Exception 异常
   */
  @Override
  public MobileCodeInf selectByPrimaryKey(Integer nCellPhoneTattedCodeId) throws Exception {
    return null;
  }

  /**
   * 修改手机随机码
   * 
   * @param record 手机随机码实体
   * @return 返回值
   * @throws Exception 异常
   */
  @Override
  public Integer updateMobileCodeInf(MobileCodeInf mobileCodeInf) throws Exception {
    return null;
  }

  public MobileCodeInfDAO getMobileCodeInfDAO() {
    return mobileCodeInfDAO;
  }

  public void setMobileCodeInfDAO(MobileCodeInfDAO mobileCodeInfDAO) {
    this.mobileCodeInfDAO = mobileCodeInfDAO;
  }


}
