package com.qiongsong.ficus.dal.dialect;

/**
 *
 * @ClassName: Dialect
 * @author thierry.fu
 * @date Jan 5, 2016 4:39:30 PM
 */
public interface Dialect {
  String getLimitString(String querySql, int offset, int limit);

  String getDialectName();
}
