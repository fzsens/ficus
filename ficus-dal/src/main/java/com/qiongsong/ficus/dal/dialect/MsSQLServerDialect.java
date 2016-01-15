package com.qiongsong.ficus.dal.dialect;

/**
 * only support MS SQL SERVER 2005 and above.
 *
 * @ClassName: MsSQLServerDialect
 * @author thierry.fu
 * @date Jan 4, 2016 10:32:41 PM
 */
public class MsSQLServerDialect extends CommonDialect implements Dialect {

  private static String getOrderByPart(String sql) {
    String loweredString = sql.toLowerCase();
    int orderByIndex = loweredString.indexOf("order by");
    if (orderByIndex != -1) {
      return sql.substring(orderByIndex);
    }
    return "";
  }

  @Override
  public String getLimitString(String querySql, int offset, int limit) {
    StringBuilder pagingBuilder = new StringBuilder();
    String orderby = getOrderByPart(querySql);
    String distinctStr = "";

    String loweredString = querySql.toLowerCase();
    String sqlPartString = querySql;
    if (loweredString.trim().startsWith("select")) {
      int index = 6;
      if (loweredString.startsWith("select distinct")) {
        distinctStr = "DISTINCT ";
        index = 15;
      }
      sqlPartString = sqlPartString.substring(index);
    }
    pagingBuilder.append(sqlPartString);

    if ((orderby == null) || (orderby.length() == 0)) {
      orderby = "ORDER BY CURRENT_TIMESTAMP";
    }

    StringBuffer result = new StringBuffer();
    result.append("WITH query AS (SELECT ").append(distinctStr).append("TOP 100 PERCENT ")
        .append(" ROW_NUMBER() OVER (").append(orderby).append(") as __row_number__, ").append(pagingBuilder)
        .append(") SELECT * FROM query WHERE __row_number__ BETWEEN ").append(offset + 1).append(" AND ")
        .append(offset + limit).append(" ORDER BY __row_number__");

    return result.toString();
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
    return MsSQLServerDialect.class.getName();
  }

}
