package com.qiongsong.ficus.ibatis.builder;

import com.qiongsong.ficus.ibatis.xml.SqlNode;

/**
 *
 * @ClassName: RawSqlSource
 * @author thierry.fu
 * @date Jan 7, 2016 10:34:48 PM
 */
public class RawSqlSource implements SqlSource {

  private final SqlSource sqlSource;

  public RawSqlSource(Configuration configuration, SqlNode rootSqlNode) {
    this(configuration, getSql(rootSqlNode));
  }

  public RawSqlSource(Configuration configuration, String sql) {
    SqlSourceBuilder sqlSourceParser = new SqlSourceBuilder(configuration);
    sqlSource = sqlSourceParser.parse(sql);
  }

  private static String getSql(SqlNode rootSqlNode) {
    DynamicContext context = new DynamicContext(null);
    rootSqlNode.apply(context);
    return context.getSql();
  }

  public BoundSql getBoundSql(Object parameterObject) {
    return sqlSource.getBoundSql(parameterObject);
  }

}
