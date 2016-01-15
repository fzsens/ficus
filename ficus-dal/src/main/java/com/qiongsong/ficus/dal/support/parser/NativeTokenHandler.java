package com.qiongsong.ficus.dal.support.parser;

import org.apache.commons.lang3.StringUtils;

import com.qiongsong.ficus.dal.support.handler.NameHandler;

/**
 *
 * @ClassName: NativeTokenHandler
 * @author thierry.fu
 * @date Jan 11, 2016 4:20:46 PM
 */
public class NativeTokenHandler implements TokenHandler {

  private Class<?> clazz;

  private String alias;

  private NameHandler nameHandler;

  public NativeTokenHandler(Class<?> clazz, String alias,
      NameHandler nameHandler) {
    this.clazz = clazz;
    this.alias = alias;
    this.nameHandler = nameHandler;
  }

  public String handleToken(String content) {
    String columnName = nameHandler.getColumnName(this.clazz, content);
    if (StringUtils.isBlank(alias)) {
      return columnName;
    }
    return new StringBuilder(alias).append(".").append(columnName).toString();
  }
}
