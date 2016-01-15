package com.qiongsong.ficus.dal.jdbc.access;

import com.qiongsong.ficus.dal.datasource.SqlType;

/**
 * 获取DataAccess
 * @ClassName: DataAccessFactory
 * @author thierry.fu
 * @date Jan 9, 2016 8:30:06 PM
 */
public interface DataAccessFactory {
  /**
   *
   * @param sqlType
   * @return
   */
  DataAccess getDataAccess(SqlType sqlType);
}
