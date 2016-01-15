package com.qiongsong.ficus.ibatis.wrapper;

import java.util.HashMap;
import java.util.Map;

import com.qiongsong.ficus.ibatis.builder.PropertyTokenizer;
import com.qiongsong.ficus.ibatis.meta.MetaObject;

/**
 * @author Clinton Begin
 */
public class MapWrapper extends BaseWrapper {

  private Map<String, Object> map;

  public MapWrapper(MetaObject metaObject, Map<String, Object> map) {
    super(metaObject);
    this.map = map;
  }

  public Object get(PropertyTokenizer prop) {
    if (prop.getIndex() != null) {
      Object collection = resolveCollection(prop, map);
      return getCollectionValue(prop, collection);
    }
    else {
      return map.get(prop.getName());
    }
  }

  public void set(PropertyTokenizer prop, Object value) {
    if (prop.getIndex() != null) {
      Object collection = resolveCollection(prop, map);
      setCollectionValue(prop, collection, value);
    }
    else {
      map.put(prop.getName(), value);
    }
  }

  public MetaObject instantiatePropertyValue(String name,
      PropertyTokenizer prop) {
    HashMap<String, Object> map = new HashMap<String, Object>();
    set(prop, map);
    return MetaObject.forObject(map);
  }

}
