//package com.pltfm.cms.parse;
//
//import com.pltfm.app.util.DateTimeUtils;
//import com.pltfm.cms.util.FileOperateUtils;
//
//import org.apache.commons.io.IOUtils;
//import org.apache.log4j.Logger;
//import org.springframework.stereotype.Component;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.net.URL;
//import java.util.Date;
//
//import javax.annotation.Resource;
//
//import redis.clients.jedis.ShardedJedis;
//import redis.clients.jedis.ShardedJedisPool;
//
///*
// * 生成本地静态文件
// */
//@Component(value = "urlConnection")
//
//public class URLConnection {
//    //日志
//    private static Logger logger = Logger.getLogger(URLConnection.class);
//
//    @Resource(name = "shardedJedisPool")
//    private ShardedJedisPool shardedJedisPool;
//
//    public void makeLocalFile() throws IOException {
//        ShardedJedis jedis = shardedJedisPool.getResource();
//        String key = "cms_news_quartz_key";
//        if (jedis.setnx(key, "value") > 0) {
//            logger.info("生成资询系统页面成功执行时间：" + DateTimeUtils.getDateTime(new Date()));
//            jedis.expire(key, 30 * 60);
//            InputStream is = null;
//            OutputStream os = null;
//            try {
//                String path = PathConstants.infosPublishPath();
//                FileOperateUtils.checkAndCreateDirs(path);
//                String infosUrlHtml = PathConstants.infosUrlHtml();
//                String[] strUrl = infosUrlHtml.split(",");
//                int n;
//                for (int i = 0; i < strUrl.length; i++) {
//                    String urlHtml = strUrl[i];
//                    String titleUrl = urlHtml.substring(urlHtml.lastIndexOf("/"), urlHtml.length());
//                    URL url = new URL(urlHtml);
//                    java.net.URLConnection urlConn = url.openConnection();
//                    //用于创建输入流，获取指定url上资源文件的信息
//                    is = urlConn.getInputStream();
//                    os = new FileOutputStream(path + titleUrl);
//                    while ((n = is.read()) != -1) {
//                        os.write(n);
//                    }
//                    logger.info("生成页面：" + url);
//                }
//            } catch (Exception e) {
//                logger.error("生成资询系统页面失败。", e);
//            } finally {
//                IOUtils.closeQuietly(os);
//                IOUtils.closeQuietly(is);
//                jedis.del(key);
//            }
//        }
//    }
//
//    public ShardedJedisPool getShardedJedisPool() {
//        return shardedJedisPool;
//    }
//
//    public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
//        this.shardedJedisPool = shardedJedisPool;
//    }
//
//
//}
