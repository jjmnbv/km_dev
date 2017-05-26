package com.kmzyc.product.remote.service.impl;

import com.kmzyc.product.remote.service.SystemDockingService;
import com.pltfm.app.vobject.CarryStockOutVO;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("systemDockingService")
public class SystemDockingServiceImpl implements SystemDockingService {

    //将订单退单数据转换成XML格式数据
    @Override
    public List<String> convertOrderReturnMsgToXml(List<CarryStockOutVO> carryStockOutList, Map<String, String> erpProductSkuCodeMap) {
        List<String> orderXmlStrList = new ArrayList<String>();
//		String nowDate = DateTimeUtils.getDate(new Date());

        Document document = DocumentHelper.createDocument();
        Element root = DocumentHelper.createElement("IMessage");
        document.setRootElement(root);
        Element iMessageHeade = DocumentHelper.createElement("IMessageHeade");
        root.add(iMessageHeade);
        Element messageBasic = DocumentHelper.createElement("MessageBasic");
        iMessageHeade.add(messageBasic);
        for (CarryStockOutVO csov : carryStockOutList) {
            Element billNum = messageBasic.addElement("BILLNUM");//单据编码*(捷科系统会检查编号是否重复)
            billNum.setText(csov.getBillNo());

            Element receiptType = messageBasic.addElement("RECEIPTTYPE");//单据类型*(此节点固定填:SaleIssueBill)
            receiptType.setText("SaleIssueBill");

            Element sourceSystem = messageBasic.addElement("SOURCESYSTEM");//来源系统*(暂定为EB,意为电商 e-Business 的缩写)
            sourceSystem.setText("EB");

            Element targetSystem = messageBasic.addElement("TARGETSYSTEM");//目标系统*(此节点固定填SCM,意为供应链缩写)
            targetSystem.setText("SCM");

            Element createTime = messageBasic.addElement("TIMESTAMP");//创建时间
            createTime.setText(csov.getCreateTime());

            Element messageBody = DocumentHelper.createElement("MessageBody");
            Element data = DocumentHelper.createElement("DATA");
            Element rowHead = DocumentHelper.createElement("ROWHEAD");
            root.add(messageBody);
            messageBody.add(data);
            data.add(rowHead);

            Element number = rowHead.addElement("number");
            number.setText(csov.getBillNo());//单据编码

            Element bizdate = rowHead.addElement("bizdate");
            bizdate.setText(csov.getPayTime());//业务日期(付款日期)

            Element biztype = rowHead.addElement("biztype");
            biztype.setText("301");//业务类型*(此节点固定填:210)

            Element customer = rowHead.addElement("customer");//客商*(电商在供应链系统下单的固定客商编号: 50105230000)
            customer.setText("50105230000");

            Element creator = rowHead.addElement("creator");
            creator.setText("0116001");//创建人*(制单员的OA编号，如电商在供应链下单的人员通常为：张加城)

            Element entry = DocumentHelper.createElement("ENTRY");
            data.add(entry);
            Element row = null;
//			List<CarryStockOutDetailVO> detailList = csov.getDetailList();
//			for(int i=0; i<1 ;i++){
            row = DocumentHelper.createElement("ROW");
            entry.add(row);

            Element warehouse = row.addElement("warehouse");//机构*(0079为电商在供应链下单的固定机构编号)
            warehouse.setText("0079");

            Element amount = row.addElement("amount");//金额，单价
            amount.setText("776");

            Element nontaxamount = row.addElement("nontaxamount");
            nontaxamount.setText("");
            Element taxprice = row.addElement("taxprice");
            taxprice.setText("");
            Element saleprice = row.addElement("saleprice");
            saleprice.setText("");

            Element exp = row.addElement("exp");//到期日期*(对于销售订单，此字段作用不大，写当前日期即可)
            exp.setText("2042-02-25");

            Element lot = row.addElement("lot");
            lot.setText("");

            Element mfg = row.addElement("mfg");//生产日期*(对于销售订单，此字段作用不大，写当前日期即可)
            mfg.setText("2014-10-10");

            Element qty = row.addElement("qty");//数量
            qty.setText("2");

            Element unit = row.addElement("unit");
            unit.setText("");
            Element material = row.addElement("material");
            material.setText("");
            Element producePlace = row.addElement("producePlace");
            producePlace.setText("");

            Element mdmaterial = row.addElement("mdmaterial");//主数据物料编码(商品对应到供应链的主数据编号)，捷科SKU编码
            mdmaterial.setText("50101147573");

            Element reasoncode = row.addElement("reasoncode");
            reasoncode.setText("");
            Element actualprice = row.addElement("actualprice");
            actualprice.setText("");
            Element saleperson = row.addElement("saleperson");
            saleperson.setText("");

            Element ispresent = row.addElement("ispresent");//是否是赠品
            ispresent.setText("0");
//			}
        }

        orderXmlStrList.add(document.asXML());

        return orderXmlStrList;
    }
}
