package com.qiongsong.ficus.dal.jdbc.datasource.support;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.util.CollectionUtils;

import com.qiongsong.ficus.dal.datasource.SqlType;
import com.qiongsong.ficus.dal.jdbc.datasource.DataSourceFactory;
import com.qiongsong.ficus.dal.jdbc.datasource.DataSourceHolder;

/**
 * 主从数据源，使用一个Master 简单数据源和一个Slave 随机数据源作为基础
 *
 * @ClassName: MasterSlaveDataSourceFactory
 * @author thierry.fu
 * @date Jan 11, 2016 10:47:26 PM
 */
public class MasterSlaveDataSourceFactory implements DataSourceFactory {

  private DataSourceFactory masters = new SimpleDataSourceFactory();

  private DataSourceFactory slaves = new RandomDataSourceFactory();

  public MasterSlaveDataSourceFactory() {
  }

  /**
   *
   * @param master
   * @param slaves
   * @param queryFromMaster true代表允许从master数据源查询数据
   */
  public MasterSlaveDataSourceFactory(DataSource master,
      List<DataSource> slaves, boolean queryFromMaster) {
    if (queryFromMaster && !CollectionUtils.containsInstance(slaves, master)) {
      slaves = new ArrayList<DataSource>(slaves);
      slaves.add(master);
    }
    setSlaves(new RandomDataSourceFactory(slaves));
    setMasters(new SimpleDataSourceFactory(master));
  }

  /**
   *
   * @param masters
   * @see RandomDataSourceFactory
   * @see SimpleDataSourceFactory
   */
  public void setMasters(DataSourceFactory masters) {
    this.masters = masters;
  }

  /**
   *
   * @param slaves
   * @see RandomDataSourceFactory
   */
  public void setSlaves(DataSourceFactory slaves) {
    this.slaves = slaves;
  }

  @Override
  public DataSourceHolder getHolder(SqlType sqlType) {
    if (sqlType != SqlType.READ) {
      return masters.getHolder(sqlType);
    }
    else {
      return slaves.getHolder(sqlType);
    }
  }
}
