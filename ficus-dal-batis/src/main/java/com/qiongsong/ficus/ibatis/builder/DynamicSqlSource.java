package com.qiongsong.ficus.ibatis.builder;

import java.util.Map;

import com.qiongsong.ficus.ibatis.xml.SqlNode;

/**
 *
 * @ClassName: DynamicSqlSource
 * @author Clinton Begin
 * @author liyd
 * @author thierry.fu
 * @date Jan 7, 2016 10:31:28 PM
 */
public class DynamicSqlSource implements SqlSource {

  private Configuration configuration;
  private SqlNode rootSqlNode;

  public DynamicSqlSource(Configuration configuration, SqlNode rootSqlNode) {
    this.configuration = configuration;
    this.rootSqlNode = rootSqlNode;
  }

  public BoundSql getBoundSql(Object parameterObject) {
    DynamicContext context = new DynamicContext(parameterObject);
    rootSqlNode.apply(context);
    SqlSourceBuilder sqlSourceParser = new SqlSourceBuilder(configuration);
    SqlSource sqlSource = sqlSourceParser.parse(context.getSql());
    BoundSql boundSql = sqlSource.getBoundSql(parameterObject);
    for (Map.Entry<String, Object> entry : context.getBindings().entrySet()) {
      boundSql.setAdditionalParameter(entry.getKey(), entry.getValue());
    }
    return boundSql;
  }

}
