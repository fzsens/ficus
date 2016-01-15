package com.qiongsong.ficus.dal;

import com.qiongsong.ficus.dal.datasource.SqlType;

/**
 * generate by builder .pass to other component be implement.
 *
 * @ClassName: NestedSql
 * @author thierry.fu
 * @date Dec 30, 2015 12:00:16 PM
 */
public interface NestedSql {

  /**
   * sql statement
   * @return
   */
  String getSql();

  /**
   * parameters
   * @return
   */
  Object[] getParameters();

  String toString();

  SqlType getSqlType();

}
