package com.qiongsong.ficus.ibatis.xml;

import com.qiongsong.ficus.ibatis.builder.DynamicContext;

/**
 * @author Clinton Begin
 */
public class StaticTextSqlNode implements SqlNode {
  private String text;

  public StaticTextSqlNode(String text) {
    this.text = text;
  }

  public boolean apply(DynamicContext context) {
    context.appendSql(text);
    return true;
  }

}
