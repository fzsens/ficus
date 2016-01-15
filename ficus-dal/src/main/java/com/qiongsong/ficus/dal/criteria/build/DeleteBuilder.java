package com.qiongsong.ficus.dal.criteria.build;

import com.qiongsong.ficus.dal.NestedSql;
import com.qiongsong.ficus.dal.criteria.Builder;
import com.qiongsong.ficus.dal.criteria.criterion.Criterion;
import com.qiongsong.ficus.dal.criteria.criterion.CriterionNestedSql;
import com.qiongsong.ficus.dal.criteria.criterion.Predicate;
import com.qiongsong.ficus.dal.support.handler.NameHandler;

/**
 * build delete sql
 *
 * @ClassName: DeleteBuilder
 * @author thierry.fu
 * @date Dec 29, 2015 8:42:35 PM
 */
public class DeleteBuilder extends AbstractSqlBuilder {

  private Builder whereBuilder;

  public DeleteBuilder() {
    this.whereBuilder = new WhereBuilder();
  }

  @Override
  public void addCriterion(Predicate predic, String relation, String property,
      String op, Object value) {
    this.addCondition(predic, relation, property, op, value);
  }

  @Override
  public void addCondition(Predicate predic, String relation, String property,
      String op, Object value) {
    Criterion criterion = buildCriterion(predic, relation, property, op, value);
    this.whereBuilder.getCriterions().put(property, criterion);
  }

  @Override
  public NestedSql build(Class<?> clazz, Object entity, boolean isIgnoreNull,
      NameHandler nameHandler) {
    mergeEntityFields(entity, Predicate.WHERE, isIgnoreNull);
    whereBuilder.setTableAlias(getTableAlias());
    whereBuilder.getCriterions().putAll(this.getCriterions());
    StringBuilder sb = new StringBuilder(DELETE);
    String tableName = nameHandler.getTableName(clazz);
    sb.append(applyTableAlias(tableName)).append(BLANK_);
    NestedSql whereSql = this.whereBuilder.build(clazz, entity, isIgnoreNull,
        nameHandler);
    sb.append(whereSql.getSql());
    return new CriterionNestedSql(sb.toString(), whereSql.getParameters());
  }

}
