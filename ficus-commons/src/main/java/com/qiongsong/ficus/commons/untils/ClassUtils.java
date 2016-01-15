package com.qiongsong.ficus.commons.untils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import org.apache.commons.lang3.StringUtils;

import com.qiongsong.ficus.commons.FatalBeanException;

/**
 * Class 操作类，用于获取类的相关信息 拓展commons-lang3. 并增加對BeanInfo的操作
 *
 * @ClassName: ClassUtils
 * @author thierry.fu
 * @date 2015-12-23
 */
public class ClassUtils extends org.apache.commons.lang3.ClassUtils {

  /**
   * Map keyed by class containing CachedIntrospectionResults. Needs to be a
   * WeakHashMap with WeakReferences as values to allow for proper garbage
   * collection in case of multiple class loaders.
   */
  private static final Map<Class<?>, BeanInfo> classCache = Collections
      .synchronizedMap(new WeakHashMap<Class<?>, BeanInfo>());

  /**
   * 获取类本身的BeanInfo，不包含父类属性
   *
   * @param clazz
   * @return
   */
  public static BeanInfo getBeanInfo(Class<?> clazz, Class<?> stopClazz) {
    try {
      BeanInfo beanInfo;
      if (classCache.get(clazz) == null) {
        beanInfo = Introspector.getBeanInfo(clazz, stopClazz);
        classCache.put(clazz, beanInfo);
        // Immediately remove class from Introspector cache, to allow
        // for proper
        // garbage collection on class loader shutdown - we cache it
        // here anyway,
        // in a GC-friendly manner. In contrast to
        // CachedIntrospectionResults,
        // Introspector does not use WeakReferences as values of its
        // WeakHashMap!
        Class<?> classToFlush = clazz;
        do {
          Introspector.flushFromCaches(classToFlush);
          classToFlush = classToFlush.getSuperclass();
        } while (classToFlush != null);
      }
      else {
        beanInfo = classCache.get(clazz);
      }
      return beanInfo;
    }
    catch (IntrospectionException e) {
      throw new FatalBeanException("获取BeanInfo失败", e);
    }
  }

  /**
   * 获取类的BeanInfo,包含父类属性
   *
   * @param clazz
   * @return
   */
  public static BeanInfo getBeanInfo(Class<?> clazz) {

    return getBeanInfo(clazz, Object.class);
  }

  /**
   * 获取类本身的BeanInfo，不包含父类属性
   *
   * @param clazz
   * @return
   */
  public static BeanInfo getSelfBeanInfo(Class<?> clazz) {

    return getBeanInfo(clazz, clazz.getSuperclass());
  }

  /**
   * 获取类属性的PropertyDescriptor
   *
   * @param clazz
   * @param name
   * @return
   */
  public static PropertyDescriptor getPropertyDescriptor(Class<?> clazz,
      String name) {
    BeanInfo beanInfo = getBeanInfo(clazz);
    PropertyDescriptor[] propertyDescriptors = beanInfo
        .getPropertyDescriptors();
    if (propertyDescriptors == null) {
      return null;
    }
    for (PropertyDescriptor pd : propertyDescriptors) {
      if (StringUtils.equals(pd.getName(), name)) {
        return pd;
      }
    }
    return null;
  }

  /**
   * bean属性转换为map
   *
   * @param object
   * @return
   */
  public static Map<String, Object> getBeanPropMap(Object object) {

    BeanInfo beanInfo = getBeanInfo(object.getClass());
    PropertyDescriptor[] propertyDescriptors = beanInfo
        .getPropertyDescriptors();
    if (propertyDescriptors == null) {
      return null;
    }
    Map<String, Object> propMap = new HashMap<String, Object>();
    for (PropertyDescriptor pd : propertyDescriptors) {

      Method readMethod = pd.getReadMethod();
      if (readMethod == null) {
        continue;
      }
      Object value = invokeMethod(readMethod, object);
      propMap.put(pd.getName(), value);
    }
    return propMap;
  }

  /**
   * invokeMethod
   *
   * @param method
   * @param bean
   * @param value
   */
  public static void invokeMethod(Method method, Object bean, Object value) {
    try {
      method.invoke(bean, value);
    }
    catch (Exception e) {
      throw new FatalBeanException(
          "执行invokeMethod失败:" + (method == null ? "null" : method.getName()),
          e);
    }
  }

  /**
   * invokeMethod
   *
   * @param method
   * @param bean
   */
  public static Object invokeMethod(Method method, Object bean) {
    try {
      method.setAccessible(true);
      return method.invoke(bean);
    }
    catch (Exception e) {
      throw new FatalBeanException(
          "执行invokeMethod失败:" + (method == null ? "null" : method.getName()),
          e);
    }
  }

  /**
   * 初始化实例
   *
   * @param clazz
   * @return
   */
  public static Object newInstance(Class<?> clazz) {
    try {
      return clazz.newInstance();
    }
    catch (Exception e) {
      throw new FatalBeanException(
          "根据class创建实例失败:" + (clazz == null ? "null" : clazz.getName()), e);
    }
  }

  /**
   * 初始化实例
   *
   * @param clazz
   * @return
   */
  public static Object newInstance(String clazz) {

    try {
      Class<?> loadClass = getDefaultClassLoader().loadClass(clazz);
      return loadClass.newInstance();
    }
    catch (Exception e) {
      throw new FatalBeanException("创建实例失败:" + clazz, e);
    }
  }

  /**
   * 加载类
   *
   * @param clazz
   * @return
   */
  public static Class<?> loadClass(String clazz) {
    try {
      Class<?> loadClass = getDefaultClassLoader().loadClass(clazz);
      return loadClass;
    }
    catch (Exception e) {
      throw new FatalBeanException("加载类失败:" + clazz, e);
    }
  }

  /**
   * 当前线程的classLoader
   *
   * @return
   */
  public static ClassLoader getDefaultClassLoader() {
    ClassLoader cl = null;
    try {
      cl = Thread.currentThread().getContextClassLoader();
    }
    catch (Throwable ex) {
      // Cannot access thread context ClassLoader - falling back...
    }
    if (cl == null) {
      // No thread context class loader -> use class loader of this class.
      cl = ClassUtils.class.getClassLoader();
      if (cl == null) {
        // getClassLoader() returning null indicates the bootstrap
        // ClassLoader
        try {
          cl = ClassLoader.getSystemClassLoader();
        }
        catch (Throwable ex) {
          // Cannot access system ClassLoader - oh well, maybe the
          // caller can live with null...
        }
      }
    }
    return cl;
  }

  /**
   * 通过反射, 获得定义Class时声明的父类的泛型参数的类型. 如无法找到, 返回Object.class.
   *
   */
  public static Class<?> getSuperClassGenricType(final Class<?> clazz,
      final int index) {

    // 返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。
    Type genType = clazz.getGenericSuperclass();

    if (!(genType instanceof ParameterizedType)) {
      return Object.class;
    }
    // 返回表示此类型实际类型参数的 Type 对象的数组。
    Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

    if (index >= params.length || index < 0) {
      return Object.class;
    }
    if (!(params[index] instanceof Class)) {
      return Object.class;
    }

    return (Class<?>) params[index];
  }

  public static Class<?> getSuperClassGenricType(final Class<?> clazz) {
    return getSuperClassGenricType(clazz, 1);
  }
}
