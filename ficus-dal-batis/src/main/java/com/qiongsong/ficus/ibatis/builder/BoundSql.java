package com.qiongsong.ficus.ibatis.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.qiongsong.ficus.dal.NestedSql;
import com.qiongsong.ficus.dal.datasource.AbstractNestedSql;
import com.qiongsong.ficus.dal.datasource.SqlType;
import com.qiongsong.ficus.ibatis.meta.MetaObject;

/**
 *
 * @ClassName: BoundSql
 * @author thierry.fu
 * @date Jan 7, 2016 10:29:05 PM
 */
public class BoundSql extends AbstractNestedSql implements NestedSql {

  private String sql;

  private List<ParameterMapping> parameterMappings;

  // private Object parameterObject;
  private Map<String, Object> additionalParameters;

  private MetaObject metaParameters;

  // 默认为自动判断
  private SqlType sqlType = SqlType.AUTO;

  public BoundSql(String sql, List<ParameterMapping> parameterMappings) {
    this.sql = sql;
    this.parameterMappings = parameterMappings;
    this.additionalParameters = new HashMap<String, Object>();
    this.metaParameters = MetaObject.forObject(additionalParameters);
  }

  public Object[] getParameters() {
    List<Object> params = new ArrayList<Object>();
    if (parameterMappings == null) {
      return params.toArray();
    }
    for (ParameterMapping parameterMapping : parameterMappings) {
      String property = parameterMapping.getProperty();
      Object value = getAdditionalParameter(property);
      params.add(value);
    }
    return params.toArray();
  }

  public String getSql() {
    return sql;
  }

  public void setAdditionalParameter(String name, Object value) {
    metaParameters.setValue(name, value);
  }

  public Object getAdditionalParameter(String name) {
    return metaParameters.getValue(name);
  }

  @Override
  public SqlType getSqlType() {
    return resolveSqlType(this.sqlType, this.sql);
  }
}
