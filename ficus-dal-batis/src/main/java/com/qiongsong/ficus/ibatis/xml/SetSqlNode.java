package com.qiongsong.ficus.ibatis.xml;

import java.util.Arrays;
import java.util.List;

import com.qiongsong.ficus.ibatis.builder.Configuration;

/**
 * @author Clinton Begin
 */
public class SetSqlNode extends TrimSqlNode {

  private static List<String> suffixList = Arrays.asList(",");

  public SetSqlNode(Configuration configuration, SqlNode contents) {
    super(configuration, contents, "SET", null, null, suffixList);
  }

}
