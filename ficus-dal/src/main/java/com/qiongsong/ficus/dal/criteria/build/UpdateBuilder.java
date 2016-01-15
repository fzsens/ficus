package com.qiongsong.ficus.dal.criteria.build;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.qiongsong.ficus.dal.NestedSql;
import com.qiongsong.ficus.dal.criteria.Builder;
import com.qiongsong.ficus.dal.criteria.criterion.Criterion;
import com.qiongsong.ficus.dal.criteria.criterion.CriterionNestedSql;
import com.qiongsong.ficus.dal.criteria.criterion.Predicate;
import com.qiongsong.ficus.dal.exceptions.BuilderException;
import com.qiongsong.ficus.dal.lock.Versionable;
import com.qiongsong.ficus.dal.support.handler.NameHandler;

/**
 * to build update sql statement
 *
 * @ClassName: UpdateBuilder
 * @author thierry.fu
 * @date Dec 28, 2015 11:00:55 PM
 */
public class UpdateBuilder extends AbstractSqlBuilder {

  protected Builder whereBuilder;

  public UpdateBuilder() {
    whereBuilder = new WhereBuilder();
  }

  /**
   * add update columns.
   */
  @Override
  public void addCriterion(Predicate predic, String relation, String property,
      String op, Object value) {
    Criterion criterion = buildCriterion(predic, relation, property, op, value);
    this.criterions.put(property, criterion);
  }

  @Override
  public void addCondition(Predicate predic, String relation, String property,
      String op, Object value) {
    this.whereBuilder.addCondition(predic, relation, property, op, value);
  }

  @Override
  public NestedSql build(Class<?> clazz, Object entity, boolean isIgnoreNull,
      NameHandler nameHandler) {
    mergeEntityFields(entity, Predicate.UPDATE, isIgnoreNull);
    if (!hasCriterions()) {
      throw new BuilderException(
          "update statement can not without criterions.");
    }
    String pk = nameHandler.getPkFieldName(clazz);
    Criterion pkCriterion = getCriterions().get(pk);
    if (pkCriterion != null) {
      // 不重复更新主键
      getCriterions().remove(pk);
      if (!whereBuilder.containsCriterion(pk)) {
        // didn't deal with array parameters.
        this.whereBuilder.getCriterions().put(pk, pkCriterion);
        this.whereBuilder.setTableAlias(getTableAlias());
      }
    }
    else {
      throw new BuilderException(
          "unsupport update operations, the primary key: " + pk
              + " of object is null.");
    }
    // OPTIMISTIC 包含锁处理
    if (entity instanceof Versionable) {
      int version = ((Versionable) entity).getVersion();
      Criterion versionCriterion = this.buildCriterion(Predicate.WHERE, "and",
          VERSION, "=", version);
      this.whereBuilder.getCriterions().put(VERSION, versionCriterion);
    }

    String tableName = nameHandler.getTableName(clazz);
    tableName = applyTableAlias(tableName);
    List<Object> params = new ArrayList<Object>();
    StringBuilder sb = new StringBuilder(UPDATE);
    sb.append(tableName).append(SET);
    for (Map.Entry<String, Criterion> entry : getCriterions().entrySet()) {
      String column = nameHandler.getColumnName(clazz, entry.getKey());
      column = applyColumnAlias(column);
      Criterion criterion = entry.getValue();
      if (criterion.isNativeField()) {
        String nativeFieldName = tokenParse(criterion.getPropertyName(), clazz,
            nameHandler);
        String nativeValue = tokenParse(String.valueOf(criterion.getValue()),
            clazz, nameHandler);
        sb.append(nativeFieldName).append(" = ").append(nativeValue)
            .append(",");
      }
      else if (criterion.getValue() == null) {
        sb.append(column).append(" = NULL,");
      }
      else {
        sb.append(column).append(" = ?,");
        params.add(criterion.getValue());
      }
    }
    sb.deleteCharAt(sb.length() - 1);
    NestedSql whereSql = whereBuilder.build(clazz, entity, isIgnoreNull,
        nameHandler);
    sb.append(BLANK_).append(whereSql.getSql());
    params.addAll(Arrays.asList(whereSql.getParameters()));
    return new CriterionNestedSql(sb.toString(), params);
  }

}
