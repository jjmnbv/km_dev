package com.pltfm.app.service.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.sys.util.RedisCacheUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pltfm.app.dao.ProdAppraiseDAO;
import com.pltfm.app.enums.AuditStatus;
import com.pltfm.app.enums.YesOrNoStatus;
import com.pltfm.app.service.ProdAppraiseService;
import com.pltfm.app.vobject.ProdAppraise;
import com.pltfm.app.vobject.ProdAppraiseExample;
import com.pltfm.app.vobject.ProdAppraiseExample.Criteria;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;
import redis.clients.jedis.JedisCluster;

/**
 * 产品评价业务实现
 *
 * @author tanyunxing
 */
@Service("prodAppraiseService")
public class ProdAppraiseServiceImpl implements ProdAppraiseService {

    private static Logger logger = LoggerFactory.getLogger(ProdAppraiseServiceImpl.class);

    @Resource
    private JedisCluster jedisCluster;

    @Resource
    private ProdAppraiseDAO prodAppraiseDao;

    @Override
    public void searchByPage(Page page, ProdAppraise prodApp) throws ServiceException {
        int pageIndex = page.getPageNo();
        if (pageIndex == 0)
            pageIndex = 1;
        int pageSize = page.getPageSize();
        int skip = (pageIndex - 1) * pageSize;
        int max = pageSize;

        ProdAppraiseExample exm = new ProdAppraiseExample();
        Criteria c = exm.createCriteria();
        if (StringUtils.isNotBlank(prodApp.getSerchType())) {//回复状态
            if ("0".equals(prodApp.getSerchType())) {
                exm.setReplyContent(Long.parseLong("0"));
            }
            if ("1".equals(prodApp.getSerchType())) {
                exm.setReplyContent(Long.parseLong("1"));
                exm.setReplyStatus(Long.parseLong("0"));//已回复但是为待审核
            }
            if ("2".equals(prodApp.getSerchType())) {
                exm.setReplyContent(Long.parseLong("1"));
                exm.setReplyStatus(Long.parseLong("1"));//已回复但是为审核通过
            }
            if ("3".equals(prodApp.getSerchType())) {
                exm.setReplyContent(Long.parseLong("1"));
                exm.setReplyStatus(Long.parseLong("2"));//已回复但是为审核没有通过
            }
        }
        if (StringUtils.isNotBlank(prodApp.getSerchType1())) {//追加状态
            if ("0".equals(prodApp.getSerchType1())) {//没有追加
                exm.setAddtoContent(Long.parseLong("0"));
            }
            if ("1".equals(prodApp.getSerchType1())) {
                exm.setAddtoContent(Long.parseLong("1"));
                exm.setCheckStatus1(Long.parseLong("0"));//已追评但是为待审核
            }
            if ("2".equals(prodApp.getSerchType1())) {
                exm.setAddtoContent(Long.parseLong("1"));
                exm.setCheckStatus1(Long.parseLong("1"));//已追评但是为审核通过
            }
            if ("3".equals(prodApp.getSerchType1())) {
                exm.setAddtoContent(Long.parseLong("1"));
                exm.setCheckStatus1(Long.parseLong("2"));//已追评但是为审核没有通过
            }
        }
        if (StringUtils.isNotBlank(prodApp.getCustName())) {
            c.andCustNameLike("%" + prodApp.getCustName().trim() + "%");
        }
        if (prodApp.getProductSku() != null && StringUtils.isNotBlank(prodApp.getProductSku().getProduct().getProductTitle())) {
            exm.setProductSku(prodApp.getProductSku());
        }
        if (StringUtils.isNotBlank(prodApp.getSatisficing())) {
            c.andSatisficingEqualTo(prodApp.getSatisficing());
        }
        if (prodApp.getCheckStatus() != null && !"-1".equals(prodApp.getCheckStatus().toString())) {
            exm.setCheckStatus(prodApp.getCheckStatus());
        }
        if (prodApp.getStartAppDate() != null) {
            c.andAppraiseDateGreaterThanOrEqualTo(prodApp.getStartAppDate());
        }
        if (prodApp.getEndAppDate() != null) {
            Date endAppDate = DateUtils.addDays(prodApp.getEndAppDate(), 1);
            c.andAppraiseDateLessThanOrEqualTo(endAppDate);
        }
        if (prodApp.getStartBuyDate() != null) {
            c.andProdBuyDateGreaterThanOrEqualTo(prodApp.getStartBuyDate());
        }
        if (prodApp.getEndBuyDate() != null) {
            Date endBuyDate = DateUtils.addDays(prodApp.getEndBuyDate(), 1);
            c.andProdBuyDateLessThanOrEqualTo(endBuyDate);
        }
        exm.setOrderByClause(" c.CHECK_STATUS,APPRAISE_DATE desc");

        try {
            int count = prodAppraiseDao.countByExample(exm);
            List<ProdAppraise> list = prodAppraiseDao.selectByExample(exm, skip, max);

            list.stream().filter(pa -> StringUtils.isNotBlank(pa.getProductTitle())).forEach(pa -> {
                pa.setProductTitle(pa.getProductTitle().replaceAll(" ", "&nbsp;"));
            });
            page.setDataList(list);
            page.setRecordCount(count);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public ProdAppraise findByPrimaryKey(Long prodAppId) throws ServiceException {
        try {
            return prodAppraiseDao.selectByPrimaryKey(prodAppId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public int updateCheckStatus(ProdAppraise record) throws ServiceException {
        int count = 0;
        try {
            /*if (record.getCheckStatus() != null) {
                if (record.getCheckStatus().toString().equals(AuditStatus.AUDIT.getStatus())) {
                    if (YesOrNoStatus.YES.getStatus().equals(record.getElite().toString())) {
                        // 规则编号
                        String ruleCode = "RU0013";
                        // 0----扣减积分 1---增加积分
                        Integer scoreType = 1;
                        // 登录账号
                        String loginAccount = record.getCustName();

                        Map<String, String> paramsMap = new HashMap<String, String>();

					int result = userGrowingService.updateUserScoreInfo(ruleCode, scoreType, loginAccount, paramsMap);
                    if(result != 1){
						logger.error("评价审核通过送积分出现异常！");
					}
                    }
                }
            }*/
            count = prodAppraiseDao.updateByPrimaryKeySelective(record);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        return count;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void deleteByPrimaryKey(Long[] prodAppId) throws ServiceException {
        try {
            prodAppraiseDao.deleteByPrimaryKeyBatch(prodAppId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void searchReplyPage(Page page, ProdAppraise prodApp) throws ServiceException {
        int pageIndex = page.getPageNo();
        if (pageIndex == 0)
            pageIndex = 1;
        int pageSize = page.getPageSize();
        int skip = (pageIndex - 1) * pageSize;
        int max = pageSize;

        ProdAppraiseExample exm = new ProdAppraiseExample();
        Criteria c = exm.createCriteria();
        exm.setCheckStatus(Short.parseShort("1"));
        if (prodApp.getProductSku() != null
                && StringUtils.isNotBlank(prodApp.getProductSku().getProduct().getProductTitle())) {
            exm.setProductSku(prodApp.getProductSku());
        }
        if (StringUtils.isNotBlank(prodApp.getSatisficing())) {
            c.andSatisficingEqualTo(prodApp.getSatisficing());
        }
        if (prodApp.getStartAppDate() != null) {
            c.andAppraiseDateGreaterThanOrEqualTo(prodApp.getStartAppDate());
        }
        if (prodApp.getEndAppDate() != null) {
            Date endAppDate = DateUtils.addDays(prodApp.getEndAppDate(), 1);
            c.andAppraiseDateLessThanOrEqualTo(endAppDate);
        }
        if (StringUtils.isNotBlank(prodApp.getReplyContent())) {
            exm.setReplyContent(Long.parseLong(prodApp.getReplyContent()));
        }
        exm.setOrderByClause(" c.APPRAISE_DATE desc,POINT ");

        try {
            int count = prodAppraiseDao.countByExample(exm);
            List<ProdAppraise> list = prodAppraiseDao.selectByExample(exm, skip, max);
            list.stream().filter(pa -> StringUtils.isNotBlank(pa.getProductTitle())).forEach(pa -> {
                pa.setProductTitle(pa.getProductTitle().replaceAll(" ", "&nbsp;"));
            });
            page.setDataList(list);
            page.setRecordCount(count);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void saveProductAppraise(ProdAppraise prodApp) throws ServiceException {
        try {
            String[] satisficing = ConfigurationUtil.getString("Satisficing").split(",");
            String flag = ConfigurationUtil.getString("appraiseAutoAudit");
            if ("true".equals(flag)) {
                prodApp.setCheckStatus((short) 1);
            }
            prodApp.setSatisficing(satisficing[prodApp.getPoint() - 1]);
            prodAppraiseDao.insert(prodApp);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void insertDataForExcel(List<ProdAppraise> list) throws ServiceException {
        try {
            int result = prodAppraiseDao.insertDataForExcel(list);
            if (result == 0) throw new ServiceException("数据有错，插入失败！时间：" + new Date());
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<String> findIsExistUserName(List<String> checkUsers) throws ServiceException {
        try {
            return prodAppraiseDao.findIsExistUserName(checkUsers);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Map> queryProductAppraise(Boolean isTimeSort, String sort, Long skuId, int pageNumber) throws ServiceException {
        String key = "ProductAppraise_" + (isTimeSort ? sort : "null") + "_" + (!isTimeSort ? sort : "null")
                + "_" + skuId + "_" + pageNumber;
        List<Map> resultList = null;
        try {
            byte[] btKey = key.getBytes("GBK");
            //1.取缓存
            byte[] result = jedisCluster.get(btKey);
            if (result != null) {
                return (List<Map>) RedisCacheUtil.unserialize(result);
            }

            //2.缓存不存在，查询当前页数下时间和评分的升序和降序的数据并插入缓存中方便下次查询，并缓存24h
            String[] keyArray = {"ProductAppraise_desc_null_" + skuId + "_" + pageNumber,
                    "ProductAppraise_asc_null_" + skuId + "_" + pageNumber,
                    "ProductAppraise_null_desc_" + skuId + "_" + pageNumber,
                    "ProductAppraise_null_asc_" + skuId + "_" + pageNumber};
            Boolean[] isTimeSortArray = {Boolean.TRUE, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE};
            String[] sortArray = {"desc", "asc", "desc", "asc"};
            Map<String, Object> condition = new HashMap<String, Object>();
            condition.put("skuId", skuId);
            condition.put("startIndex", (pageNumber - 1) * 10 + 1);
            condition.put("endIndex", pageNumber * 10);
            for (int index = 0; index < 4; index++) {
                condition.put("sort", sortArray[index]);
                if (isTimeSortArray[index]) {
                    condition.put("time", Boolean.TRUE);
                    condition.remove("point");
                } else {
                    condition.put("point", Boolean.TRUE);
                    condition.remove("time");
                }
                List<Map> appraiseList = prodAppraiseDao.queryProductAppraise(condition);

                //设置当前数据缓存
                if (key.equals(keyArray[index])) {
                    resultList = appraiseList;
                }
                if (CollectionUtils.isNotEmpty(appraiseList)) {
                    jedisCluster.setex(keyArray[index].getBytes(), 86400, RedisCacheUtil.serialize(appraiseList));
                }
            }
        } catch (Exception e) {
            logger.error("查找sku[{}]的评论失败,", new Object[]{skuId, e});
            throw new ServiceException(e);
        }

        return resultList;
    }

    @Override
    public int countProductAppraise(Long skuId) throws ServiceException {
        String pageCountKey = "ProductAppraise_Count_" + skuId;
        int sum = 0;
        try {
            //1.取缓存
            String count = jedisCluster.get(pageCountKey);
            if (StringUtils.isNotBlank(count) && NumberUtils.isNumber(count)) {
                return Integer.parseInt(count);
            }

            //2.查库
            logger.info("没有从缓存中取到skuId[{}]的评论总数，为空。", skuId);
            sum = prodAppraiseDao.countProductAppraise(skuId);
            jedisCluster.setex(pageCountKey, 24 * 60 * 60, "" + sum);
            return sum;
        } catch (Exception e) {
            logger.error("统计当前skuId[{}]下的评论条数失败，", new Object[]{skuId, e});
            throw new ServiceException(e);
        }
    }

}
