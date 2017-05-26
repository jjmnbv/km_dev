package com.pltfm.app.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.app.dao.DiffAdjDAO;
import com.pltfm.app.dao.SellerSettlementDAO;
import com.pltfm.app.entities.DiffAdj;
import com.pltfm.app.entities.DiffAdjCriteria;
import com.pltfm.app.entities.DiffAdjExample;
import com.pltfm.app.entities.HurlFare;
import com.pltfm.app.entities.HurlProduct;
import com.pltfm.app.entities.SellerSettlement;
import com.pltfm.app.entities.SellerSettlementCriteria;
import com.pltfm.app.entities.SettlementRefund;
import com.pltfm.app.service.DiffAdjService;
import com.pltfm.app.util.CreateExcelUtil;
import com.pltfm.sys.util.ErrorCode;

@SuppressWarnings("unchecked")
@Service("diffAdjService")
public class DiffAdjServiceImpl extends BaseService implements DiffAdjService {

  private static final Logger log = Logger.getLogger(DiffAdjServiceImpl.class);

  @Resource
  private DiffAdjDAO diffAdjDAO;

  @Resource
  private SellerSettlementDAO sellerSettlementDAO;

  @Override
  public int countByExample(DiffAdjExample example) throws ServiceException {
    try {
      return diffAdjDAO.countByExample(example);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INTER_DIFF_ADJ_ERROR, e.getMessage());
    }
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
  public int deleteByExample(DiffAdjExample example) throws ServiceException {
    try {
      return diffAdjDAO.deleteByExample(example);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INTER_DIFF_ADJ_ERROR, e.getMessage());
    }
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
  public Long insert(DiffAdj record) throws ServiceException {
    try {
      return diffAdjDAO.insertSelective(record);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INTER_DIFF_ADJ_ERROR, e.getMessage());
    }
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
  public Long insertSelective(DiffAdj record) throws ServiceException {
    try {
      return diffAdjDAO.insertSelective(record);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INTER_DIFF_ADJ_ERROR, e.getMessage());
    }
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
  public List selectByExample(DiffAdjExample example) throws ServiceException {
    try {
      return diffAdjDAO.selectByExample(example);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INTER_DIFF_ADJ_ERROR, e.getMessage());
    }
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
  public int updateByExample(DiffAdj record, DiffAdjExample example) throws ServiceException {
    try {
      return diffAdjDAO.updateByExample(record, example);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INTER_DIFF_ADJ_ERROR, e.getMessage());
    }
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
  public List selectDiffAdjDetail(DiffAdjExample example) throws ServiceException {
    try {
      List list = diffAdjDAO.selectByExample(example);

      if (list == null || list.isEmpty()) {
        return list;
      }
      // 取用户名
      for (int i = 0; i < list.size(); i++) {
        DiffAdj info = (DiffAdj) list.get(i);

        info.setUserName(getUserName(info.getOperaterId() + ""));

        info.setCalcSettmentPeriod(getSettlementFlag(info.getCalcSettmentNo()));
      }

      return list;
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INTER_DIFF_ADJ_ERROR, e.getMessage());
    }
  }

  /**
   * 取结算期
   * 
   * @param sno
   * @return
   */
  private String getSettlementFlag(String sno) {
    SellerSettlementCriteria criteria = new SellerSettlementCriteria();
    criteria.setSettlementNo(sno);
    SellerSettlement info = null;

    try {
      info = sellerSettlementDAO.getSettlementByNo(criteria);
    } catch (Exception e1) {
      log.error("查询结算单。。。", e1);
    }
    if (info != null)
      return info.getSettlementPeriod();
    else
      return null;
  }

  private String getUserName(String userId) {
    String username = null;
    if (StringUtils.isEmpty(userId)) {
      return username;
    }

    DiffAdj record = new DiffAdj();
    record.setOperaterId(Long.valueOf(userId));

    try {
      username = diffAdjDAO.getUserName(record);
    } catch (Exception e) {
      e.printStackTrace();
      log.error("查询用户名出错。。。", e);
    }

    return username;
  }

  @Override
  public List<DiffAdj> selectDiffAdjList(DiffAdjCriteria criteria) throws ServiceException {
    try {

      List<DiffAdj> list = null;
      list = diffAdjDAO.selectDiffAdjList(criteria);

      // 查询结果为空则查询第一页数据返回
      if (list.size() == 0 && criteria.getStartIndex() != 0) {
        criteria.setEndIndex(criteria.getEndIndex() - criteria.getStartIndex());
        criteria.setStartIndex(0);
        list = diffAdjDAO.selectDiffAdjList(criteria);
      }
      return list;

    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INTER_DIFF_ADJ_ERROR, e.getMessage());
    }
  }

  @Override
  public int selectDiffAdjSize(DiffAdjCriteria criteria) throws ServiceException {
    try {
      return diffAdjDAO.selectDiffAdjSize(criteria);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INTER_DIFF_ADJ_ERROR, e.getMessage());
    }
  }

  /**
   * 导出excel
   * 
   * @param settlementNo 结算单
   * @param type 类型 1:妥投商品明细，2:运费明细,3:退款明细,4:差异调整明细
   * @return
   * @throws ServiceException
   */
  @Override
  public String exportExcel(String settlementNo, int type, List data) throws ServiceException {
    List<String> path = createFilePath(settlementNo, type);
    if (null != path && path.size() == 1) {
      return path.get(0);
    }
    // List data = null;
    /*
     * switch (type) { case 1:
     * 
     * break; case 2:
     * 
     * break; case 3:
     * 
     * break; case 4:
     * 
     * break; }
     */
    if (null != data && !data.isEmpty()) {
      HSSFWorkbook wb = new HSSFWorkbook();// excel
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

      String sheetName = null;
      List<String> lables = new ArrayList<String>();
      switch (type) {
        case 1:
          sheetName = "妥投商品明细";
          lables.add("订单编号");
          lables.add("SKU编号");
          lables.add("商品标题");
          lables.add("数量");
          lables.add("实收小计");
          lables.add("佣金比例");
          lables.add("佣金");
          lables.add("推广服务费");
          lables.add("应结金额");
          lables.add("订单完成时间");
          break;
        case 2:
          sheetName = "运费明细";
          lables.add("订单编号");
          lables.add("收货人");
          lables.add("手机号码");
          lables.add("收货地址");
          lables.add("运费金额");
          lables.add("订单完成时间");
          break;
        case 3:
          sheetName = "退款明细";
          lables.add("订单编号");
          lables.add("退换货单号");
          lables.add("SKU编号");
          lables.add("商品标题");
          lables.add("服务类型");
          lables.add("数量");
          lables.add("退款金额");
          lables.add("佣金比例");
          lables.add("佣金");
          lables.add("退款返推广服务费");
          lables.add("应结退款");
          lables.add("退款完成时间");
          break;
        case 4:
          sheetName = "差异调整明细";
          lables.add("发生账期");
          lables.add("调整人");
          lables.add("调整说明");
          lables.add("调整金额");
          lables.add("调整发生时间");
          break;
      }
      // sheet
      HSSFSheet sheet = wb.createSheet(sheetName);
      int len = lables.size();
      for (int i = 0; i < len; i++) {
        sheet.setColumnWidth(i, 4500);
      }

      // 标题
      CreateExcelUtil excel = new CreateExcelUtil(wb, sheet);
      int rowIndex = 0;// 行下标
      HSSFRow row = sheet.createRow(rowIndex++);
      for (int i = 0; i < len; i++) {
        excel.cteateCell(wb, row, i, cellStyle, lables.get(i));
      }
      // 内容
      switch (type) {
        case 1:
          int sumNum = 0;
          double shishou = 0.0,
          yongjin = 0.0,
          tgpvSum = 0.0, // 推广服务费
          accounts = 0.0;
          for (HurlProduct hp : (List<HurlProduct>) data) {
            sumNum += hp.getCommodityNumber();
            shishou += hp.getReceiveSub().doubleValue();
            yongjin += hp.getCommission().doubleValue();
            tgpvSum +=hp.getCommodityPvSum().doubleValue();
            accounts += hp.getSettleAccounts().doubleValue();
            row = sheet.createRow(rowIndex++);
            Cell cell = row.createCell(0);
            cell.setCellStyle(cellStyleCon);
            getStringValue(cell, hp.getOrderCode());
            cell = row.createCell(1);
            cell.setCellStyle(cellStyleCon);
            getStringValue(cell, hp.getSkuNo());
            cell = row.createCell(2);
            cell.setCellStyle(cellStyleCon);
            getStringValue(cell, hp.getProductTile());
            cell = row.createCell(3);
            cell.setCellStyle(cellStyleCon);
            getStringValue(cell, hp.getCommodityNumber());
            cell = row.createCell(4);
            cell.setCellStyle(cellStyleCon);
            getStringValue(cell, hp.getReceiveSub());
            cell = row.createCell(5);
            cell.setCellStyle(cellStyleCon);
            getStringValue(cell, hp.getCommissionRate() == null ? '0' : hp.getCommissionRate()
                .doubleValue() * 100 + "%");
            cell = row.createCell(6);
            cell.setCellStyle(cellStyleCon);
            getStringValue(cell, hp.getCommission());
            cell = row.createCell(7);
            cell.setCellStyle(cellStyleCon);
            getStringValue(cell, hp.getCommodityPvSum());
            cell = row.createCell(8);
            cell.setCellStyle(cellStyleCon);
            getStringValue(cell, hp.getSettleAccounts());
            cell = row.createCell(9);
            cell.setCellStyle(cellStyleCon);
            getStringValue(cell, hp.getSettlementTime());
          }
          int rowTotal1 = rowIndex++;
          row = sheet.createRow(rowTotal1);
          Cell cellTotal1 = row.createCell(0);
          cellTotal1.setCellStyle(cellStyleCon);
          cellTotal1.setCellValue("合计");
          cellTotal1 = row.createCell(1);
          cellTotal1.setCellStyle(cellStyleCon);
          cellTotal1 = row.createCell(2);
          CellRangeAddress address1 = new CellRangeAddress(rowTotal1, rowTotal1, 0, 2);
          sheet.addMergedRegion(address1);
          cellTotal1.setCellStyle(cellStyleCon);
          cellTotal1 = row.createCell(3);
          cellTotal1.setCellValue(sumNum);
          cellTotal1.setCellStyle(cellStyleCon);
          cellTotal1 = row.createCell(4);
          cellTotal1.setCellStyle(cellStyleCon);
          cellTotal1.setCellValue(shishou);
          cellTotal1 = row.createCell(5);
          cellTotal1.setCellStyle(cellStyleCon);
          cellTotal1 = row.createCell(6);
          cellTotal1.setCellStyle(cellStyleCon);
          cellTotal1.setCellValue(yongjin);
          cellTotal1 = row.createCell(7);
          cellTotal1.setCellValue(tgpvSum);
          cellTotal1 = row.createCell(8);
          cellTotal1.setCellValue(accounts);
          cellTotal1.setCellStyle(cellStyleCon);
          cellTotal1 = row.createCell(9);
          cellTotal1.setCellStyle(cellStyleCon);

          break;
        case 2:
          Double fare = 0.0;
          for (HurlFare hr : (List<HurlFare>) data) {
            fare += hr.getFare().doubleValue();
            row = sheet.createRow(rowIndex++);
            Cell cell = row.createCell(0);
            cell.setCellStyle(cellStyleCon);
            getStringValue(cell, hr.getOrderCode());
            cell = row.createCell(1);
            cell.setCellStyle(cellStyleCon);
            getStringValue(cell, hr.getConsigneeName());
            cell = row.createCell(2);
            cell.setCellStyle(cellStyleCon);
            getStringValue(cell, hr.getConsigneeMobile());
            cell = row.createCell(3);
            cell.setCellStyle(cellStyleCon);
            getStringValue(cell, hr.getConsigneeAddr());
            cell = row.createCell(4);
            cell.setCellStyle(cellStyleCon);
            getStringValue(cell, hr.getFare());
            cell = row.createCell(5);
            cell.setCellStyle(cellStyleCon);
            getStringValue(cell, hr.getSettlementTime());
          }
          // 计算总计金额
          int rowTotal2 = rowIndex++;
          row = sheet.createRow(rowTotal2);
          Cell cellTotal2 = row.createCell(0);
          cellTotal2.setCellStyle(cellStyleCon);
          cellTotal2.setCellValue("合计");
          cellTotal2 = row.createCell(1);
          cellTotal2.setCellStyle(cellStyleCon);
          cellTotal2 = row.createCell(2);
          cellTotal2.setCellStyle(cellStyleCon);
          cellTotal2 = row.createCell(3);
          cellTotal2.setCellStyle(cellStyleCon);
          CellRangeAddress address2 = new CellRangeAddress(rowTotal2, rowTotal2, 0, 3);
          sheet.addMergedRegion(address2);
          cellTotal2 = row.createCell(4);
          cellTotal2.setCellStyle(cellStyleCon);
          cellTotal2.setCellValue(fare);
          cellTotal2 = row.createCell(5);
          cellTotal2.setCellStyle(cellStyleCon);
          break;
        case 3:
          sumNum = 0;
          Double refundMoney = 0.0;
          yongjin = 0.0;
          Double refundPvSum = 0.00;
          accounts = 0.0;
          for (SettlementRefund sr : (List<SettlementRefund>) data) {
            sumNum += sr.getDealNumber();
            refundMoney += sr.getRefundMoney().doubleValue();
            yongjin += sr.getCommission().doubleValue();
            accounts += sr.getSettleAccounts().doubleValue();
            refundPvSum += sr.getRefundCommodityPvSum().doubleValue();
            row = sheet.createRow(rowIndex++);
            Cell cell = row.createCell(0);
            cell.setCellStyle(cellStyleCon);
            getStringValue(cell, sr.getOrderCode());
            cell = row.createCell(1);
            cell.setCellStyle(cellStyleCon);
            getStringValue(cell, sr.getOrderAlterCode());
            cell = row.createCell(2);
            cell.setCellStyle(cellStyleCon);
            getStringValue(cell, sr.getSkuNo());
            cell = row.createCell(3);
            cell.setCellStyle(cellStyleCon);
            getStringValue(cell, sr.getProductTitle());
            cell = row.createCell(4);
            cell.setCellStyle(cellStyleCon);
            // 服务类型1：退款 2：换货 3：不退货退款
            String serviceType = "";
            switch (sr.getServiceType()) {
              case 1:
                serviceType = "退款";
                break;
              case 2:
                serviceType = "换货";
                break;
              case 3:
                serviceType = "不退货退款";
                break;
            }
            getStringValue(cell, serviceType);
            cell = row.createCell(5);
            cell.setCellStyle(cellStyleCon);
            getStringValue(cell, sr.getDealNumber());
            cell = row.createCell(6);
            cell.setCellStyle(cellStyleCon);
            getStringValue(cell, sr.getRefundMoney());
            cell = row.createCell(7);
            cell.setCellStyle(cellStyleCon);
            getStringValue(cell, sr.getCommissionRate() == null ? '0' : sr.getCommissionRate()
                .doubleValue() * 100 + "%");
            cell = row.createCell(8);
            cell.setCellStyle(cellStyleCon);
            getStringValue(cell, sr.getCommission());
            cell = row.createCell(9);
            cell.setCellStyle(cellStyleCon);
            getStringValue(cell, sr.getRefundCommodityPvSum());
            cell = row.createCell(10);
            cell.setCellStyle(cellStyleCon);
            getStringValue(cell, sr.getSettleAccounts());
            cell = row.createCell(11);
            cell.setCellStyle(cellStyleCon);
            getStringValue(cell, sr.getSettlementTime());
          }
          int rowTotal3 = rowIndex++;
          row = sheet.createRow(rowTotal3);
          Cell cellTotal3 = row.createCell(0);
          cellTotal3.setCellStyle(cellStyleCon);
          cellTotal3.setCellValue("合计");
          cellTotal3 = row.createCell(1);
          cellTotal3.setCellStyle(cellStyleCon);
          cellTotal3 = row.createCell(2);
          cellTotal3.setCellStyle(cellStyleCon);
          cellTotal3 = row.createCell(3);
          cellTotal3.setCellStyle(cellStyleCon);
          cellTotal3 = row.createCell(4);
          CellRangeAddress address = new CellRangeAddress(rowTotal3, rowTotal3, 0, 3);
          sheet.addMergedRegion(address);
          cellTotal3.setCellStyle(cellStyleCon);
          cellTotal3 = row.createCell(5);
          cellTotal3.setCellValue(sumNum);
          cellTotal3.setCellStyle(cellStyleCon);
          cellTotal3 = row.createCell(6);
          cellTotal3.setCellValue(refundMoney);
          cellTotal3.setCellStyle(cellStyleCon);
          cellTotal3 = row.createCell(7);
          cellTotal3.setCellStyle(cellStyleCon);
          cellTotal3 = row.createCell(8);
          cellTotal3.setCellValue(yongjin);
          cellTotal3.setCellStyle(cellStyleCon);
          cellTotal3 = row.createCell(9);
          cellTotal3.setCellStyle(cellStyleCon);
          cellTotal3.setCellValue(refundPvSum);
          cellTotal3 = row.createCell(10);
          cellTotal3.setCellStyle(cellStyleCon);
          cellTotal3.setCellValue(accounts);
          cellTotal3 = row.createCell(11);
          cellTotal3.setCellStyle(cellStyleCon);
          break;
        case 4:
          double diffSum = 0.0;
          for (DiffAdj da : (List<DiffAdj>) data) {
            diffSum += da.getAdjMoney().doubleValue();
            row = sheet.createRow(rowIndex++);
            Cell cell = row.createCell(0);
            cell.setCellStyle(cellStyleCon);
            getStringValue(cell, da.getCurrSettmentPeriod());
            cell = row.createCell(1);
            cell.setCellStyle(cellStyleCon);
            getStringValue(cell, da.getUserName());
            cell = row.createCell(2);
            cell.setCellStyle(cellStyleCon);
            getStringValue(cell, da.getAdjDetail());
            cell = row.createCell(3);
            cell.setCellStyle(cellStyleCon);
            getStringValue(cell, da.getAdjMoney());
            cell = row.createCell(4);
            cell.setCellStyle(cellStyleCon);
            getStringValue(cell, da.getAdjTime());
          }
          // 计算总计金额
          int rowTotal4 = rowIndex++;
          row = sheet.createRow(rowTotal4);
          Cell cellTotal4 = row.createCell(0);
          cellTotal4.setCellStyle(cellStyleCon);
          cellTotal4.setCellValue("合计");
          cellTotal4 = row.createCell(1);
          cellTotal4.setCellStyle(cellStyleCon);
          cellTotal4 = row.createCell(2);
          cellTotal4.setCellStyle(cellStyleCon);
          CellRangeAddress address4 = new CellRangeAddress(rowTotal4, rowTotal4, 0, 2);
          sheet.addMergedRegion(address4);
          cellTotal4 = row.createCell(3);
          cellTotal4.setCellStyle(cellStyleCon);
          cellTotal4.setCellValue(diffSum);
          cellTotal2 = row.createCell(4);
          cellTotal2.setCellStyle(cellStyleCon);
          cellTotal2 = row.createCell(5);
          cellTotal2.setCellStyle(cellStyleCon);
          break;

      }
      FileOutputStream fileOut;
      try {
        fileOut = new FileOutputStream(path.get(0));
        wb.write(fileOut);
        fileOut.close();
        return path.get(1);
      } catch (Exception e) {
        throw new ServiceException(0, e.getMessage());
      }
    }
    return null;
  }

  /**
   * 生成文件路径
   * 
   * @param settlementNo
   * @param type
   * @return 返回长度为1表示已生成
   */
  private List<String> createFilePath(String settlementNo, int type) {
    List<String> liststr = new ArrayList<String>();
    StringBuffer sb = new StringBuffer();
    sb.append("/report/").append(settlementNo).append('_').append(type).append('_')
        .append(Calendar.getInstance().getTimeInMillis()).append(".xls");
    String excelPath = ConfigurationUtil.getString("path");
    String visitPath =
        ConfigurationUtil.getString("visitPath");
    File file = new File(excelPath + sb.toString());
    if (file.exists()) {
      liststr.add(visitPath + sb.toString());
    } else {
      if (!file.getParentFile().exists()) {
        file.getParentFile().mkdirs();
      }
      liststr.add(file.getPath());
      liststr.add(visitPath + sb.toString());
    }
    return liststr;
  }

  /**
   * 查询文件路径
   * 
   * @param settlementNo
   * @param type
   * @return 返回
   */
  @Override
  public String getFilePath(String settlementNo, int type) {
    String filePath = null;
    StringBuffer sb = new StringBuffer();
    sb.append("/report/").append(settlementNo).append('_').append(type).append('_')
        .append(Calendar.getInstance().getTimeInMillis()).append(".xls");
    String excelPath = ConfigurationUtil.getString("path");
    String visitPath =
        ConfigurationUtil.getString("visitPath");
    File file = new File(excelPath + sb.toString());
    if (file.exists()) {
      filePath = visitPath + sb.toString();
      // file.delete();
    }
    return filePath;
  }

  @Override
public String diffAdjExport(String sno, String filePath, DiffAdjCriteria diffAdjCriteria)
      throws ServiceException {
    diffAdjCriteria.setCalcSettmentNo(sno);
    int count = selectDiffAdjSize(diffAdjCriteria);
    List<DiffAdj> dataList = null;
    diffAdjCriteria.setStartIndex(0);
    diffAdjCriteria.setEndIndex(count + 1);
    dataList = selectDiffAdjList(diffAdjCriteria);
    filePath = exportExcel(sno, 4, dataList);
    return filePath;
  }

  @Override
  public Map selectSumDiff(DiffAdjCriteria diffAdjCriteria) throws ServiceException {
    try {
      return diffAdjDAO.selectSumDiff(diffAdjCriteria);
    } catch (Exception e) {
      throw new ServiceException(e.hashCode(), e.getMessage());
    }
  }


}
