package com.qiongsong.ficus.dal.criteria.build;

import java.util.ArrayList;
import java.util.List;

import com.qiongsong.ficus.dal.NestedSql;
import com.qiongsong.ficus.dal.criteria.Builder;
import com.qiongsong.ficus.dal.criteria.criterion.Criterion;
import com.qiongsong.ficus.dal.criteria.criterion.CriterionNestedSql;
import com.qiongsong.ficus.dal.criteria.criterion.Predicate;
import com.qiongsong.ficus.dal.exceptions.BuilderException;
import com.qiongsong.ficus.dal.support.handler.NameHandler;

/**
 * QueryBuilder create select sql
 *
 * @ClassName: QueryBuilder
 * @author thierry.fu
 * @date Dec 31, 2015 2:24:55 PM
 */
public class QueryBuilder extends AbstractSqlBuilder {

  protected List<String> includeFields;

  protected List<String> excludeFields;

  protected List<Criterion> funcCriterions;

  protected Builder whereBuilder;

  protected Builder orderByBuilder;

  protected boolean isExclusion = false;

  protected boolean isOrderBy = false;

  public QueryBuilder() {
    includeFields = new ArrayList<>();
    excludeFields = new ArrayList<>();
    funcCriterions = new ArrayList<>();
    whereBuilder = new WhereBuilder();
    orderByBuilder = new OrderBuilder();
  }

  /**
   * query fields and order by fields.
   */
  @Override
  public void addCriterion(Predicate predic, String relation, String property,
      String op, Object value) {
    if (predic == Predicate.INCLUDE) {
      includeFields.add(property);
    }
    else if (predic == Predicate.EXCLUDE) {
      excludeFields.add(property);
    }
    else if (predic == Predicate.ORDER_BY_ASC) {
      orderByBuilder.addCriterion(predic, relation, property, op, value);
    }
    else if (predic == Predicate.ORDER_BY_DESC) {
      orderByBuilder.addCriterion(predic, relation, property, op, value);
    }
    else if (predic == Predicate.FUNC) {
      /**
       * TODO process function and parse function parameters.
       */
      this.isExclusion = Boolean.valueOf(op);
      this.isOrderBy = Boolean.valueOf(relation);
      Criterion criterion = buildCriterion(predic, relation, property, op,
          value);
      this.funcCriterions.add(criterion);
    }
    else {
      throw new BuilderException("Unsupport Predicate type setting.");
    }
  }

  @Override
  public void addCondition(Predicate predic, String relation, String property,
      String op, Object value) {
    whereBuilder.addCondition(predic, relation, property, op, value);
  }

  @Override
  public NestedSql build(Class<?> clazz, Object entity, boolean isIgnoreNull,
      NameHandler nameHandler) {
    /**
     * merge entity fields
     */
    mergeEntityFields(entity, Predicate.WHERE, isIgnoreNull);
    whereBuilder.getCriterions().putAll(this.getCriterions());
    /**
     * 处理查询语句忽略version
     */
    whereBuilder.getCriterions().remove(VERSION);
    String tableName = nameHandler.getTableName(clazz);
    StringBuilder sb = new StringBuilder(SELECT);
    if (columnFields.isEmpty() && !isExclusion) {
      this.fetchClassFields(clazz);
    }
    /**
     * parse
     */
    if (!funcCriterions.isEmpty()) {
      for (Criterion criterion : funcCriterions) {
        String nativeFieldName = tokenParse(criterion.getPropertyName(), clazz,
            nameHandler);
        sb.append(nativeFieldName).append(COMMA);
      }
    }
    if (!isExclusion) {
      for (String columnField : columnFields) {
        // white and black list.
        if (!includeFields.isEmpty() && !includeFields.contains(columnField)) {
          continue;
        }
        else if (!excludeFields.isEmpty()
            && excludeFields.contains(columnField)) {
          continue;
        }
        String column = nameHandler.getColumnName(clazz, columnField);
        sb.append(applyColumnAlias(column));
        sb.append(COMMA);
      }
    }
    sb.deleteCharAt(sb.length() - 1);
    sb.append(FROM).append(applyTableAlias(tableName)).append(BLANK_);

    whereBuilder.setTableAlias(getTableAlias());
    NestedSql whereSql = whereBuilder.build(clazz, entity, isIgnoreNull,
        nameHandler);
    sb.append(whereSql.getSql());
    if (isOrderBy) {
      orderByBuilder.setTableAlias(getTableAlias());
      NestedSql orderByBoundSql = orderByBuilder.build(clazz, entity,
          isIgnoreNull, nameHandler);
      sb.append(orderByBoundSql.getSql());
    }
    return new CriterionNestedSql(sb.toString(), whereSql.getParameters());
  }

}
