package com.qiongsong.ficus.dal.dialect;

import java.util.regex.Pattern;

/**
 * provides basic commonly functions for dialect. such as get count .
 *
 * @ClassName: Dialect
 * @author thierry.fu
 * @date Jan 4, 2016 9:00:22 PM
 */
public abstract class CommonDialect {

  protected final Pattern orderByPattern = Pattern.compile("order\\s*by");

  protected boolean countIngnoreOrderBy = false;

  public boolean isCountIngnoreOrderBy() {
    return countIngnoreOrderBy;
  }

  public void setCountIngnoreOrderBy(boolean countIngnoreOrderBy) {
    this.countIngnoreOrderBy = countIngnoreOrderBy;
  }

  public String getCountString(String querySql) {
    return "select count(1) from (" + removeOrderBy(querySql) + ") count_";
  }

  protected String removeOrderBy(String querySql) {
    String[] splitedSql = orderByPattern.split(querySql);
    if (2 != splitedSql.length) {
      return querySql;
    } else {
      return splitedSql[0];
    }
  }

}
