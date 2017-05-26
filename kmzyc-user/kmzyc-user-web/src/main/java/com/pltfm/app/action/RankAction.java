package com.pltfm.app.action;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.service.BnesCustomerTypeService;
import com.pltfm.app.service.PersonalityInfoService;
import com.pltfm.app.service.RankService;
import com.pltfm.app.util.ConfigureUtils;
import com.pltfm.app.util.ListUtils;
import com.pltfm.app.util.Token;
import com.pltfm.app.vobject.BnesCustomerTypeQuery;
import com.pltfm.app.vobject.Rank;
import com.pltfm.sys.model.SysUser;

@Component(value = "rankAction")
@Scope(value = "prototype")
public class RankAction extends BaseAction implements ModelDriven {
    private static final long serialVersionUID = -579941341825593085L;
    @Resource(name = "rankService")
    private RankService rankService;
    @Resource(name = "bnesCustomerTypeService")
    private BnesCustomerTypeService bnesCustomerTypeService;
    @Resource(name = "personalityInfoService")
    private PersonalityInfoService personalityInfoService;
    private Rank rank;
    private Integer n_rId;// 头衔id
    private List<String> rankIds;// 头衔id
    private String rank_Id;// 头衔id


    /**
     * 头衔信息集合
     */
    private List<Rank> RankList;
    /**
     * 分页类
     */
    private Page page;
    /**
     * 客户类别集合
     **/
    private List customerTypeList;

    /**
     * 客户类别主键
     **/
    private String customerId;

    /**
     * 验证编号
     **/
    private String checkCode;
    /**
     * 验证头衔
     **/
    private String checRarkName;

    /**
     * 客户类别主键
     **/
    private int customer_Ids;
    /**
     * 经验值
     **/
    private String score_Max;


    /**
     * 头衔信息列表
     */
    public String queryPageList() {
        try {
            if (page == null) {
                page = new Page();
            }
            operateCustomerType();
            // 设置查询条件默认值 如果有二级 则保证一级二级为默认选中
            if (rank != null && rank.getCustomer_son_id() != null) {
                customerId = String.valueOf(rank.getCustomerTypeId());
                rank.setCustomerTypeId(rank.getCustomer_son_id());
                page = rankService.searchPageByVo(page, rank);
                rank.setCustomerTypeId(Integer.valueOf(customerId));
            } else {
                page = rankService.searchPageByVo(page, rank);
            }
            return "pageSuccess";
        } catch (Exception e) {
            e.printStackTrace();
            this.addActionError(ConfigureUtils.getMessageConfig("rank.query.fail"));
            return "queryFail";
        }
    }

    /**
     * 进入添加头衔信息页面
     */
    public String operateAdd() {
        try {
            String codes;
            rank = rankService.maxRankiId(1);
            if (rank != null) {
                String code = rank.getCode();
                int count = Integer.parseInt(code.substring(6)) + 1;
                codes = "RANKA_" + count;
            } else {
                codes = "RANKA_1";
            }
            rank = new Rank();
            rank.setCode(codes);
            operateCustomerType();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "addSuccess";
    }

    /**
     * 异步获取某类客户生成编号
     */
    public void ajaxOperateCode() {
        try {
            String codes;
            int customer_Id = Integer.parseInt(customerId);
            rank = rankService.maxRankiId(customer_Id);
            if (rank != null) {
                String code = rank.getCode();
                int count = Integer.parseInt(code.substring(6)) + 1;
                codes = code.substring(0, 6) + count;
            } else {
                if (customer_Id == 1) {
                    codes = "RANKA_1";
                } else if (customer_Id == 2) {
                    codes = "RANKB_1";
                } else if (customer_Id == 4) {
                    codes = "RANKC_1";
                } else if (customer_Id == 5) {
                    codes = "RANKD_1";
                } else {
                    codes = "RANKE_1";
                }
                // codes="RANKA_1";
            }
            super.writeJson(codes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Token
    public String operateSave() {
        try {
            // 执行更新客户头衔
            if (rank != null) {
                if (rank.getRankId() != null) {
                    operateUpdate();
                    // 执行添加客户头衔
                } else {
                    SysUser sysuser = (SysUser) session.get("sysUser");
                    rank.setCreated(sysuser.getUserId());
                    if (rank.getCustomer_son_id() != null) {
                        rank.setCustomerTypeId(rank.getCustomer_son_id());
                    }
                    rank.setCreateDate(new Date());
                    rankService.insertRank(rank);
                    this.addActionMessage(ConfigureUtils.getMessageConfig("rank.add.success"));
                }
            }
            rank = new Rank();
            return this.queryPageList();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            this.addActionError(ConfigureUtils.getMessageConfig("rank.operate.fail"));
            return this.operateAdd();
        }
    }

    // 删除头衔信息
    @Token
    public String operateDetele() {
        try {
            rankService.deleteRank(rankIds);
            this.addActionMessage(ConfigureUtils.getMessageConfig("rank.delete.success"));
            return this.queryPageList();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            this.addActionError(ConfigureUtils.getMessageConfig("rank.delete.fail"));
            return this.queryPageList();
        }
    }

    @Token
    public String operateUpdate() {
        try {
            if (rank.getCustomer_son_id() != null) {
                rank.setCustomerTypeId(rank.getCustomer_son_id());
            }
            SysUser sysuser = (SysUser) session.get("sysUser");
            rank.setModified(sysuser.getUserId());
            rank.setModifyDate(new Date());
            rankService.udpateRank(rank);
            this.addActionMessage(ConfigureUtils.getMessageConfig("rank.update.success"));
            rank = new Rank();
            return this.queryPageList();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            this.addActionError(ConfigureUtils.getMessageConfig("rank.operate.fail"));
            return operateAdd();
        }
    }

    /**
     * 进入头衔信息详细
     */
    public String operateEdit() {
        try {
            operateCustomerType();
            rank = rankService.getRankInfoId(n_rId);
            BnesCustomerTypeQuery customer = bnesCustomerTypeService
                    .findBnesCustomerTypeDOByPrimaryKey(rank.getCustomerTypeId());
            // rankService.getCustomerTypeIdKey(rank.getCustomerTypeId());
            if (customer != null && customer.getParentId() != 0) {
                rank.setCustomerTypeId(customer.getParentId());
                rank.setCustomer_son_id(customer.getCustomerTypeId());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            this.addActionError(ConfigureUtils.getMessageConfig("rank.query.fail"));
            return operateAdd();
        }
        return "editSuccess";
    }

    /**
     * 查询头衔编号
     */
    public void checkCode() {
        if (checkCode != null || checkCode != "") {
            try {
                List list = rankService.selectCode(checkCode);// 传入头编号，查询是否有记录
                int value = 0;
                if (ListUtils.isNotEmpty(list)) {// list是否有值
                    if (!rank_Id.equals("")) {
                        Rank rank = (Rank) list.get(0);
                        if (rank.getRankId()
                                .equals(Integer.valueOf(rank_Id))) {// 查询出的头衔id与更新时的传入的id比较
                            value = 0;
                        } else {
                            value = 1;
                        }
                    } else {
                        value = 1;
                    }
                }
                super.writeJson(value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 查询头衔名称
     */
    public void checRarkName() {
        if (checRarkName != null || checRarkName != "") {
            try {
                List list = rankService.selectRankName(checRarkName);// 传入头衔名称，查询是否有记录
                int value = 0;
                if (ListUtils.isNotEmpty(list)) {// list是否有值
                    if (!rank_Id.equals("")) {
                        Rank rank = (Rank) list.get(0);
                        if (rank.getRankId()
                                .equals(Integer.valueOf(rank_Id))) {// 查询出的头衔id与更新时的传入的id比较
                            value = 0;
                        } else {
                            value = 1;
                        }
                    } else {
                        value = 1;
                    }
                }
                super.writeJson(value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 查询客户类型
     */
    public void operateCustomerType() {
        try {
            customerTypeList = bnesCustomerTypeService.findParentList(Integer.valueOf("0"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 异步调用客户类别信息
     */
    public void ajaxOperateCustomerType() {
        try {

            super.writeJson(bnesCustomerTypeService.findParentList(Integer.valueOf(customerId)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 异步获取某类客户最大
     */
    public void ajaxOperateExpend() {
        try {
            Integer number = rankService.getScoreMaxs(Integer.valueOf(customerId));
            super.writeJson(number);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询是否已经头衔是否被使用
     */
    public void ajaxcountRank() {
        try {
            Integer number = personalityInfoService.countRank(Integer.valueOf(rank_Id));
            super.writeJson(number);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 异步获取某类客户是否有下个等级头衔
     */
    public void ajaxRank() {
        try {
            int scoreMax = 0;
            rank = rankService
                    .selectRank(Integer.parseInt(score_Max) + 1, Integer.parseInt(customerId));
            if (rank != null) {
                if (rank.getScoreMin() != null) {
                    if (!rank.getRankId().equals(Integer.valueOf(rank_Id))) {
                        scoreMax = rank.getScoreMin();
                    }
                }

            }
            super.writeJson(scoreMax);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public RankService getRankService() {
        return rankService;
    }

    public void setRankService(RankService rankService) {
        this.rankService = rankService;
    }

    public List<String> getRankIds() {
        return rankIds;
    }

    public void setRankIds(List<String> rankIds) {
        this.rankIds = rankIds;
    }

    public List<Rank> getRankList() {
        return RankList;
    }

    public void setRankList(List<Rank> rankList) {
        RankList = rankList;
    }

    @Override
    public Page getPage() {
        return page;
    }

    public String getChecRarkName() {
        return checRarkName;
    }

    public void setChecRarkName(String checRarkName) {
        this.checRarkName = checRarkName;
    }

    @Override
    public void setPage(Page page) {
        this.page = page;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    @Override
    public Object getModel() {
        rank = new Rank();
        return rank;
    }

    public Integer getN_rId() {
        return n_rId;
    }

    public void setN_rId(Integer nRId) {
        n_rId = nRId;
    }

    public BnesCustomerTypeService getBnesCustomerTypeService() {
        return bnesCustomerTypeService;
    }

    public void setBnesCustomerTypeService(BnesCustomerTypeService bnesCustomerTypeService) {
        this.bnesCustomerTypeService = bnesCustomerTypeService;
    }

    public List getCustomerTypeList() {
        return customerTypeList;
    }

    public void setCustomerTypeList(List customerTypeList) {
        this.customerTypeList = customerTypeList;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }

    public String getRank_Id() {
        return rank_Id;
    }

    public void setRank_Id(String rankId) {
        rank_Id = rankId;
    }

    public Integer getCustomer_Ids() {
        return customer_Ids;
    }

    public PersonalityInfoService getPersonalityInfoService() {
        return personalityInfoService;
    }

    public void setPersonalityInfoService(PersonalityInfoService personalityInfoService) {
        this.personalityInfoService = personalityInfoService;
    }

    public void setCustomer_Ids(Integer customerIds) {
        customer_Ids = customerIds;
    }

    public String getScore_Max() {
        return score_Max;
    }

    public void setScore_Max(String scoreMax) {
        score_Max = scoreMax;
    }
}
