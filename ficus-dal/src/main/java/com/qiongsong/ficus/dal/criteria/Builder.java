package com.qiongsong.ficus.dal.criteria;

import java.util.Map;

import com.qiongsong.ficus.dal.NestedSql;
import com.qiongsong.ficus.dal.criteria.criterion.Criterion;
import com.qiongsong.ficus.dal.criteria.criterion.Predicate;
import com.qiongsong.ficus.dal.support.handler.NameHandler;

/**
 * interface of Sql builder interface.
 *
 * @ClassName: SqlBuilder
 * @author thierry.fu
 * @date Dec 28, 2015 10:56:08 PM
 */
public interface Builder {

  public static final String SELECT = "SELECT ";

  public static final String FROM = " FROM ";

  public static final String WHERE = " WHERE ";

  public static final String INSERT = "INSERT INTO ";

  public static final String VALUES = " VALUES ";

  public static final String DELETE = "DELETE FROM ";

  public static final String UPDATE = "UPDATE ";

  public static final String ORDER_BY = " ORDER BY ";

  public static final String DESC = " DESC";

  public static final String ASC = " ASC";

  public static final String SET = " SET ";

  public static final String BLANK_ = " ";

  public static final String COMMA = ",";

  public static final String VERSION = "version";

  void addCriterion(Predicate predic, String relation, String property,
      String op, Object value);

  void addCondition(Predicate predic, String relation, String property,
      String op, Object value);

  String getTableAlias();

  void setTableAlias(String alias);

  boolean hasCriterions();

  boolean containsCriterion(String fieldName);

  Map<String, Criterion> getCriterions();

  NestedSql build(Class<?> clazz, Object entity, boolean isIgnoreNull,
      NameHandler nameHandler);

}
