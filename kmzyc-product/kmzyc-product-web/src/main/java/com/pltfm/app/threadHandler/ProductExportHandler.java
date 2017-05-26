package com.pltfm.app.threadHandler;

import java.text.DateFormat;
import java.util.List;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pltfm.app.vobject.ProductSkuForExport;

import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2016/12/20 8:59
 */
public class ProductExportHandler implements Callable<Boolean> {

    private Logger logger = LoggerFactory.getLogger(ProductExportHandler.class);

    private int sheetIndex;

    private List<ProductSkuForExport> list;

    private DateFormat dateFormat;

    private String[] title;

    private WritableWorkbook workbook;

    public ProductExportHandler(List<ProductSkuForExport> list, DateFormat dateFormat,
                                WritableWorkbook workbook, int sheetIndex, String[] title) {
        this.list = list;
        this.dateFormat = dateFormat;
        this.workbook = workbook;
        this.sheetIndex = sheetIndex;
        this.title = title;
    }

    @Override
    public Boolean call() {
        try {
            WritableSheet sheet = workbook.createSheet("商品信息表" + (sheetIndex + 1), sheetIndex);

            // 标题
            WritableFont font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
            WritableCellFormat cellFormat = new WritableCellFormat(font);
            cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
            cellFormat.setBackground(Colour.YELLOW2);
            for (int i = 0; i < title.length; i++) {
                sheet.addCell(new Label(i, 0, title[i], cellFormat));
            }

            // 内容
            cellFormat = new WritableCellFormat();
            cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
            for (int i = 0; i < list.size(); i++) {
                ProductSkuForExport export = list.get(i);
                sheet.addCell(new Label(0,  i+1, export.getBrandName(), cellFormat));
                sheet.addCell(new Label(1,  i+1, export.getProductName(), cellFormat));
                sheet.addCell(new Label(2,  i+1, export.getProductTitle(), cellFormat));
                sheet.addCell(new Label(3,  i+1, export.getProductSkuDesc(), cellFormat));
                sheet.addCell(new Label(4,  i+1, export.getProductType(), cellFormat));
                sheet.addCell(new Label(5,  i+1, export.getProductNo().toString(), cellFormat));
                sheet.addCell(new Label(6,  i+1, export.getProductSkuCode().toString(), cellFormat));
                sheet.addCell(new Label(7,  i+1, export.getProductSkuStatus(), cellFormat));
                sheet.addCell(new Label(8,  i+1, export.getCostPrice() != null ? export.getCostPrice().toString() : "暂无", cellFormat));
                sheet.addCell(new Label(9,  i+1, export.getMarkPrice() != null ? export.getMarkPrice().toString() : "暂无", cellFormat));
                sheet.addCell(new Label(10, i+1, export.getPrice() != null ? export.getPrice().toString() : "暂无", cellFormat));
                sheet.addCell(new Label(11, i+1, export.getPvValue(), cellFormat));
                sheet.addCell(new Label(12, i+1, export.getStatus(), cellFormat));
                sheet.addCell(new Label(13, i+1, export.getFirstCategoryName(), cellFormat));
                sheet.addCell(new Label(14, i+1, export.getSecondCategoryName(), cellFormat));
                sheet.addCell(new Label(15, i+1, export.getThirdCategoryName(), cellFormat));
                sheet.addCell(new Label(16, i+1, export.getSupplierName(), cellFormat));
                sheet.addCell(new Label(17, i+1, export.getSupplierType(), cellFormat));
                sheet.addCell(new Label(18, i+1, export.getUpTime() != null ? dateFormat.format(export.getUpTime()) : "", cellFormat));
                sheet.addCell(new Label(19, i+1, export.getArchiveTime() != null ? dateFormat.format(export.getArchiveTime()) : "", cellFormat));
                sheet.addCell(new Label(20, i+1, export.getPostil(), cellFormat));
                sheet.addCell(new Label(21, i + 1,
                        "http://www.kmb2b.com/products/" + export.getProductSkuId() + ".html",
                        cellFormat));
            }
        } catch (WriteException e) {
            logger.error("导出商品异常：", e);
        }
        return Boolean.TRUE;
    }
}