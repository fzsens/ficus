package com.qiongsong.ficus.dal;

import java.util.Map;

import com.qiongsong.ficus.dal.support.BaseQueryCondition;

/**
 * 获取SQL来源接口
 * @ClassName: SqlFactory
 * @author thierry.fu
 * @date Jan 11, 2016 4:29:14 PM
 */
public interface SqlFactory {

  NestedSql getBoundSql(String refSql, String expectParamKey,
      Object[] parameters);

  NestedSql getBoundSql(String refSql, Map<String, Object> parameters);

  /**
   * 使用条件匹配模式获取查询语句
   * @param condition
   * @return
   */
  NestedSql getBoundSql(BaseQueryCondition condition);

}
