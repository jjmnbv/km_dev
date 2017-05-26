package com.kmzyc.framework.page;

/**
 * startpage表示起始页
 * viewpagecount表示浏览器可以显示的数量是几个，比如是10个或者是20个
 * currentPage表示当前页
 * totalpage表示总共有多少页
 * 这个类实现的功能是：假如数据有30页，而页面可以显示的是10页，但我们想浏览第10页内容，起始页应该是6，结束页应该是15
 */
public class WebTool {

    public static PageIndex getPageIndex(long viewpagecount, int currentPage, long totalpage) {
        long startpage = currentPage - (viewpagecount % 2 == 0 ? viewpagecount / 2 - 1 : viewpagecount / 2);
        long endpage = currentPage + viewpagecount / 2;
        if (startpage < 1) {
            startpage = 1;
            if (totalpage >= viewpagecount) endpage = viewpagecount;
            else endpage = totalpage;
        }
        if (endpage > totalpage) {
            endpage = totalpage;
            if ((endpage - viewpagecount) > 0) startpage = endpage - viewpagecount + 1;
            else startpage = 1;
        }
        return new PageIndex(startpage, endpage);
    }
}
