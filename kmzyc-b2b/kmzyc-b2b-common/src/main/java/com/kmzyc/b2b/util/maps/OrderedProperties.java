package com.kmzyc.b2b.util.maps;

import java.util.*;

public class OrderedProperties extends Properties {

  /**
   * uid
   */
  private static final long serialVersionUID = 5320090533776547878L;

  private final LinkedHashSet<Object> keys = new LinkedHashSet<Object>();

  public Enumeration<Object> keys() {
    return Collections.<Object>enumeration(keys);
  }

  public Object put(Object key, Object value) {
    keys.add(key);
    return super.put(key, value);
  }

  public Set<Object> keySet() {
    return keys;
  }

  public Set<String> stringPropertyNames() {
    Set<String> set = new LinkedHashSet<String>();
    for (Object key : this.keys) {
      set.add((String) key);
    }

    return set;
  }
}
