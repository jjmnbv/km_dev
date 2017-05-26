package com.pltfm.app.action;

import java.io.OutputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.dataobject.BnesAcctTransListDO;
import com.pltfm.app.map.TransactionTypeEnumMap;
import com.pltfm.app.service.AccountInfoService;
import com.pltfm.app.service.BnesAcctTransListService;
import com.pltfm.app.util.CreateExcelUtil;
import com.pltfm.app.vobject.AccountInfo;
import com.pltfm.app.vobject.BnesAcctTransListQuery;

@Component(value = "acctTrationListAction")
@Scope(value = "prototype")
public class AcctTrationListAction extends BaseAction {

    /**
   * 
   */
    private static final long serialVersionUID = 4702954802850347339L;

    /**
     * 交易记录action
     * 
     * @author beary
     * @since 2013-07-15
     */
    private BnesAcctTransListQuery bnesAcctTransListQuery;
    @Resource(name = "bnesAcctTransListService")
    private BnesAcctTransListService bnesAcctTransListService;
    private List<BnesAcctTransListQuery> list;
    private BnesAcctTransListDO bnesAcctTransListDO;
    private List<String> levelId;
    private Page page;
    /** 公共页面参数 **/
    private Integer showType;
    private Integer loginId;
    /**
     * 账户信息业务逻辑接口
     */
    @Resource(name = "accountInfoService")
    private AccountInfoService accountInfoService;

    /**
     * @throws Exception 查询交易记录
     * 
     * @param @return String @exception
     */
    public String showTrationList() throws Exception {
        if (showType != null) {
            AccountInfo in = new AccountInfo();
            in.setN_LoginId(loginId);
            AccountInfo accountInfo = accountInfoService.showAccountInfo(in);
            if (null == bnesAcctTransListQuery) {
                bnesAcctTransListQuery = new BnesAcctTransListQuery();
            }
            if (accountInfo != null) {
                bnesAcctTransListQuery.setAccountId(accountInfo.getN_AccountId());
                bnesAcctTransListQuery.setAccountLogin(accountInfo.getAccountLogin());
            }
        }
        if (page == null) {
            page = new Page();
        }
        // 将交易流水的类型map放入request域中
        super.getRequest().setAttribute("transactionTypeEnumMap", TransactionTypeEnumMap.getMap());
        page = bnesAcctTransListService.findListByPageExample(page, bnesAcctTransListQuery);
        return "trationlist";
    }



    /***
     * 
     * 导出交易记录报表
     * 
     * @return
     */

    public String exportTrationReport() {
        try {
            List<BnesAcctTransListQuery> list =
                    bnesAcctTransListService.queryAllBnesAcctTransList(bnesAcctTransListQuery);
            List<BnesAcctTransListQuery> epVoList = new ArrayList<BnesAcctTransListQuery>();
            for (int i = 0; i < list.size(); i++) {
                BnesAcctTransListQuery epVo = list.get(i);
                epVoList.add(epVo);
            }
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setHeader("Content-disposition", "attachment;  filename=data.xls");// 设定输出文件头
            response.setContentType("application/vnd.ms-excel");// 定义输出类型
            int rowNumEx = 0;// 数据行下标
            HSSFWorkbook wb = new HSSFWorkbook();// excel
            // 地址样式 水平居中 垂直居中 默认字体大小 自动换行
            HSSFCellStyle cellStyleAddr = wb.createCellStyle();
            cellStyleAddr.setAlignment(CellStyle.ALIGN_CENTER);
            cellStyleAddr.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            cellStyleAddr.setWrapText(true);

            // 列名样式 水平居中 垂直居中 12px出题黑楷体文字
            HSSFCellStyle cellStyle = wb.createCellStyle();
            cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            Font font = wb.createFont();
            font.setFontHeightInPoints((short) 12);
            font.setFontName("楷体");
            font.setBoldweight(Font.BOLDWEIGHT_BOLD);
            font.setColor(HSSFColor.BLACK.index);
            cellStyle.setFont(font);
            // 数据样式 水平居中 垂直居中 默认字体大小
            HSSFCellStyle cellStyleCon = wb.createCellStyle();
            cellStyleCon.setAlignment(CellStyle.ALIGN_CENTER);
            cellStyleCon.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            // 小标题样式 水平居中 垂直居中 12px出题黑楷体文字
            HSSFCellStyle cellStyleItem = wb.createCellStyle();
            cellStyleItem.setAlignment(CellStyle.ALIGN_LEFT);
            cellStyleItem.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            Font fontItem = wb.createFont();
            fontItem.setFontHeightInPoints((short) 12);
            fontItem.setFontName("楷体");
            fontItem.setColor(HSSFColor.BLACK.index);
            cellStyleItem.setFont(fontItem);
            rowNumEx = 0;
            HSSFSheet delivery = wb.createSheet("交易记录");
            delivery.setColumnWidth(0, 3000);
            delivery.setColumnWidth(1, 6000);
            delivery.setColumnWidth(2, 6000);
            delivery.setColumnWidth(3, 6000);
            delivery.setColumnWidth(4, 6000);
            delivery.setColumnWidth(5, 6000);
            CreateExcelUtil excel = new CreateExcelUtil(wb, delivery);
            // 标题行
            HSSFRow row = delivery.createRow(rowNumEx);
            rowNumEx++;
            StringBuilder strB = new StringBuilder();
            strB.append("交易记录:");
            // strB.append("交易记录详");
            excel.cteateCell(wb, row, 0, cellStyleItem, strB.toString());
            // 时间行
            HSSFRow rowtime = delivery.createRow(rowNumEx);
            rowNumEx++;
            Date dDate = new Date();
            DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String reTime = format2.format(dDate);
            excel.cteateCell(wb, rowtime, 0, cellStyleItem, reTime);
            // 空行
            rowNumEx++;
            // 数据列表头行
            HSSFRow rowtitle = delivery.createRow(rowNumEx);
            rowNumEx++;
            excel.cteateCell(wb, rowtitle, 0, cellStyle, "序列号");
            excel.cteateCell(wb, rowtitle, 1, cellStyle, "账户号");
            excel.cteateCell(wb, rowtitle, 2, cellStyle, "交易流水号");
            excel.cteateCell(wb, rowtitle, 3, cellStyle, "交易金额");
            excel.cteateCell(wb, rowtitle, 4, cellStyle, "交易前账号金额");
            excel.cteateCell(wb, rowtitle, 5, cellStyle, "交易后账号金额");
            excel.cteateCell(wb, rowtitle, 6, cellStyle, "交易内容");
            excel.cteateCell(wb, rowtitle, 7, cellStyle, "交易类型");
            excel.cteateCell(wb, rowtitle, 8, cellStyle, "交易状态");
            excel.cteateCell(wb, rowtitle, 9, cellStyle, "外部单号");
            excel.cteateCell(wb, rowtitle, 10, cellStyle, "交易对象");
            excel.cteateCell(wb, rowtitle, 11, cellStyle, "交易日期");
            // 数据列表行
            HSSFRow rowData;
            final DecimalFormat df = new DecimalFormat("#,##0.00");
            for (int i = 0; i < epVoList.size(); i++) {
                rowData = delivery.createRow(rowNumEx);
                rowNumEx++;
                BnesAcctTransListQuery BnesAcctTransListVo = epVoList.get(i);
                excel.cteateCell(wb, rowData, 0, cellStyleItem, "" + (i + 1));
                excel.cteateCell(wb, rowData, 1, cellStyleItem,
                        BnesAcctTransListVo.getAccountLogin());
                excel.cteateCell(wb, rowData, 2, cellStyleItem,
                        BnesAcctTransListVo.getAccountNumber());
                excel.cteateCell(wb, rowData, 3, cellStyleItem,
                        df.format(BnesAcctTransListVo.getMoneyAmount()));
                excel.cteateCell(wb, rowData, 4, cellStyleItem,
                        df.format(BnesAcctTransListVo.getBeforeAmount()));
                excel.cteateCell(wb, rowData, 5, cellStyleItem,
                        df.format(BnesAcctTransListVo.getAfterAmount()));
                excel.cteateCell(wb, rowData, 6, cellStyleItem, BnesAcctTransListVo.getContent());

                if (BnesAcctTransListVo.getType() != null) {
                    if (BnesAcctTransListVo.getType().intValue() == 1) {
                        excel.cteateCell(wb, rowData, 7, cellStyleItem, "在线充值");
                    } else if (BnesAcctTransListVo.getType().intValue() == 2) {
                        excel.cteateCell(wb, rowData, 7, cellStyleItem, "后台充值");
                    } else if (BnesAcctTransListVo.getType().intValue() == 3) {
                        excel.cteateCell(wb, rowData, 7, cellStyleItem, "余额支付");
                    } else if (BnesAcctTransListVo.getType().intValue() == 4) {
                        excel.cteateCell(wb, rowData, 7, cellStyleItem, "取消订单");
                    } else if (BnesAcctTransListVo.getType().intValue() == 5) {
                        excel.cteateCell(wb, rowData, 7, cellStyleItem, "订单退款");
                    } else if (BnesAcctTransListVo.getType().intValue() == 6) {
                        excel.cteateCell(wb, rowData, 7, cellStyleItem, "取现");
                    } else {
                        excel.cteateCell(wb, rowData, 7, cellStyleItem, "");
                    }
                }

                if (BnesAcctTransListVo.getStatus() != null
                        && BnesAcctTransListVo.getStatus().intValue() == 1) {
                    excel.cteateCell(wb, rowData, 8, cellStyleItem, "成功");
                } else {
                    excel.cteateCell(wb, rowData, 8, cellStyleItem, "失败");
                }
                excel.cteateCell(wb, rowData, 9, cellStyleItem, BnesAcctTransListVo.getOtherOrder());

                if (BnesAcctTransListVo.getTrasObject() != null) {
                    if (BnesAcctTransListVo.getTrasObject().intValue() == 1) {
                        excel.cteateCell(wb, rowData, 10, cellStyleItem, "订单系统");
                    } else if (BnesAcctTransListVo.getTrasObject().intValue() == 2) {
                        excel.cteateCell(wb, rowData, 10, cellStyleItem, "易宝");
                    } else if (BnesAcctTransListVo.getTrasObject().intValue() == 3) {
                        excel.cteateCell(wb, rowData, 10, cellStyleItem, "支付宝");
                    } else {
                        excel.cteateCell(wb, rowData, 10, cellStyleItem, "");
                    }
                } else {
                    excel.cteateCell(wb, rowData, 10, cellStyleItem, "");
                }

                excel.cteateCell(wb, rowData, 11, cellStyleItem,
                        format2.format(BnesAcctTransListVo.getCreateDate()));
            }
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public List<BnesAcctTransListQuery> getList() {
        return list;
    }

    public void setList(List<BnesAcctTransListQuery> list) {
        this.list = list;
    }

    public List<String> getLevelId() {
        return levelId;
    }

    public void setLevelId(List<String> levelId) {
        this.levelId = levelId;
    }

    public BnesAcctTransListQuery getBnesAcctTransListQuery() {
        return bnesAcctTransListQuery;
    }

    public void setBnesAcctTransListQuery(BnesAcctTransListQuery bnesAcctTransListQuery) {
        this.bnesAcctTransListQuery = bnesAcctTransListQuery;
    }

    public BnesAcctTransListService getBnesAcctTransListService() {
        return bnesAcctTransListService;
    }

    public void setBnesAcctTransListService(BnesAcctTransListService bnesAcctTransListService) {
        this.bnesAcctTransListService = bnesAcctTransListService;
    }

    public BnesAcctTransListDO getBnesAcctTransListDO() {
        return bnesAcctTransListDO;
    }

    public void setBnesAcctTransListDO(BnesAcctTransListDO bnesAcctTransListDO) {
        this.bnesAcctTransListDO = bnesAcctTransListDO;
    }

    public Integer getShowType() {
        return showType;
    }

    public void setShowType(Integer showType) {
        this.showType = showType;
    }

    public AccountInfoService getAccountInfoService() {
        return accountInfoService;
    }

    public void setAccountInfoService(AccountInfoService accountInfoService) {
        this.accountInfoService = accountInfoService;
    }

    public Integer getLoginId() {
        return loginId;
    }

    public void setLoginId(Integer loginId) {
        this.loginId = loginId;
    }

}
