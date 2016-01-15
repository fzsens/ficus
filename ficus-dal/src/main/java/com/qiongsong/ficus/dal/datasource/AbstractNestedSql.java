package com.qiongsong.ficus.dal.datasource;

import java.util.Arrays;
import java.util.regex.Pattern;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.qiongsong.ficus.dal.NestedSql;

/**
 * 添加SQL语句读写类型判断
 * @ClassName: AbstractNestedSql
 * @author thierry.fu
 * @date Jan 9, 2016 4:02:19 PM
 */
public abstract class AbstractNestedSql implements NestedSql {

  private static Pattern[] SELECT_PATTERNS = new Pattern[] {
      Pattern.compile("^\\s*SELECT.*", Pattern.CASE_INSENSITIVE),
      Pattern.compile("^\\s*GET.*", Pattern.CASE_INSENSITIVE),
      Pattern.compile("^\\s*FIND.*", Pattern.CASE_INSENSITIVE),
      Pattern.compile("^\\s*READ.*", Pattern.CASE_INSENSITIVE),
      Pattern.compile("^\\s*QUERY.*", Pattern.CASE_INSENSITIVE),
      Pattern.compile("^\\s*COUNT.*", Pattern.CASE_INSENSITIVE),
      Pattern.compile("^\\s*SHOW.*", Pattern.CASE_INSENSITIVE),
      Pattern.compile("^\\s*DESC.*", Pattern.CASE_INSENSITIVE),
      Pattern.compile("^\\s*DESCRIBE.*", Pattern.CASE_INSENSITIVE), };

  /**
   *
   * 解析得到SQL语句的读写类型
   * @param sql
   * @return
   */
  protected SqlType resolveSqlType(SqlType sqlType, String sql) {
    if (sqlType == SqlType.AUTO) {
      for (int i = 0; i < SELECT_PATTERNS.length; i++) {
        // 用正则表达式匹配 SELECT 语句
        if (SELECT_PATTERNS[i].matcher(sql).find()) {
          sqlType = SqlType.READ;
          break;
        }
      }
      if (sqlType == SqlType.AUTO) {
        sqlType = SqlType.WRITE;
      }
    }
    return sqlType;
  }

  // @Override
  // public boolean equals(Object obj) {
  // if (obj == null || !(obj instanceof NestedSql)) {
  // return false;
  // }
  // NestedSql comprionTarget = (NestedSql) obj;
  // return getSql().equals(comprionTarget.getSql())
  // && Arrays.equals(getParameters(), comprionTarget.getParameters())
  // && getSqlType().equals(comprionTarget.getSqlType());
  // }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || !(obj instanceof NestedSql)) {
      return false;
    }
    NestedSql comprionTarget = (NestedSql) obj;
    if (getSql() == null) {
      if (comprionTarget.getSql() != null)
        return false;
    }
    return getSql().equals(comprionTarget.getSql())
        && Arrays.equals(getParameters(), comprionTarget.getParameters())
        && getSqlType().equals(comprionTarget.getSqlType());
  }

  @Override
  public int hashCode() {
    HashCodeBuilder builder = new HashCodeBuilder();
    builder.append(getParameters());
    builder.append(getSql());
    builder.append(getSqlType());
    return builder.hashCode();
  }

}
