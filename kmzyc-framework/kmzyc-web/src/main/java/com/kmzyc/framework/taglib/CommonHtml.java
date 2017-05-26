package com.kmzyc.framework.taglib;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.kmzyc.framework.common.util.GetProperties;


public class CommonHtml extends TagSupport {
    private static final long serialVersionUID = 1L;
    private String title;
    private String keywords;
    private String description;
    private String css;
    private String js;
    private String isAlert = "true";
    private String wdatePicker;
    private String webContext;
    private JspWriter out;
    HttpServletRequest request;

    public CommonHtml() {
    }

    private void init() {
        this.request = (HttpServletRequest)this.pageContext.getRequest();
        this.out = this.pageContext.getOut();
    }

    @Override
    public int doStartTag() {
        this.init();
        this.webContext = GetProperties.getConfig("staticFilesUrl");

        try {
            if(this.request.getAttribute("pageMsg") != null) {
                this.request.setAttribute("MSGBOOLEAN", "true");
            } else {
                this.request.setAttribute("MSGBOOLEAN", "false");
            }

            this.out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
            this.out.println("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
            this.out.println("<head>");
            this.out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">");
            this.out.println("<title>" + this.title + "</title>");
            this.out.println("<meta name=\"Keywords\" content=\"" + this.keywords + "\">");
            this.out.println("<meta name=\"description\" content=\"" + this.description + "\">");
            this.out.println("<link type=\'image/x-icon\' href=\'" + this.webContext + "images/logos/favicon.png\' rel=\'shortcut icon\'>");
            String[] e;
            String jsFile;
            int var3;
            int var4;
            String[] var5;
            if(this.css != null && !"".equals(this.css.trim())) {
                e = this.css.split(",");
                var5 = e;
                var4 = e.length;

                for(var3 = 0; var3 < var4; ++var3) {
                    jsFile = var5[var3];
                    this.out.println("<link href=\"" + this.webContext + "css/" + jsFile + ".css\" rel=\"stylesheet\" type=\"text/css\"/>");
                }
            }

            if(this.js != null && !"".equals(this.js.trim())) {
                e = this.js.split(",");
                var5 = e;
                var4 = e.length;

                for(var3 = 0; var3 < var4; ++var3) {
                    jsFile = var5[var3];
                    if(jsFile.indexOf("WdatePicker") > 0) {
                        this.wdatePicker = jsFile;
                    } else if("jquery-ui".equals(jsFile)) {
                        this.importJQueryUI(this.out);
                    } else {
                        this.out.println("<script type=\"text/javascript\" src=\"" + this.webContext + "js/" + jsFile + ".js\"></script>");
                    }
                }
            }

            this.out.println("</head>");
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return 1;
    }

    private void importJQueryUI(JspWriter out) throws IOException {
        out.println("<link rel=\"stylesheet\" href=\"" + this.webContext + "/js/jquery-ui/themes/base/jquery.ui.all.css\">");
        out.println("<script src=\"" + this.webContext + "/js/jquery-ui/jquery-1.4.4.js\"></script>");
        out.println("<script src=\"" + this.webContext + "/js/right.js\"></script>");
        out.println("<script src=\"" + this.webContext + "/js/jquery-ui/external/jquery.bgiframe-2.1.2.js\"></script>");
        out.println("<script src=\"" + this.webContext + "/js/jquery-ui/ui/jquery.ui.core.js\"></script>");
        out.println("<script src=\"" + this.webContext + "/js/jquery-ui/ui/jquery.ui.widget.js\"></script>");
        out.println("<script src=\"" + this.webContext + "/js/jquery-ui/ui/jquery.ui.mouse.js\"></script>");
        out.println("<script src=\"" + this.webContext + "/js/jquery-ui/ui/jquery.ui.button.js\"></script>");
        out.println("<script src=\"" + this.webContext + "/js/jquery-ui/ui/jquery.ui.draggable.js\"></script>");
        out.println("<script src=\"" + this.webContext + "/js/jquery-ui/ui/jquery.ui.position.js\"></script>");
        out.println("<script src=\"" + this.webContext + "/js/jquery-ui/ui/jquery.ui.resizable.js\"></script>");
        out.println("<script src=\"" + this.webContext + "/js/jquery-ui/ui/jquery.ui.dialog.js\"></script>");
        out.println("<script src=\"" + this.webContext + "/js/jquery-ui/ui/jquery.ui.dialog.js\"></script>");
        out.println("<script src=\"" + this.webContext + "/js/jquery-ui/ui/jquery.effects.core.js\"></script>");
        out.println("<script src=\"" + this.webContext + "/js/jquery-ui/ui/jquery.ui.datepicker.js\"></script>");
    }

    @Override
    public int doEndTag() throws JspTagException {
        try {
            this.out.println("</html>");
            if(this.wdatePicker != null) {
                this.out.println("<script type=\"text/javascript\" src=\"" + this.webContext + "js/" + this.wdatePicker + ".js\"></script>");
            }
        } catch (IOException var2) {
            var2.printStackTrace();
        }

        return 6;
    }

    public String getCss() {
        return this.css;
    }

    public void setCss(String css) {
        this.css = css;
    }

    public String getJs() {
        return this.js;
    }

    public void setJs(String js) {
        this.js = js;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsAlert() {
        return this.isAlert;
    }

    public void setIsAlert(String isAlert) {
        this.isAlert = isAlert;
    }

    public String getKeywords() {
        return this.keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

