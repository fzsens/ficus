package com.qiongsong.ficus.ibatis;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.qiongsong.ficus.commons.untils.ClassUtils;
import com.qiongsong.ficus.dal.NestedSql;
import com.qiongsong.ficus.dal.SqlFactory;
import com.qiongsong.ficus.dal.support.BaseQueryCondition;
import com.qiongsong.ficus.ibatis.builder.Configuration;
import com.qiongsong.ficus.ibatis.builder.MappedStatement;

/**
 * 用于拓展Mybatis模式，作为SQL获取的工厂类
 *
 * @ClassName: MyBatisSqlFactory
 * @author thierry.fu
 * @date Jan 7, 2016 9:24:43 AM
 */
public class MyBatisSqlFactory implements SqlFactory {

  private Configuration configuration;

  public MyBatisSqlFactory(Configuration configuration) {
    this.configuration = configuration;
  }

  public Configuration getConfiguration() {
    return this.configuration;
  }

  @Override
  public NestedSql getBoundSql(String refSql, String expectParamKey,
      Object[] parameters) {
    Map<String, Object> params = this.processParameters(expectParamKey,
        parameters);
    MappedStatement mappedStatement = this.configuration.getMappedStatements()
        .get(refSql);
    return mappedStatement.getSqlSource().getBoundSql(params);
  }

  @Override
  public NestedSql getBoundSql(String refSql, Map<String, Object> parameters) {
    MappedStatement mappedStatement = this.configuration.getMappedStatements()
        .get(refSql);
    return mappedStatement.getSqlSource().getBoundSql(parameters);
  }

  @Override
  public NestedSql getBoundSql(BaseQueryCondition condition) {
    Map<String, Object> parameters = ClassUtils.getBeanPropMap(condition);
    return getBoundSql(condition.getQueryName(), parameters);
  }

  /**
   * 处理转换参数
   *
   * @param expectParamKey
   * @param parameters
   * @return
   */
  private Map<String, Object> processParameters(String expectParamKey,
      Object[] parameters) {

    if (ArrayUtils.isEmpty(parameters)) {
      return null;
    }
    String paramKey = StringUtils.isBlank(expectParamKey) ? "item"
        : expectParamKey;
    Map<String, Object> map = new HashMap<String, Object>();
    if (parameters.length == 1) {
      map.put(paramKey, parameters[0]);
    }
    else {
      map.put(paramKey, parameters);
    }
    return map;
  }

}
