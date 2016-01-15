package com.qiongsong.ficus.dal.jdbc.datasource;

import com.qiongsong.ficus.dal.datasource.SqlType;

/**
 *
 * @ClassName: DataSourceFactory
 * @author thierry.fu
 * @date Jan 9, 2016 8:16:16 PM
 */
public interface DataSourceFactory {
  DataSourceHolder getHolder(SqlType sqlType);
}
