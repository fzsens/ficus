package com.qiongsong.ficus.dal.dialect;

/**
 *
 * @ClassName: MySQLDialect
 * @author thierry.fu
 * @date Jan 4, 2016 10:28:26 PM
 */
public class MySQLDialect extends CommonDialect implements Dialect {

  @Override
  public String getLimitString(String querySql, int offset, int limit) {
    if (offset > 0) {
      return querySql + " limit " + offset + "," + limit;
    }
    return querySql + " limit " + limit;
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
    return MySQLDialect.class.getName();
  }

}
