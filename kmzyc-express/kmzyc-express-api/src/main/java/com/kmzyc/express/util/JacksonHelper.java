package com.kmzyc.express.util;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;


/**
 * json序列化，及反序列化
 * 
 * @author hekai
 * 
 */
public class JacksonHelper {
  private static ObjectMapper toJSONMapper = new ObjectMapper();
  private static ObjectMapper fromJSONMapper = new ObjectMapper();
  static {
    fromJSONMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    fromJSONMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
  }

  public static String toJSON(Object obj) {
    ObjectMapper mapper = toJSONMapper;
    StringWriter writer = new StringWriter();
    try {
      mapper.writeValue(writer, obj);
    } catch (JsonGenerationException e) {
      throw new RuntimeException(e);
    } catch (JsonMappingException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return writer.toString();
  }

  public static void toJSON(Object obj, OutputStream stream, String charset) {
    ObjectMapper mapper = toJSONMapper;
    try {
      OutputStreamWriter writer = new OutputStreamWriter(stream, charset);
      mapper.writeValue(writer, obj);
    } catch (JsonGenerationException e) {
      throw new RuntimeException(e);
    } catch (JsonMappingException e) {
      throw new RuntimeException(e);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> T fromJSON(String json, Class<T> clazz) {
    ObjectMapper mapper = fromJSONMapper;
    try {
      return mapper.readValue(json, clazz);
    } catch (JsonParseException e) {
      throw new RuntimeException(e);
    } catch (JsonMappingException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> T fromJSON(InputStream json, Class<T> clazz) {
    ObjectMapper mapper = fromJSONMapper;
    try {
      return mapper.readValue(json, clazz);
    } catch (JsonParseException e) {
      throw new RuntimeException(e);
    } catch (JsonMappingException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> boolean isJSONList(List<T> list) {
    String jsonVal = null;
    try {
      jsonVal = toJSONMapper.writeValueAsString(list);
    } catch (JsonGenerationException e) {
      throw new RuntimeException(e);
    } catch (JsonMappingException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return "null".equals(jsonVal) || jsonVal == null ? false : true;
  }

  public static <T> List<T> fromJSONList(String jsonVal, Class<?> clazz) {

    List<T> list = null;
    try {
      JavaType javaType = getCollectionType(ArrayList.class, clazz);
      list = fromJSONMapper.readValue(jsonVal, javaType);
    } catch (JsonParseException e) {
      throw new RuntimeException(e);
    } catch (JsonMappingException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return list;
  }

  // json字符串转MAP
  public static <T, V> Map<T, V> fromJSONMap(String jsonVal, Class<?> keyClazz, Class<?> valueClazz) {

    Map<T, V> map = null;
    try {
      JavaType javaType = getCollectionType(HashMap.class, keyClazz, valueClazz);
      map = fromJSONMapper.readValue(jsonVal, javaType);
    } catch (JsonParseException e) {
      throw new RuntimeException(e);
    } catch (JsonMappingException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return map;
  }

  public static <T> T[] fromJSONArray(String jsonVal, Class<?> clazz) {
    T[] array = null;
    try {
      JavaType javaType = getCollectionType(Array.newInstance(clazz, 1).getClass(), clazz);
      array = fromJSONMapper.readValue(jsonVal, javaType);
    } catch (JsonParseException e) {
      throw new RuntimeException(e);
    } catch (JsonMappingException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return array;
  }

  /**
   * 获取泛型的Collection Type
   * 
   * @param collectionClass 泛型的Collection
   * @param elementClasses 元素类,如果为map需要传递2个参数
   * @return JavaType Java类型
   * @since 1.0
   */
  public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
    return fromJSONMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
  }

 
}
