package com.qiongsong.ficus.ibatis.ognl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ognl.Ognl;
import ognl.OgnlException;

/**
 * @author Clinton Begin
 */
public final class OgnlCache {

  private static final Map<String, Object> expressionCache = new ConcurrentHashMap<String, Object>();

  private OgnlCache() {
    // Prevent Instantiation of Static Class
  }

  public static Object getValue(String expression, Object root) {
    try {
      return Ognl.getValue(parseExpression(expression), root);
    } catch (OgnlException e) {
      throw new UnsupportedOperationException("Error evaluating expression '" + expression + "'. Cause: " + e, e);
    }
  }

  private static Object parseExpression(String expression) throws OgnlException {
    Object node = expressionCache.get(expression);
    if (node == null) {
      node = Ognl.parseExpression(expression);
      expressionCache.put(expression, node);
    }
    return node;
  }

}
