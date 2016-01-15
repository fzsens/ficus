package com.qiongsong.ficus.ibatis.xml;

import com.qiongsong.ficus.ibatis.builder.DynamicContext;
import com.qiongsong.ficus.ibatis.ognl.OgnlCache;

/**
 * @author Clinton Begin
 */
public class VarDeclSqlNode implements SqlNode {

  private final String name;
  private final String expression;

  public VarDeclSqlNode(String var, String exp) {
    name = var;
    expression = exp;
  }

  public boolean apply(DynamicContext context) {
    final Object value = OgnlCache.getValue(expression, context.getBindings());
    context.bind(name, value);
    return true;
  }

}
