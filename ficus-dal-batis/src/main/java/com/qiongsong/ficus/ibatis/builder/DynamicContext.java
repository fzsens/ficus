package com.qiongsong.ficus.ibatis.builder;

import java.util.HashMap;
import java.util.Map;

import com.qiongsong.ficus.dal.exceptions.BuilderException;

/**
 *
 * @ClassName: DynamicContext
 * @author thierry.fu
 * @date Jan 7, 2016 10:29:17 PM
 */
public class DynamicContext {

  private final Map<String, Object> bindings;
  private final StringBuilder sqlBuilder = new StringBuilder();
  private int uniqueNumber = 0;

  @SuppressWarnings({ "rawtypes", "unchecked" })
  public DynamicContext(Object parameterObject) {
    bindings = new HashMap<String, Object>();
    if (parameterObject != null && !(parameterObject instanceof Map)) {
      throw new BuilderException("参数错误");
    }
    if (parameterObject instanceof Map) {
      bindings.putAll((Map) parameterObject);
    }
  }

  public Map<String, Object> getBindings() {
    return bindings;
  }

  public void bind(String name, Object value) {
    bindings.put(name, value);
  }

  public void appendSql(String sql) {
    sqlBuilder.append(sql);
    sqlBuilder.append(" ");
  }

  public String getSql() {
    return sqlBuilder.toString().trim();
  }

  public int getUniqueNumber() {
    return uniqueNumber++;
  }

}
