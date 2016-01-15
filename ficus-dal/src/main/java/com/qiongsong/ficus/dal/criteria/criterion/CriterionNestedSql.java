package com.qiongsong.ficus.dal.criteria.criterion;

import java.util.List;

import com.qiongsong.ficus.dal.NestedSql;
import com.qiongsong.ficus.dal.datasource.AbstractNestedSql;
import com.qiongsong.ficus.dal.datasource.SqlType;

/**
 * <NestedSql> 's criteria implement
 *
 * @ClassName: CriterionNestedSql
 * @author thierry.fu
 * @date Dec 30, 2015 1:35:31 PM
 */
public class CriterionNestedSql extends AbstractNestedSql implements NestedSql {

  private String sql;

  private Object[] parameters;

  // 默认为自动判断
  private SqlType sqlType = SqlType.AUTO;

  public CriterionNestedSql(String sql, Object[] parameters) {
    super();
    this.sql = sql;
    this.parameters = parameters;
  }

  public CriterionNestedSql(String sql, List<Object> parameters) {
    super();
    this.sql = sql;
    this.parameters = parameters.toArray();
  }

  @SuppressWarnings("unused")
  private CriterionNestedSql(String sql, List<Object> parameters,
      SqlType sqlType) {
    super();
    this.sql = sql;
    this.parameters = parameters.toArray();
    this.sqlType = sqlType;
  }

  @SuppressWarnings("unused")
  private CriterionNestedSql(String sql, Object[] parameters, SqlType sqlType) {
    super();
    this.sql = sql;
    this.parameters = parameters;
    this.sqlType = sqlType;
  }

  public CriterionNestedSql(String sql) {
    super();
    this.sql = sql;
    this.parameters = new Object[0];
  }

  public void setSql(String sql) {
    this.sql = sql;
  }

  public void setParameters(Object[] parameters) {
    this.parameters = parameters;
  }

  @Override
  public String getSql() {
    return sql;
  }

  @Override
  public Object[] getParameters() {
    return null == parameters ? new Object[] {} : parameters;
  }

  @Override
  public String toString() {
    return this.getSql();
  }

  @Override
  public SqlType getSqlType() {
    return resolveSqlType(this.sqlType, this.sql);
  }

}
