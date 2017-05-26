package com.pltfm.app.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.dao.impl.UserChannelDAOImpl;
import com.pltfm.app.service.UserChannelService;
import com.pltfm.app.vobject.UserChannel;
import com.pltfm.app.vobject.UserChannelExample;
import com.pltfm.sys.bean.BaseBean;

/**
 * 
 * @author tanyunxing
 * 
 */
@SuppressWarnings("unchecked")
@Service("userChannelService")
public class UserChannelServiceImpl extends BaseBean implements UserChannelService {
  private Logger log = Logger.getLogger(OrderDictionaryServiceImpl.class);

  // @Resource
  // private UserChannelDAO userChannelDao;

  @Override
  public List<UserChannel> findByUserId(Long userId) throws Exception {
    UserChannelDAOImpl userChannelDao = new UserChannelDAOImpl(sqlMap);
    UserChannelExample exa = new UserChannelExample();
    exa.createCriteria().andUserIdEqualTo(userId);
    return userChannelDao.selectByExample(exa);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
  public void saveUserChannel(List<UserChannel> userChannelList, List<Long> userIds)
      throws ServiceException {
    int count;
    try {
      UserChannelDAOImpl userChannelDao = new UserChannelDAOImpl(sqlMap);
      List<UserChannelExample> exas = new ArrayList<UserChannelExample>();
      for (Long userId : userIds) {
        UserChannelExample exa = new UserChannelExample();
        exa.createCriteria().andUserIdEqualTo(userId);
        exas.add(exa);
      }
      count = userChannelDao.deleteBatch(exas);
      if (count < 1) {
        throw new ServiceException(0, "保存失败!");
      }
      if (userChannelList.size() > 0) {
        count = userChannelDao.saveBatch(userChannelList);
        if (count < 1) {
          throw new ServiceException(0, "保存失败!");
        }
      }
    } catch (Exception e) {
      log.error(e);
      throw new ServiceException(0, e.getMessage(), e);
    }
  }

}
