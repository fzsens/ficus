package com.qiongsong.ficus.dal.jdbc;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.qiongsong.ficus.dal.NestedSql;
import com.qiongsong.ficus.dal.SqlFactory;
import com.qiongsong.ficus.dal.datasource.SqlType;
import com.qiongsong.ficus.dal.dialect.Dialect;
import com.qiongsong.ficus.dal.dialect.MsSQLServerDialect;
import com.qiongsong.ficus.dal.dialect.MySQLDialect;
import com.qiongsong.ficus.dal.dialect.OracleDialect;
import com.qiongsong.ficus.dal.jdbc.access.DataAccess;
import com.qiongsong.ficus.dal.jdbc.access.DataAccessFactory;
import com.qiongsong.ficus.dal.jdbc.support.BeanPropertyRowMapper;
import com.qiongsong.ficus.dal.support.BaseObject;
import com.qiongsong.ficus.dal.support.PagingInfo;
import com.qiongsong.ficus.dal.support.handler.DefaultNameHandler;
import com.qiongsong.ficus.dal.support.handler.NameHandler;

/**
 *
 * @ClassName: AbstractJdbcDaoImpl
 * @author thierry.fu
 * @date Jan 8, 2016 3:58:31 PM
 */
public abstract class AbstractJdbcDaoImpl {

  protected DataAccessFactory dataAccessFactory;

  protected Dialect dialect;

  protected NameHandler nameHandler;

  protected SqlFactory sqlFactory;

  public NameHandler getNameHandler() {
    return null == nameHandler ? new DefaultNameHandler() : nameHandler;
  }

  public void setNameHandler(NameHandler nameHandler) {
    this.nameHandler = nameHandler;
  }

  public DataAccessFactory getDataAccessFactory() {
    return dataAccessFactory;
  }

  public void setDataAccessFactory(DataAccessFactory dataAccessFactory) {
    this.dataAccessFactory = dataAccessFactory;
  }

  /**
   * for best performance consider using a custom RowMapper
   * @param clazz
   * @return
   */
  protected <T> RowMapper<T> getRowMapper(Class<T> clazz) {
    return BeanPropertyRowMapper.newInstance(clazz);
  }

  public void setDialect(Dialect dialect) {
    this.dialect = dialect;
  }

  /**
   * 根据读写库类型，获取
   * @param sqlType
   * @return
   */
  public Dialect getDialect(SqlType sqlType) {
    if (null == dialect) {
      String dataBaseName = "";
      try {
        dataBaseName = getDataAccess(sqlType).getDataSource().getConnection()
            .getMetaData().getDatabaseProductName().toUpperCase();
      }
      catch (SQLException e) {
        throw new NullPointerException(" dialect is null cause :");
      }
      if (dataBaseName.equals("MYSQL")) {
        this.dialect = new MySQLDialect();
      }
      else if (dataBaseName.equals("ORACLE")) {
        this.dialect = new OracleDialect();
      }
      else if (dataBaseName.equals("MSSERVERSQL")) {
        this.dialect = new MsSQLServerDialect();
      }
    }

    return dialect;
  }

  public SqlFactory getSqlFactory() {
    return sqlFactory;
  }

  public void setSqlFactory(SqlFactory sqlFactory) {
    this.sqlFactory = sqlFactory;
  }

  /**
   * 获取分页信息
   * @param querySql
   * @param pagingInfo
   * @return
   */
  protected String getLimitString(NestedSql querySql,
      PagingInfo pagingInfo) {
    return getLimitString(querySql.getSql(), pagingInfo);
  }

  protected String getLimitString(String querySql,
      PagingInfo pagingInfo) {
    if (null == pagingInfo) {
      return querySql;
    }
    else {
      return getDialect(SqlType.READ).getLimitString(querySql,
          pagingInfo.getOffset(), pagingInfo.getLimit());
    }
  }

  /**
   * 获取dataAccess.
   * @param nesedSql
   * @return
   */
  protected DataAccess getDataAccess(NestedSql nestedSql) {
    if (dataAccessFactory == null) {
      throw new NullPointerException("the dataAccessFactory can not be Null");
    }
    return dataAccessFactory.getDataAccess(nestedSql.getSqlType());
  }

  protected DataAccess getDataAccess(SqlType sqlType) {
    if (dataAccessFactory == null) {
      throw new NullPointerException("the dataAccessFactory can not be Null");
    }
    return dataAccessFactory.getDataAccess(sqlType);
  }

  protected int update(NestedSql nestedSql) {
    DataAccess dataAccess = getDataAccess(nestedSql);
    return dataAccess.update(nestedSql.getSql(), nestedSql.getParameters());
  }

  protected <T extends BaseObject> List<T> query(NestedSql nestedSql,
      RowMapper<T> rowMapper) {
    DataAccess dataAccess = getDataAccess(nestedSql);
    return dataAccess.query(nestedSql.getSql(), nestedSql.getParameters(),
        rowMapper);
  }

  protected <T extends BaseObject> List<T> query(NestedSql nestedSql,
      PagingInfo pagingInfo, RowMapper<T> rowMapper) {
    DataAccess dataAccess = getDataAccess(nestedSql);
    return dataAccess.query(getLimitString(nestedSql, pagingInfo),
        nestedSql.getParameters(), rowMapper);
  }

  protected List<Map<String, Object>> queryForList(NestedSql nestedSql) {
    DataAccess dataAccess = getDataAccess(nestedSql);
    return dataAccess.queryForList(nestedSql.getSql(),
        nestedSql.getParameters());
  }

  protected List<Map<String, Object>> queryForList(NestedSql nestedSql,
      PagingInfo pagingInfo) {
    DataAccess dataAccess = getDataAccess(nestedSql);
    return dataAccess.queryForList(getLimitString(nestedSql, pagingInfo),
        nestedSql.getParameters());
  }

  <T extends BaseObject> T queryForObject(NestedSql nestedSql,
      RowMapper<T> rowMapper) {
    DataAccess dataAccess = getDataAccess(nestedSql);
    return dataAccess.queryForObject(nestedSql.getSql(),
        nestedSql.getParameters(), rowMapper);
  }

  protected int[] batchUpdate(String sql, List<Object[]> params) {
    DataAccess dataAccess = getDataAccess(SqlType.WRITE);
    return dataAccess.batchUpdate(sql, params);
  }

}
