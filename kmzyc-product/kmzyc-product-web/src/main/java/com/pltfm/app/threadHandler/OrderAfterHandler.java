package com.pltfm.app.threadHandler;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kmzyc.order.remote.OrderAlterChangeStatusRemoteService;
import com.pltfm.app.util.OrderAlterDictionaryEnum;
import com.pltfm.app.vobject.StockOut;

public class OrderAfterHandler implements Runnable {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	private List<StockOut> stockOutList;

	private OrderAlterChangeStatusRemoteService remoteService;
	
    public OrderAfterHandler(List<StockOut> stockOutList, OrderAlterChangeStatusRemoteService remoteService) {
        this.stockOutList = stockOutList;
        this.remoteService = remoteService;
    }

    @Override
	public void run() {
		try{
            if (CollectionUtils.isEmpty(stockOutList)) {
                log.error("订单转退货接口，没有找到列表。");
                return;
            }

			for(StockOut stockOut : stockOutList){
				//调订单转退货接口
                int result = remoteService.changeAlterStatusForProduct(stockOut.getCreateUserName(), stockOut.getBillNo(),
                        Long.valueOf(OrderAlterDictionaryEnum.Propose_Status.ExchangeToReturn.getKey()), null);
                log.info("调订单转退货接口结果：", result);
			}
		}catch(Exception e){
            log.error("订单转退货接口失败：", e);
		}
	}

	public List<StockOut> getStockOutList() {
		return stockOutList;
	}

	public void setStockOutList(List<StockOut> stockOutList) {
		this.stockOutList = stockOutList;
	}

    public OrderAlterChangeStatusRemoteService getRemoteService() {
        return remoteService;
    }

    public void setRemoteService(OrderAlterChangeStatusRemoteService remoteService) {
        this.remoteService = remoteService;
    }
}
