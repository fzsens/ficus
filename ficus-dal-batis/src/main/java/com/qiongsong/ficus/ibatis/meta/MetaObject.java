package com.qiongsong.ficus.ibatis.meta;

import java.util.Collection;
import java.util.Map;

import com.qiongsong.ficus.ibatis.builder.PropertyTokenizer;
import com.qiongsong.ficus.ibatis.builder.SystemMetaObject;
import com.qiongsong.ficus.ibatis.wrapper.BeanWrapper;
import com.qiongsong.ficus.ibatis.wrapper.CollectionWrapper;
import com.qiongsong.ficus.ibatis.wrapper.MapWrapper;
import com.qiongsong.ficus.ibatis.wrapper.ObjectWrapper;

/**
 * @author Clinton Begin
 */
public class MetaObject {

  private Object originalObject;

  private ObjectWrapper objectWrapper;

  @SuppressWarnings({ "unchecked", "rawtypes" })
  private MetaObject(Object object) {
    this.originalObject = object;

    if (object instanceof ObjectWrapper) {
      this.objectWrapper = (ObjectWrapper) object;
    }
    else if (object instanceof Map) {
      this.objectWrapper = new MapWrapper(this, (Map) object);
    }
    else if (object instanceof Collection) {
      this.objectWrapper = new CollectionWrapper(this, (Collection) object);
    }
    else {
      this.objectWrapper = new BeanWrapper(this, object);
    }
  }

  public static MetaObject forObject(Object object) {
    if (object == null) {
      return SystemMetaObject.NULL_META_OBJECT;
    }
    else {
      return new MetaObject(object);
    }
  }

  public Object getOriginalObject() {
    return originalObject;
  }

  public Object getValue(String name) {
    PropertyTokenizer prop = new PropertyTokenizer(name);
    if (prop.hasNext()) {
      MetaObject metaValue = metaObjectForProperty(prop.getIndexedName());
      if (metaValue == SystemMetaObject.NULL_META_OBJECT) {
        return null;
      }
      else {
        return metaValue.getValue(prop.getChildren());
      }
    }
    else {
      return objectWrapper.get(prop);
    }
  }

  public void setValue(String name, Object value) {
    PropertyTokenizer prop = new PropertyTokenizer(name);
    if (prop.hasNext()) {
      MetaObject metaValue = metaObjectForProperty(prop.getIndexedName());
      if (metaValue == SystemMetaObject.NULL_META_OBJECT) {
        if (value == null && prop.getChildren() != null) {
          // don't instantiate child path if value is null
          return;
        }
        else {
          metaValue = objectWrapper.instantiatePropertyValue(name, prop);
        }
      }
      metaValue.setValue(prop.getChildren(), value);
    }
    else {
      objectWrapper.set(prop, value);
    }
  }

  public MetaObject metaObjectForProperty(String name) {
    Object value = getValue(name);
    return MetaObject.forObject(value);
  }

}
