package com.qiongsong.ficus.ibatis.wrapper;

import com.qiongsong.ficus.ibatis.builder.PropertyTokenizer;
import com.qiongsong.ficus.ibatis.meta.MetaObject;
/**
 *
 * @ClassName: ObjectWrapper
 * @author Clinton Begin
 * @author thierry.fu
 * @date Jan 7, 2016 10:23:07 PM
 */
public interface ObjectWrapper {

  Object get(PropertyTokenizer prop);

  void set(PropertyTokenizer prop, Object value);

  // String findProperty(String name, boolean useCamelCaseMapping);
  //
  // String[] getGetterNames();
  //
  // String[] getSetterNames();

  // Class<?> getSetterType(String name);
  //
  // Class<?> getGetterType(String name);

  // boolean hasSetter(String name);

  // boolean hasGetter(String name);

  MetaObject instantiatePropertyValue(String name, PropertyTokenizer prop);

  // boolean isCollection();

  // void add(Object element);

  // <E> void addAll(List<E> element);

}
