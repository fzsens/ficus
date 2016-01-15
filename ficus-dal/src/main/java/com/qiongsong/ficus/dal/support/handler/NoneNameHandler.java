package com.qiongsong.ficus.dal.support.handler;

/**
 * getTableName: User => User
 *
 * getPkFieldName: User => UserId
 *
 *
 *
 * @ClassName: NoneNameHandler
 * @author thierry.fu
 * @date Dec 31, 2015 3:24:19 PM
 */
public class NoneNameHandler implements NameHandler {

  public String getTableName(Class<?> entityClass) {
    return entityClass.getSimpleName();
  }

  public String getPkFieldName(Class<?> entityClass) {
    return entityClass.getSimpleName() + "Id";
  }

  public String getPkColumnName(Class<?> entityClass) {
    return entityClass.getSimpleName() + "Id";
  }

  public String getColumnName(Class<?> entityClass, String fieldName) {
    return fieldName;
  }

  public String getPkNativeValue(Class<?> entityClass, String dialect) {
    return null;
  }
}
