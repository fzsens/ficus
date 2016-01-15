package com.qiongsong.ficus.ibatis.builder;

import java.util.ArrayList;
import java.util.List;

import com.qiongsong.ficus.dal.support.parser.GenericTokenParser;
import com.qiongsong.ficus.dal.support.parser.TokenHandler;

/**
 *
 * @ClassName: SqlSourceBuilder
 * @author thierry.fu
 * @date Jan 7, 2016 10:32:10 PM
 */
public class SqlSourceBuilder extends BaseBuilder {

  public SqlSourceBuilder(Configuration configuration) {
    super(configuration);
  }

  public SqlSource parse(String originalSql) {
    ParameterMappingTokenHandler handler = new ParameterMappingTokenHandler();
     //解析各个参数条件。
    GenericTokenParser parser = new GenericTokenParser("#{", "}", handler);
    String sql = parser.parse(originalSql);
    //静态的SqlSource，SQL 和 getParameterMappings 得到的参数
    return new StaticSqlSource(sql, handler.getParameterMappings());
  }

  private static class ParameterMappingTokenHandler implements TokenHandler {

    private List<ParameterMapping> parameterMappings = new ArrayList<ParameterMapping>();

    public List<ParameterMapping> getParameterMappings() {
      return parameterMappings;
    }
     //将各个参数解析成为?,并添加参数
    public String handleToken(String content) {
      parameterMappings.add(buildParameterMapping(content));
      return "?";
    }

    private ParameterMapping buildParameterMapping(String content) {
      ParameterMapping.Builder builder = new ParameterMapping.Builder(content);
      return builder.build();
    }
  }

}
