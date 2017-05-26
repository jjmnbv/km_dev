package com.pltfm.app.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kmzyc.commons.exception.ActionException;
import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.dao.OrderPreferentialDAO;
import com.pltfm.app.entities.OrderCarry;
import com.pltfm.app.service.OrderExecuteService;
import com.pltfm.app.service.OrderItemQryService;
import com.pltfm.app.service.OrderPayStatementService;
import com.pltfm.app.service.OrderQryService;
import com.pltfm.app.util.OrderDictionaryEnum;
import com.pltfm.sys.model.SysUser;

@SuppressWarnings("unchecked")
@Controller("orderExecuteAction")
@Scope("prototype")
public class OrderExecuteAction extends BaseAction {

    private static final long serialVersionUID = 1L;
    private Logger log = Logger.getLogger(OrderExecuteAction.class);
    @Resource
    private OrderExecuteService orderExecuteService;
    @Resource
    OrderItemQryService orderItemQryService;
    @Resource
    OrderQryService orderQryService;
    @Resource
    OrderPreferentialDAO orderPreferentialDAO;
    @Resource
    OrderPayStatementService orderPayStatementService;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Map<String, String> map = new HashMap<String, String>();// 查询条件map

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }


    /**
     * 移除结转标识
     * 
     * @param symbol @throws
     */
    public String removeExecuteSymbol() throws Exception {
        HttpServletRequest request = getHttpServletRequest();
        String symbol = request.getParameter("symbol");
        orderExecuteService.removeExecuteSymbol(symbol);
        return null;
    }

    /**
     * 结转
     * 
     * @return
     * @throws Exception
     */
    public void orderExecute() throws Exception {
        try {
            // 查询条件map
            HttpServletRequest request = getHttpServletRequest();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SysUser sysuser = (SysUser) request.getSession().getAttribute("sysUser");
            Map mapQuery = new HashMap();
            mapQuery.put("sysuser", sysuser);
            mapQuery.put("StartDate", sdf.parse(request.getParameter("StartDate")));
            mapQuery.put("endDate", sdf.parse(request.getParameter("endDate")));
            /* 删除渠道 mapQuery.put("orderChannel", request.getParameter("orderChannel")); */

            List<Integer> otherStatus = new ArrayList<Integer>();
            otherStatus.add(OrderDictionaryEnum.Order_Status.Risk_Pass.getKey());// 风控通过
            otherStatus.add(OrderDictionaryEnum.Order_Status.Splited_Not_Settle.getKey());
            /*
             * 取消合并未结转状态
             * otherStatus.add(OrderDictionaryEnum.Order_Status.Merge_Not_Settle.getKey());
             */
            otherStatus.add(OrderDictionaryEnum.Order_Status.Settle_Not_Stock.getKey());
            mapQuery.put("orderStatus", otherStatus);
            mapQuery.put("operator", sysuser.getUserName());// 操作人
            OrderCarry oc = orderExecuteService
                    .OrderExecuteEntrance(request.getParameter("orderChannel"), mapQuery);
            StringBuffer sb = new StringBuffer();
            if (null != oc) {
                sb.append('{').append("id:").append(oc.getHandleId()).append(",OrderSum:")
                        .append(oc.getOrderSum()).append(",NoOrderSum:").append(oc.getNoOrderSum())
                        .append('}');
            }
            getHttpServletResponse().getWriter().print(sb.toString());
        } catch (Exception e) {
            log.error("发生错误", e);
        }
        // return null;
    }

    /**
     * 验证是否能结转
     * 
     * @return
     */
    public String iaAllowExecute() {
        String rs = "0";
        String operator = getHttpServletRequest().getParameter("operator");
        try {
            if (null == operator || "".equals(operator)) {
                operator = "B2B";
            }
            Date maxDate = orderExecuteService.queryMaxCreateDate(operator);
            Date now = new Date();
            if (null == maxDate || (now.getTime() - maxDate.getTime()) / 60000d > 2) {
                rs = "1";
            }
        } catch (ServiceException e) {
            log.error(e);
        }
        try {
            getHttpServletResponse().getWriter().print(rs);
        } catch (Exception e) {
        }
        return null;
    }

    public String qry() {
        Map mapPa = new HashMap();
        mapPa.put("start", ((getPage().getPageNo() - 1) * getPage().getPageSize() + 1) + "");
        mapPa.put("end", (getPage().getPageNo() * getPage().getPageSize()) + "");
        try {
            getPage().setRecordCount(orderExecuteService.listCount(mapPa));
            getPage().setDataList(orderExecuteService.listOrder(mapPa));
        } catch (ServiceException e) {
            log.error("发生错误", e);
        }
        return "qryList";
    }

    public String qryList() throws ActionException {
        try {
            Map mapPa = new HashMap();
            if (map.get("startDate") != null && !"".equals(map.get("startDate"))) {
                mapPa.put("startDate", sdf.parse(map.get("startDate")));
            }
            if (map.get("endDate") != null && !"".equals(map.get("endDate"))) {
                mapPa.put("endDate", sdf.parse(map.get("endDate")));
            }
            mapPa.put("start", ((getPage().getPageNo() - 1) * getPage().getPageSize() + 1) + "");
            mapPa.put("end", (getPage().getPageNo() * getPage().getPageSize()) + "");
            getPage().setRecordCount(orderExecuteService.listCount(mapPa));
            getPage().setDataList(orderExecuteService.listOrder(mapPa));
        } catch (Exception e) {
            log.error("异常！", e);
            throw new ActionException();
        }
        return "qryList";
    }
}
