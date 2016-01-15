package com.qiongsong.ficus.ibatis.xml;

import java.util.List;

import com.qiongsong.ficus.ibatis.builder.DynamicContext;

/**
 * @author Clinton Begin
 */
public class MixedSqlNode implements SqlNode {
  private List<SqlNode> contents;

  public MixedSqlNode(List<SqlNode> contents) {
    this.contents = contents;
  }

  public boolean apply(DynamicContext context) {
    for (SqlNode sqlNode : contents) {
      sqlNode.apply(context);
    }
    return true;
  }
}
