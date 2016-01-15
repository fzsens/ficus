package com.qiongsong.ficus.dal.criteria;

import com.qiongsong.ficus.dal.NestedSql;
import com.qiongsong.ficus.dal.criteria.build.DeleteBuilder;
import com.qiongsong.ficus.dal.criteria.build.InsertBuilder;
import com.qiongsong.ficus.dal.criteria.build.QueryBuilder;
import com.qiongsong.ficus.dal.criteria.build.UpdateBuilder;
import com.qiongsong.ficus.dal.criteria.criterion.Predicate;
import com.qiongsong.ficus.dal.support.handler.DefaultNameHandler;
import com.qiongsong.ficus.dal.support.handler.NameHandler;

/**
 * beginner of criteria operations.
 *
 * @ClassName: Criteria
 * @author thierry.fu
 * @date Dec 28, 2015 10:28:09 PM
 */
public class Criteria {

  /**
   * target object
   */
  private Class<?> entityClass;

  /**
   * criteria implements for QueryCriteria/DeleteCriteria/UpdateCriteria
   */
  private Builder builder;

  /**
   * private constructor
   *
   * @param clazz
   * @param sqlBuilder
   */
  private Criteria(Class<?> clazz, Builder builder) {
    this.entityClass = clazz;
    this.builder = builder;
  }

  public static Criteria select(Class<?> clazz) {
    return new Criteria(clazz, new QueryBuilder());
  }

  public static Criteria insert(Class<?> clazz) {
    return new Criteria(clazz, new InsertBuilder());
  }

  public static Criteria update(Class<?> clazz) {
    return new Criteria(clazz, new UpdateBuilder());
  }

  public static Criteria delete(Class<?> clazz) {
    return new Criteria(clazz, new DeleteBuilder());
  }

  public Criteria tableAlias(String alias) {
    this.builder.setTableAlias(alias);
    return this;
  }

  /**
   * white list.
   *
   * @param columns
   * @return
   */
  public Criteria include(String... columns) {
    for (String column : columns) {
      this.builder.addCriterion(Predicate.INCLUDE, null, column, null, null);
    }
    return this;
  }

  /**
   * black list.
   *
   * @param columns
   * @return
   */
  public Criteria exclude(String... columns) {
    for (String column : columns) {
      this.builder.addCriterion(Predicate.EXCLUDE, null, column, null, null);
    }
    return this;
  }

  public Criteria asc(String... columns) {
    for (String column : columns) {
      this.builder.addCriterion(Predicate.ORDER_BY_ASC, null, column, "ASC",
          null);
    }
    return this;
  }

  public Criteria desc(String... columns) {
    for (String column : columns) {
      this.builder.addCriterion(Predicate.ORDER_BY_DESC, null, column, "DESC",
          null);
    }
    return this;
  }

  public Criteria into(String fieldName, Object value) {
    this.builder.addCriterion(Predicate.INSERT, null, fieldName, null, value);
    return this;
  }

  public Criteria set(String fieldName, Object value) {
    this.builder.addCriterion(Predicate.UPDATE, null, fieldName, "=", value);
    return this;
  }

  /**
   * set where conditions.
   *
   * @param fieldName
   * @param op
   * @param values
   * @return
   */
  public Criteria where(String fieldName, String op, Object[] values) {
    this.builder.addCondition(Predicate.WHERE, null, fieldName, op, values);
    return this;
  }

  public Criteria where(String fieldName, Object[] values) {
    this.where(fieldName, "=", values);
    return this;
  }

  public Criteria where(String fieldName, String op, Object value) {
    this.builder.addCondition(Predicate.WHERE, null, fieldName, op, value);
    return this;
  }

  public Criteria where(String fieldName, Object value) {
    this.where(fieldName, "=", value);
    return this;
  }

  /**
   * add where conditions.
   *
   * @param fieldName
   * @param op
   * @param values
   * @return
   */
  public Criteria and(String fieldName, String op, Object[] values) {
    this.builder.addCondition(Predicate.WHERE, "AND", fieldName, op, values);
    return this;
  }

  public Criteria and(String fieldName, Object[] values) {
    this.and(fieldName, "=", values);
    return this;
  }

  public Criteria and(String fieldName, String op, Object value) {
    this.builder.addCondition(Predicate.WHERE, "AND", fieldName, op, value);
    return this;
  }

  public Criteria and(String fieldName, Object value) {
    this.and(fieldName, "=", value);
    return this;
  }

  /**
   * or where conditions.
   *
   * @param fieldName
   * @param op
   * @param values
   * @return
   */
  public Criteria or(String fieldName, String op, Object[] values) {
    this.builder.addCondition(Predicate.WHERE, "OR", fieldName, op, values);
    return this;
  }

  public Criteria or(String fieldName, Object[] values) {
    this.or(fieldName, "=", values);
    return this;
  }

  public Criteria or(String fieldName, String op, Object value) {
    this.builder.addCondition(Predicate.WHERE, "OR", fieldName, op, value);
    return this;
  }

  public Criteria or(String fieldName, Object value) {
    this.or(fieldName, "=", value);
    return this;
  }

  public Criteria begin(String relation) {
    this.builder.addCondition(Predicate.BRACKET_BEGIN, relation, "(", null,
        null);
    return this;
  }

  public Criteria begin() {
    return this.begin("and");
  }

  public Criteria end() {
    this.builder.addCondition(Predicate.BRACKET_END, null, "(", null, null);
    return this;
  }

  /**
   * select functions.
   *
   * @param func
   * @param isFieldExclusion
   * @param isOrderBy
   * @return
   */
  public Criteria addSelectFunc(String func, boolean isFieldExclusion,
      boolean isOrderBy) {
    this.builder.addCriterion(Predicate.FUNC, String.valueOf(isOrderBy), func,
        String.valueOf(isFieldExclusion), null);
    return this;
  }

  public Criteria addSelectFunc(String func) {
    this.addSelectFunc(func, true, false);
    return this;
  }

  /**
   * set the object for operation.
   *
   * @param entity
   * @return
   */
  public NestedSql build() {
    return this.build(null, true, new DefaultNameHandler());
  }

  public NestedSql build(Object entity) {
    return this.build(entity, true, new DefaultNameHandler());
  }

  public NestedSql build(Object entity, boolean isIgnoreNull) {
    return this.build(entity, isIgnoreNull, new DefaultNameHandler());
  }

  public NestedSql build(Object entity, NameHandler nameHandler) {
    return build(entity, true, nameHandler);
  }

  public NestedSql build(boolean isIgnoreNull, NameHandler nameHandler) {
    return build(null, isIgnoreNull, nameHandler);
  }

  /**
   *
   * @param entity the object of operation.
   * @param isIgnoreNull ignore null value.
   * @param nameHandler processing table/object name.<NameHandler>
   * @return
   */
  public NestedSql build(Object entity, boolean isIgnoreNull,
      NameHandler nameHandler) {
    return this.builder.build(this.entityClass, entity, isIgnoreNull,
        nameHandler);
  }

  @SuppressWarnings("rawtypes")
  public Class getEntityClass() {
    return entityClass;
  }
}
