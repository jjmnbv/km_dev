package com.kmzyc.b2b.service.member.impl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.km.framework.page.Pagination;
import com.kmzyc.b2b.dao.member.ConsultDao;
import com.kmzyc.b2b.dao.member.UserInfoDao;
import com.kmzyc.b2b.model.PersonalityInfo;
import com.kmzyc.b2b.model.User;
import com.kmzyc.b2b.service.member.ConsultService;
import com.kmzyc.b2b.service.member.UserInfoService;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.product.remote.service.ConsultRemoteService;
// import com.km.framework.common.util.RemoteTool;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.app.vobject.Consult;

/**
 * Description:咨询信息服务接口 User: lishiming Date: 13-10-17 下午3:45 Since: 1.0
 */
@Service
public class ConsultServiceImpl implements ConsultService {

    // private static Logger logger = Logger.getLogger(ConsultServiceImpl.class);
    private static Logger logger = LoggerFactory.getLogger(ConsultServiceImpl.class);

    @Resource(name = "consultDaoImpl")
    private ConsultDao consultDao;

    @Resource(name = "userInfoDaoImpl")
    private UserInfoDao userInfoDao;

    @Resource(name = "userInfoServiceImpl")
    private UserInfoService userInfoService;

    @Resource
    private ConsultRemoteService consultRemoteService;

    /**
     * 计算用户某审核状态下的咨询总数
     * 
     * @param userId
     * @param checkStatusList
     * @return
     * @throws SQLException
     */
    @Override
    public Long countConsultByUserId(Long userId, List<Integer> checkStatusList)
            throws SQLException {
        String checkStatusStr = StringUtils.join(checkStatusList, ",");
        logger.info("计算用户某审核状态下的咨询总数:\r\nuserId:" + userId + ";checkStatusList:" + checkStatusStr);
        // 使用客户数据源

        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("userId", userId);
        condition.put("checkStatus", checkStatusStr);
        return (Long) consultDao.findById("Consult.countConsultByUserId", condition);
    }

    /**
     * 保存咨询信息
     * 
     * @throws Exception
     * 
     */
    public Consult saveConsult(String consultType, User user, String consultContent,
            String productSkuId, String productSkuCode) throws Exception {
        // 根据客户登陆id ,查询客户相关信息

        // User userMan =
        // userInfoDao.queryLeveAndNickName(user.getLoginId().intValue());
        Consult consult = new Consult();
        consult.setProductSkuid(Long.parseLong(productSkuId));
        consult.setCustNickname(user.getLoginAccount());
        consult.setCustId(user.getLoginId().intValue());
        //consult.setCustLevel(user.getLevelId().toString());
        consult.setType(Short.valueOf(consultType));
        consult.setConsultContent(consultContent);
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = simple.parse(simple.format(new Date()));
        consult.setConsultDate(now);
        consult.setProductSkucode(productSkuCode);
        // 调用产品的远程接口
        // ConsultRemoteService consultRemote =
        // (ConsultRemoteService) RemoteTool.getRemote(Constants.REMOTE_SERVICE_PRODUCT,
        // "consultService");
        Consult consultResult = consultRemoteService.saveConsult(consult);
        return consultResult;
    }

    /**
     * 
     * 分页查询产品咨询数据
     */
    @Override
    public Pagination findConsultListByPage(Pagination page) throws Exception {
        // 使用客户数据源

        page =
                consultDao.findByPage("Consult.findConsultListByPage",
                        "Consult.countConsultByPage", page);
        // 取得产品的路径
        String productImgServerUrl = ConfigurationUtil.getString("USER_IMG_PATH");
        String type = Constants.SMALL_IMG_SUFFIX;
        for (int i = 0; i < page.getRecordList().size(); i++) {
            PersonalityInfo person =
                    userInfoService.selcetImagById(((com.pltfm.app.vobject.Consult) page
                            .getRecordList().get(i)).getCustId().longValue());

            if (person != null && person.getHeadSculpture() != null) {
                if (person.getHeadSculpture().contains(".")) {
                    int point_index = person.getHeadSculpture().lastIndexOf(".");
                    int image_length = person.getHeadSculpture().length();
                    String image_Path = person.getHeadSculpture().substring(0, point_index);
                    String image_extent =
                            person.getHeadSculpture().substring(point_index, image_length);

                    ((com.pltfm.app.vobject.Consult) page.getRecordList().get(i))
                            .setRemark(productImgServerUrl + image_Path + type + image_extent);
                }
            } else {
                ((com.pltfm.app.vobject.Consult) page.getRecordList().get(i)).setRemark(null);
            }
        }

        return page;
    }

}
