package com.qiongsong.ficus.commons.untils;

import org.apache.commons.lang3.StringUtils;

/**
 * 名称转换
 *
 * @ClassName: NameUtils
 * @author thierry.fu
 * @date 2015-12-23
 */
public class NameUtils {

  /**
   * Default underLine
   */
  private static final String UNDERLINE_SEPARATOR = "_";

  /**
   * 首字母大写
   *
   * @param name
   * @return
   */
  public static String capitalize(String name) {
    return StringUtils.capitalize(name);
  }

  /**
   * 首字母小写
   *
   * @param name
   * @return
   */
  public static String uncapitalize(String name) {
    return StringUtils.uncapitalize(name);
  }

  /**
   * 转化成驼峰式命名法, 原字符串的分隔默认为: 下划线"_" HELLO_WORLD => helloWorld
   *
   * @param name
   *            原字符串
   * @return 驼峰式命名后的字符串
   */
  public static String camelCaseName(String name) {
    return camelCaseName(name, UNDERLINE_SEPARATOR);
  }

  /**
   * 转化成驼峰式命名法.
   *
   * @param name
   *            原字符串
   * @param separator
   *            分隔字符串
   * @return 驼峰式命名后的字符串
   */
  public static String camelCaseName(String name, String separator) {
    if (StringUtils.isBlank(name)) {
      return name;
    }
    name = name.toLowerCase();
    String[] names = name.split(separator);
    if (null == names || names.length == 0) {
      return name;
    }
    if (names.length == 1) {
      return name;
    }
    StringBuffer nameBuf = new StringBuffer(name.length());
    nameBuf.append(names[0]);
    for (int i = 1, len = names.length; i < len; i++) {
      nameBuf.append(StringUtils.capitalize(names[i]));
    }
    return nameBuf.toString();
  }

  /**
   * 转化成驼峰式命名法.
   *
   * @param name
   *            原字符串
   * @param separator
   *            分隔字符
   * @return 驼峰式命名后的字符串
   */
  public static String camelCaseName(String name, char separator) {
    return camelCaseName(name, String.valueOf((separator)));
  }

  /**
   * 将驼峰式命名的字符串转换为下划线大写方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。</br>
   * 例如：helloWorld->HELLO_WORLD
   *
   * @param name
   *            转换前的驼峰式命名的字符串
   * @return 转换后下划线大写方式命名的字符串
   */
  public static String underscoreName(String name) {
    StringBuilder result = new StringBuilder();
    if (name != null && name.length() > 0) {
      // 将第一个字符处理成大写
      result.append(name.substring(0, 1).toUpperCase());
      // 循环处理其余字符
      for (int i = 1; i < name.length(); i++) {
        String s = name.substring(i, i + 1);
        // 在大写字母前添加下划线
        if (s.equals(s.toUpperCase()) && !Character.isDigit(s.charAt(0))) {
          result.append("_");
        }
        // 其他字符直接转成大写
        result.append(s.toUpperCase());
      }
    }
    return result.toString();
  }

}
