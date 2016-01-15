package com.qiongsong.ficus.dal.dialect;

/**
 *
 * @ClassName: OracleDialect
 * @author thierry.fu
 * @date Jan 4, 2016 10:33:19 PM
 */
public class OracleDialect extends CommonDialect implements Dialect {

  @Override
  public String getLimitString(String sql, int offset, int limit) {
    sql = sql.trim();
    boolean isForUpdate = false;
    if (sql.toLowerCase().endsWith(" for update")) {
      sql = sql.substring(0, sql.length() - 11);
      isForUpdate = true;
    }
    StringBuilder pageSql = new StringBuilder();
    pageSql.append("select * from ( select rownum rownum_,rows_.* from (");
    pageSql.append(sql);
    pageSql.append(") rows_ where rownum < ").append(offset);
    pageSql.append(") where rownum_ >= ").append(limit);
    if (isForUpdate) {
      pageSql.append(" for update");
    }
    return pageSql.toString();
  }

  /**
   * didn't need remove order by statement.
   */
  @Override
  protected String removeOrderBy(String querySql) {
    return countIngnoreOrderBy ? super.getCountString(querySql) : querySql;
  }

  @Override
  public String getDialectName() {
    return OracleDialect.class.getName();
  }

}
