package com.kmzyc.framework.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.kmzyc.supplier.model.KeyWords;
import com.kmzyc.supplier.service.KeyWordsService;

/**
 * 功能：关键字过滤
 *
 * @author Zhoujiwei
 * @since 2015/12/18 17:45
 */
@Component
public class KeyWordsUtils {

    private static Logger logger = LoggerFactory.getLogger(KeyWordsUtils.class);

    @Resource(name = "keyWordsServiceImpl")
    private KeyWordsService keyWordsService;

    private static Map<String, String> map = new HashMap<String, String>();

    private List<KeyWords> listKeyWords;

    @PostConstruct
    public void initMap() {
        // 获取过滤的关键字放入map集合中
        try {
            listKeyWords = keyWordsService.findKeyWords();
            if (listKeyWords == null) {
                return;
            }

            for (int i = 0; i < listKeyWords.size(); i++) {
                map.put(listKeyWords.get(i).getKeyWords(), listKeyWords.get(i).getRepWords());
            }
        } catch (Exception e) {
            logger.error("获取过滤关键字错误" + e.getMessage(), e);
        }
    }

    public static Map<String, String> getMap() {
        return map;
    }

    /**
     * 敏感词过滤
     * <note>
     *     目前只过滤String类型
     * </note>
     *
     * @param object            所需过滤的对象
     * @param unneededList      不需要过滤的字段
     * @throws IllegalAccessException
     */
    public static void filter(Object object, List<String> unneededList) throws IllegalAccessException {
        try {
            Class<?> clazz = object.getClass();
            Field[] fields = clazz.getDeclaredFields();

            boolean notEmpty = CollectionUtils.isNotEmpty(unneededList);
            if (fields != null && fields.length != 0) {
                for (Field field : fields) {
                    if (field.getType() != String.class) {
                        continue;
                    }

                    if (notEmpty && unneededList.contains(field.getName())) {
                        continue;
                    }

                    //私有变量必须先设置Accessible为true
                    field.setAccessible(true);
                    String preValue = (String)field.get(object);
                    String resultValue = filterSingleString(preValue);
                    if (!resultValue.equals(preValue)) {
                        //设置用set类方法
                        field.set(object, resultValue);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            logger.error("过滤敏感词失败：：" + e.getMessage());

            throw e;
        }
    }

    /**
     * 字符串进行敏感词过滤
     *
     * @param source    所需过滤的字符串
     * @throws IllegalAccessException
     */
    public static String filterSingleString(String source) {
        if (StringUtils.isEmpty(source)) {
            return "";
        }

        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            if (source.contains(key)) {
                String value = entry.getValue();
                value = StringUtils.isEmpty(value) ? "*" : value;
                source = source.replaceAll(key, value);
            }
        }

        return source;
    }

    public void setKeyWordsService(KeyWordsService keyWordsService) {
        this.keyWordsService = keyWordsService;
    }

}
