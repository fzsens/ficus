package com.qiongsong.ficus.ibatis.xml;

import java.util.Arrays;
import java.util.List;

import com.qiongsong.ficus.ibatis.builder.Configuration;

/**
 * @author Clinton Begin
 */
public class WhereSqlNode extends TrimSqlNode {

  private static List<String> prefixList = Arrays.asList("AND ", "OR ", "AND\n", "OR\n", "AND\r", "OR\r", "AND\t",
      "OR\t");

  public WhereSqlNode(Configuration configuration, SqlNode contents) {
    super(configuration, contents, "WHERE", prefixList, null, null);
  }

}
