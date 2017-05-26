package com.kmzyc.promotion.app.vobject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单系统出库单(远程接口用)
 * 
 * @author luoyi
 * @since 2013/09/02
 */
public class CarryStockOutVO implements Serializable {
	private static final long serialVersionUID = 4408583724092667431L;

	// 明细集合
	List<CarryStockOutDetailVO> detailList = new ArrayList<CarryStockOutDetailVO>();

	private String billNo;// 订单编号
	private String purchaserName;// 收货人名称
	private String purchaserAddrr;// 收货人地址
	private String purchaserTel;// 收货人电话

	public List<CarryStockOutDetailVO> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<CarryStockOutDetailVO> detailList) {
		this.detailList = detailList;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getPurchaserName() {
		return purchaserName;
	}

	public void setPurchaserName(String purchaserName) {
		this.purchaserName = purchaserName;
	}

	public String getPurchaserAddrr() {
		return purchaserAddrr;
	}

	public void setPurchaserAddrr(String purchaserAddrr) {
		this.purchaserAddrr = purchaserAddrr;
	}

	public String getPurchaserTel() {
		return purchaserTel;
	}

	public void setPurchaserTel(String purchaserTel) {
		this.purchaserTel = purchaserTel;
	}

	@Override
	public String toString() {
		return "CarryStockOutVO [detailList=" + detailList + ", billNo=" + billNo + ", purchaserName=" + purchaserName
				+ ", purchaserAddrr=" + purchaserAddrr + ", purchaserTel=" + purchaserTel + "]";
	}

}
