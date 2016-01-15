/*
 * Copyright 2009-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License i distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.qiongsong.ficus.dal.jdbc.datasource.support;

import javax.sql.DataSource;

import com.qiongsong.ficus.dal.datasource.SqlType;
import com.qiongsong.ficus.dal.jdbc.datasource.DataSourceFactory;
import com.qiongsong.ficus.dal.jdbc.datasource.DataSourceHolder;

/**
 * 当你的应用程序只需要一个DataSource时候使用这个实现即可！
 * @ClassName: SimpleDataSourceFactory
 * @author thierry.fu
 * @date Jan 11, 2016 10:47:50 PM
 */
public class SimpleDataSourceFactory implements DataSourceFactory {

  private DataSourceHolder dataSource;

  /**
   * 构造候还得继续调用 {@link #setDataSource(DataSource)} 设置数据源，谢谢
   */
  public SimpleDataSourceFactory() {
  }

  /**
   * 提供的数据源要求非null
   *
   * @param dataSource
   */
  public SimpleDataSourceFactory(DataSource dataSource) {
    setDataSource(dataSource);
  }

  /**
   * 设置数据源。
   * <p>
   * 提供的数据源要求非null
   *
   * @param dataSource
   */
  public void setDataSource(DataSource dataSource) {
    if (dataSource == null) {
      throw new NullPointerException("dataSource");
    }
    this.dataSource = new DataSourceHolder(dataSource);
  }

  @Override
  public DataSourceHolder getHolder(SqlType sqlType) {
    return dataSource;
  }

}
