package com.pltfm.app.util;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;

/**
 * freemarker工具
 *
 * @author kingapex 2010-2-8下午04:23:18
 */
public class FreeMarkerUtil {
    private FreeMarkerUtil() {
    }

//	private static Configuration cfg;

    /**
     * 获取servlet上下文件的Configuration
     *
     * @return public static Configuration getServletCfg(String pageFolder) {
     *
     * Configuration cfg = new Configuration(); cfg.setServletContextForTemplateLoading(ThreadContextHolder
     * .getHttpRequest().getSession().getServletContext(), pageFolder); cfg.setObjectWrapper(new
     * DefaultObjectWrapper()); return cfg; }
     */

    public static Configuration getCfg() {

        Configuration cfg = new Configuration();
        cfg.setTemplateUpdateDelay(6000);
        cfg.setCacheStorage(new freemarker.cache.MruCacheStorage(20, 250));
            /*
			Map<String,TemplateDirectiveModel> directiveMap = DirectiveFactory.getCommonDirective();
			Iterator<String> keyIter= directiveMap.keySet().iterator();
			
			while(keyIter.hasNext()){
				String key = keyIter.next();
				//System.out.println("put -> ["+key+"]");
				cfg.setSharedVariable(key, directiveMap.get(key));
			}
			*/

        cfg.setObjectWrapper(new DefaultObjectWrapper());
        cfg.setDefaultEncoding("UTF-8");
        cfg.setLocale(java.util.Locale.CHINA);
        cfg.setEncoding(java.util.Locale.CHINA, "UTF-8");


        return cfg;
    }


    public static Configuration getFolderCfg(String pageFolder)
            throws IOException {
        Configuration cfg = getCfg();
        cfg.setDirectoryForTemplateLoading(new File(pageFolder));

        return cfg;

    }


    public static void main(String[] args) throws IOException,
            TemplateException {
//        Configuration cfg = FreeMarkerUtil
//                .getFolderCfg("D:/workspace/eopnew/eop/WebContent/WEB-INF/classes/com/enation/app/shop/core/widget/goodscat");
//        Template temp = cfg.getTemplate("GoodsCat.html");
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//
//        Writer out = new OutputStreamWriter(stream, "UTF-8");
//        temp.process(new HashMap(), out);
//
//        out.flush();
//        String html = stream.toString();
//        System.out.println(html);
    }

}
