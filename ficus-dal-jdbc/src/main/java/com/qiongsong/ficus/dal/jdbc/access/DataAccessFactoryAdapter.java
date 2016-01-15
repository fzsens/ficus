package com.qiongsong.ficus.dal.jdbc.access;

import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import com.qiongsong.ficus.dal.datasource.SqlType;
import com.qiongsong.ficus.dal.jdbc.datasource.DataSourceFactory;
import com.qiongsong.ficus.dal.jdbc.datasource.DataSourceHolder;

/**
 * 数据源代理
 * @ClassName: DataAccessFactoryAdapter
 * @author thierry.fu
 * @date Jan 9, 2016 8:44:42 PM
 */
public class DataAccessFactoryAdapter implements DataAccessFactory {

  private final DataSourceFactory dataSourceFactory;

  private final ConcurrentHashMap<DataSource, DataAccess> dataAccessCache;

  public DataAccessFactoryAdapter(DataSourceFactory dataSourceFactory) {
    this.dataSourceFactory = dataSourceFactory;
    this.dataAccessCache = new ConcurrentHashMap<DataSource, DataAccess>();
  }

  public DataSourceFactory getDataSourceFactory() {
    return dataSourceFactory;
  }

  @Override
  public DataAccess getDataAccess(SqlType sqlType) {
    DataSourceHolder holder = dataSourceFactory.getHolder(sqlType);
    while (holder != null && holder.isFactory()) {
      holder = holder.getFactory().getHolder(sqlType);
    }
    if (holder == null || holder.getDataSource() == null) {
      throw new NullPointerException(
          "cannot found a dataSource for: " + sqlType);
    }
    DataSource dataSource = holder.getDataSource();
    DataAccess dataAccess = dataAccessCache.get(dataSource);
    if (dataAccess == null) {
      dataAccessCache.putIfAbsent(dataSource, new DataAccessImpl(dataSource));
      dataAccess = dataAccessCache.get(dataSource);
    }
    return dataAccess;
  }
}
