package com.qiongsong.ficus.ibatis.xml;

import java.util.List;

import com.qiongsong.ficus.ibatis.builder.DynamicContext;

/**
 * @author Clinton Begin
 */
public class ChooseSqlNode implements SqlNode {
  private SqlNode defaultSqlNode;
  private List<SqlNode> ifSqlNodes;

  public ChooseSqlNode(List<SqlNode> ifSqlNodes, SqlNode defaultSqlNode) {
    this.ifSqlNodes = ifSqlNodes;
    this.defaultSqlNode = defaultSqlNode;
  }

  public boolean apply(DynamicContext context) {
    for (SqlNode sqlNode : ifSqlNodes) {
      if (sqlNode.apply(context)) {
        return true;
      }
    }
    if (defaultSqlNode != null) {
      defaultSqlNode.apply(context);
      return true;
    }
    return false;
  }
}
