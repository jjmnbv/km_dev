package com.pltfm.app.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.service.RefundRequestService;
import com.pltfm.app.util.SysConstants;
import com.pltfm.app.vobject.RefundRequest;
import com.pltfm.sys.model.SysUser;

@Controller("refundRequestAction")
@SuppressWarnings("unchecked")
@Scope("prototype")
public class RefundRequestAction extends BaseAction implements ModelDriven {

    private static final long serialVersionUID = 1L;
    private Logger log = Logger.getLogger(RefundRequestAction.class);
    @Resource(name = "refundRequestService")
    private RefundRequestService refundRequestService;
    private final static String reqUrl = SysConstants.REFUND_TRACK_URL; // 中转http://www.km1818.com/payresult/refundTrack.action
    private Map<String, String> map = new HashMap<String, String>();// 查询条件map
    /**
     * 年月日时分秒(无下划线) yyyyMMddHHmmss
     */
    private final SimpleDateFormat dtLong = new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     * 列表
     */
    @Override
    public String execute() throws Exception {
        if (null == map) {
            map = new HashMap<String, String>();
        }
        if (!map.containsKey("channel") || map.get("channel").length() < 1) {
            map.put("channel", "B2B");
        }
        if (!map.containsKey("platformCode") || map.get("platformCode").length() < 1) {
            map.put("platformCode", "3");
        }
        map.put("start", ((getPage().getPageNo() - 1) * getPage().getPageSize() + 1) + "");
        map.put("end", (getPage().getPageNo() * getPage().getPageSize()) + "");
        try {
            getPage().setRecordCount(refundRequestService.queryRefundRequestCount(map));
            getPage().setDataList(refundRequestService.queryRefundRequest(map));
        } catch (Exception e) {
            log.error(e);
        }
        return SUCCESS;
    }

    /**
     * 请求数据
     * 
     * @return
     * @throws Exception
     */
    public String sendRefundData() throws Exception {
        try {
            HttpServletRequest request = getHttpServletRequest();
            String requestIds = request.getParameter("requestIds");// 退款请求ID
            String platformCode = request.getParameter("platformCode");// 退款平台
            if (null != requestIds && requestIds.length() > 0) {
                List<Long> rIds = new ArrayList<Long>();
                for (String rid : requestIds.split(",")) {
                    rIds.add(Long.parseLong(rid));
                }
                if (rIds.isEmpty()) {
                    return null;
                }
                List<RefundRequest> rrList =
                        refundRequestService.queryBatchReadyRefundRequestByRID(rIds);
                if (null != rrList && !rrList.isEmpty()) {
                    Map session = ActionContext.getContext().getSession();
                    String uName = ((SysUser) session.get("sysUser")).getUserName();
                    String refundNo = dtLong.format(new Date()) + new Random().nextInt(1000);
                    refundRequestService.updateRequestDateRefundRequest(rIds, refundNo, uName);// 更新提交请求时间
                    StringBuffer sbHtml = new StringBuffer();
                    RefundRequest rr = rrList.get(0);
                    sbHtml.append(
                            "<form id=\"refundRequestForm\" style=\"display:none;\" name=\"refundRequestForm\" action=\""
                                    + reqUrl + "\" method=\"post\" target=\"_blank\">")
                            .append("<input type=\"hidden\" name=\"reqData\" value=\"")
                            .append(rr.getOutBatchNo()).append('^').append(rr.getRefurnMoney())
                            .append('^').append(1 == rr.getRefundType() ? "取消订单退款" : "退换货退款");
                    for (int i = 1, len = rrList.size(); i < len; i++) {
                        rr = rrList.get(i);
                        sbHtml.append('#').append(rr.getOutBatchNo()).append('^')
                                .append(rr.getRefurnMoney()).append('^')
                                .append(1 == rr.getRefundType() ? "取消订单退款" : "退换货退款");
                    }
                    sbHtml.append("\" /> ").append("<input type=\"hidden\" name=\"kmRefundSign\" ")
                            .append("id=\"kmRefundSign\" value=\"0d36faa47670a361d807b046d9229eaf\" />")
                            .append("<input type=\"hidden\" name=\"refundNo\" value=\"" + refundNo
                                    + "\" />")
                            .append("<input type=\"hidden\" name=\"platformCode\"")
                            .append(" value=\"" + platformCode + "\" />").append("</form>")
                            .append("<script>document.forms['refundRequestForm'].submit();</script>");
                    getHttpServletResponse().getWriter().print(sbHtml);
                }
            }
        } catch (Exception e) {
            log.error(e);
        }
        return null;
    }

    public String changeRefundRequest() throws Exception {
        HttpServletRequest request = getHttpServletRequest();
        String requestIds = request.getParameter("requestIds");// 退款请求ID
        if (null != requestIds && requestIds.length() > 0) {
            List<Long> rIds = new ArrayList<Long>();
            for (String rid : requestIds.split(",")) {
                rIds.add(Long.parseLong(rid));
            }
            if (rIds.isEmpty()) {
                return null;
            }
            List<RefundRequest> rrList =
                    refundRequestService.queryBatchReadyRefundRequestByRID(rIds);
            if (null != rrList && !rrList.isEmpty()) {
                StringBuffer sb = new StringBuffer();
                Map session = ActionContext.getContext().getSession();
                String uName = ((SysUser) session.get("sysUser")).getUserName();
                for (RefundRequest rr : rrList) {
                    try {
                        rr.setOperater(uName);
                        refundRequestService.updateFinishRefundRequest(rr);
                        sb.append(rr.getRrid()).append(',');
                    } catch (Exception e) {
                        log.error(e);
                    }
                }
                getHttpServletResponse().getWriter().print("修改成功列表：" + sb.toString());
            }
        }
        return null;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}
