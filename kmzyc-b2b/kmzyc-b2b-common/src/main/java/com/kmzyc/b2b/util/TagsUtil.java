package com.kmzyc.b2b.util;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import com.kmzyc.cms.remote.service.ViewProductInfoRemoteService;
import com.kmzyc.util.StringUtil;

// import com.km.framework.common.util.RemoteTool;
@Component("tagsUtil")
public class TagsUtil {

  // private static final Logger LOGGER = Logger.getLogger(TagsUtil.class);
  private static Logger logger = LoggerFactory.getLogger(TagsUtil.class);

  private static Cache<Integer, List<String>> cache = CacheBuilder.newBuilder().maximumSize(2000)
  // .weakKeys() 启动week key，使用==比较
      .softValues().expireAfterAccess(5, TimeUnit.MINUTES).build();


  @Resource
  private ViewProductInfoRemoteService viewProductInfoRemoteService;

  /**
   * 获取商品标签
   * 
   * @param productId 产品ID
   * @return
   */
  public List<String> getProductTags(final int productId, final String channel) {
    List<String> result = Lists.newArrayList();
    try {
      if(StringUtil.isEmpty(productId)){
        return result;
      }

      result = cache.get(productId, new Callable<List<String>>() {

        @Override
        public List<String> call() throws Exception {
        
          List<String> result = viewProductInfoRemoteService.queryProductTags(productId);
          if(result == null){
            return Lists.newArrayList();
          }else {
            return result;
          }
        }
      });
    } catch (Exception e) {
      logger.error("获取产品ID为：" + productId + " 的标签失败.", e);
    }
    return result;
  }
}
