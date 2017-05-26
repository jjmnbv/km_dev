package test;

import com.pltfm.app.vobject.AppraiseReply;
import com.pltfm.app.vobject.ProdAppraise;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2016/8/10 10:58
 */
public class ProdAppraiseTest {

    public static void main(String[] args) {
        List<ProdAppraise> appraiseList = new ArrayList<>();
        ProdAppraise prodAppraise = new ProdAppraise();
        prodAppraise.setAppraiseDate(new Date());
        prodAppraise.setCheckMan("sfsdf");
        appraiseList.add(prodAppraise);
        List<AppraiseReply> replyList = new ArrayList<>();
        AppraiseReply reply = new AppraiseReply();
        AppraiseReply reply2 = new AppraiseReply();
        reply.setApprReplyId(123l);
        reply.setReplyDate(new Date());
        replyList.add(reply);
        replyList.add(reply2);
        prodAppraise.setAppReplyList(replyList);
        System.out.println(appraiseList);
        System.out.println("________________________________");
        appraiseList = appraiseList.stream().map(appraise -> {
            List<AppraiseReply> appReplyList = appraise.getAppReplyList();
            if (CollectionUtils.isNotEmpty(appReplyList)) {
                appReplyList = appReplyList.stream().map(appraiseReply -> {
                    if (appraiseReply.getApprReplyId() == null) {
                        appraise.getAppReplyList().clear();
                    }
                    return appraiseReply;
                }).collect(Collectors.toList());
            }
            return appraise;
        }).collect(Collectors.toList());
        System.out.println(appraiseList);
    }
}
