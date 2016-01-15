package com.qiongsong.ficus.dal.jdbc.datasource.support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.sql.DataSource;

import com.qiongsong.ficus.dal.datasource.SqlType;
import com.qiongsong.ficus.dal.jdbc.datasource.DataSourceFactory;
import com.qiongsong.ficus.dal.jdbc.datasource.DataSourceHolder;

/**
 * 随机数据源
 * @ClassName: RandomDataSourceFactory
 * @author thierry.fu
 * @date Jan 9, 2016 8:12:30 PM
 */
public class RandomDataSourceFactory implements DataSourceFactory {

  private Random random = new Random();

  private List<DataSourceHolder> dataSources = Collections.emptyList();

  public RandomDataSourceFactory() {
  }

  public void addDataSource(DataSource dataSource) {
    if (this.dataSources.size() == 0) {
      this.dataSources = new ArrayList<DataSourceHolder>(dataSources);
    }
    this.dataSources.add(new DataSourceHolder(dataSource));
  }

  public RandomDataSourceFactory(List<DataSource> dataSources) {
    this.setDataSources(dataSources);
  }

  public void setDataSources(List<DataSource> dataSources) {
    this.dataSources = new ArrayList<DataSourceHolder>(dataSources.size());
    for (DataSource dataSource : dataSources) {
      this.dataSources.add(new DataSourceHolder(dataSource));
    }
  }

  @Override
  public DataSourceHolder getHolder(SqlType sqlType) {
    if (dataSources.size() == 0) {
      return null;
    }
    int index = random.nextInt(dataSources.size()); // 0.. size
    return dataSources.get(index);
  }

}
