package com.qiongsong.ficus.ibatis.xml;

import com.qiongsong.ficus.ibatis.builder.DynamicContext;
import com.qiongsong.ficus.ibatis.ognl.ExpressionEvaluator;

/**
 * @author Clinton Begin
 */
public class IfSqlNode implements SqlNode {
  private ExpressionEvaluator evaluator;
  private String test;
  private SqlNode contents;

  public IfSqlNode(SqlNode contents, String test) {
    this.test = test;
    this.contents = contents;
    this.evaluator = new ExpressionEvaluator();
  }

  public boolean apply(DynamicContext context) {
    if (evaluator.evaluateBoolean(test, context.getBindings())) {
      contents.apply(context);
      return true;
    }
    return false;
  }

}
