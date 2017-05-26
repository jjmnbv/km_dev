package com.kmzyc.b2b.ajax;

import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kmzyc.b2b.model.CmsAppManager;
import com.kmzyc.b2b.service.AppVersionService;
import com.km.framework.action.BaseAction;

@Controller
@Scope("prototype")
public class DownLoadAppAction extends BaseAction {

  private static final long serialVersionUID = 1L;
  // private static Logger logger = Logger.getLogger(DownLoadAppAction.class);
  private static Logger logger = LoggerFactory.getLogger(DownLoadAppAction.class);
  @Resource(name = "appVersionServiceImpl")
  private AppVersionService appVersionService;

  private String appType;

  @Override
  public String execute() throws Exception {
    String result = "";
    try {

      String UA = getRequest().getHeader("user-agent").toUpperCase();
      String androidString = "ANDROID";
      boolean isAllowVesion = true;
      if (UA.indexOf("LIKE MAC OS X") != -1 || UA.matches(".*((IOS)|(iPAD)).*(IPH).*")) {
        appType = "ios";
      } else if (UA.indexOf(androidString) != -1 || UA.contains("LINUX")) {
        String rex = ".*" + androidString + "[\\s/]*(\\d*[\\._\\d]*)";
        Matcher m = Pattern.compile(rex, Pattern.CASE_INSENSITIVE).matcher(UA);
        String version = null;
        if (m.find() && null != (version = m.group(1).replace("_", "."))
            && Double.parseDouble(version.substring(0, version.indexOf("."))) < 4.0) {
          result = "康美中药城app目前仅支持android4.0及以上版本，您当前的系统为android " + version + "，是否继续下载？";
          isAllowVesion = false;
        }
        appType = "android";
      }
      if (UA.indexOf("MICROMESSENGER") >= 0) {
        return "weixin";
      }
      if (appType == "ios" || appType == "android") {

        CmsAppManager appVersion = appVersionService.queryLastestVersionApp(appType);
        if (null == appVersion) {
          result = "<script>alert('暂时没有最新包，敬请期待！')</script>";
        } else {
          // 更新app下载次数
          appVersionService.updateDownCount(appType);
          if (isAllowVesion) {

            result = "<script>top.window.location='" + appVersion.getDownLoad() + "'</script>";
          } else {
            result =
                "<script>if(confirm('" + result + "')){top.window.location='"
                    + appVersion.getDownLoad() + "';}</script>";
          }
        }
      } else {
        result = "<script>alert('目前康美中药城app尚不支持您的手机系统');</script>";
      }
    } catch (Exception e) {
      logger.error("",e);
    }
    showpage(result);
    return null;
  }

  private void showpage(String result) {
    this.getResponse().setCharacterEncoding("GB2312");
    PrintWriter out = null;
    try {
      out = getResponse().getWriter();
      out.write(result);
    } catch (Exception e) {
      logger.error("response 向页面写入信息错误", e);
    } finally {
      if (out != null) {
        out.flush();
        out.close();
      }
    }
  }

  public String getAppType() {
    return appType;
  }

  public void setAppType(String appType) {
    this.appType = appType;
  }


}
