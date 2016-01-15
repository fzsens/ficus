package com.qiongsong.ficus.dal.criteria.build;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.qiongsong.ficus.dal.NestedSql;
import com.qiongsong.ficus.dal.criteria.criterion.Criterion;
import com.qiongsong.ficus.dal.criteria.criterion.CriterionNestedSql;
import com.qiongsong.ficus.dal.criteria.criterion.Predicate;
import com.qiongsong.ficus.dal.support.handler.NameHandler;

/**
 * dml sql conditions builder
 *
 * @ClassName: WhereBuilder
 * @author thierry.fu
 * @date Dec 29, 2015 8:56:45 PM
 */
public class WhereBuilder extends AbstractSqlBuilder {

  @Override
  public void addCriterion(Predicate predic, String relation, String property, String op, Object value) {
    Criterion criterion = buildCriterion(predic, relation, property, op, value);
    this.criterions.put(property, criterion);
  }

  /**
   * build query conditions.
   */
  @Override
  public void addCondition(Predicate predic, String relation, String property, String op, Object value) {
    /**
     * ?
     */
    while (value instanceof Object[] && Array.getLength(value) == 1) {
      value = ((Object[]) value)[0];
    }
    this.addCriterion(predic, relation, property, op, value);
  }

  @Override
  public NestedSql build(Class<?> clazz, Object entity, boolean isIgnoreNull, NameHandler nameHandler) {
    StringBuilder sb = new StringBuilder();
    if (hasCriterions()) {
      sb.append(WHERE);
    }
    List<Object> params = new ArrayList<>();
    /**
     * traversal criterion map.
     */
    Criterion preCriterion = null;
    for (Map.Entry<String, Criterion> entry : getCriterions().entrySet()) {
      String column = nameHandler.getColumnName(clazz, entry.getKey());
      column = applyColumnAlias(column);
      Criterion criterion = entry.getValue();
      if (StringUtils.isNotBlank(criterion.getRelation()) && sb.length() > WHERE.length()
          && !isBracketBegin(preCriterion)) {
        sb.append(criterion.getRelation()).append(BLANK_);
      }
      if (criterion.isNativeField()) {
        String nativeName = tokenParse(criterion.getPropertyName(), clazz, nameHandler);
        String nativeValue = tokenParse(String.valueOf(criterion.getValue()), clazz, nameHandler);
        sb.append(nativeName).append(BLANK_).append(criterion.getOp()).append(BLANK_).append(nativeValue)
            .append(BLANK_);
      } else if (criterion.getPredicate() == Predicate.BRACKET_BEGIN
          || criterion.getPredicate() == Predicate.BRACKET_END) {
        sb.append(criterion.getPropertyName()).append(BLANK_);
      } else if (criterion.getValue() == null) {
        sb.append(column).append(" IS NULL ");
      } else if (criterion.getValue() instanceof Object[]) {
        this.processArrayArgs(sb, params, column, criterion, preCriterion);
      } else {
        sb.append(column).append(BLANK_).append(criterion.getOp()).append(BLANK_).append(" ? ");
        params.add(criterion.getValue());
      }
      preCriterion = criterion;
    }

    return new CriterionNestedSql(sb.toString(), params);
  }

  /**
   * array parameters
   *
   * @param sb
   * @param params
   * @param columnName
   * @param autoField
   * @param preAutoField
   */
  protected void processArrayArgs(StringBuilder sb, List<Object> params, String columnName, Criterion criterion,
      Criterion preCriterion) {
    Object[] args = (Object[]) criterion.getValue();
    if (StringUtils.indexOf(StringUtils.upperCase(criterion.getOp()), "IN") != -1) {
      sb.append(columnName).append(" ").append(criterion.getOp()).append(" (");
      for (int i = 0; i < args.length; i++) {
        sb.append("?");
        if (i != args.length - 1) {
          sb.append(",");
        }
        params.add(args[i]);
      }
      sb.append(") ");
    } else {
      sb.append(" (");
      for (int i = 0; i < args.length; i++) {
        sb.append(columnName).append(" ").append(criterion.getOp()).append(" ").append("?");
        if (i != args.length - 1) {
          sb.append(" OR ");
        }
        params.add(args[i]);
      }
      sb.append(") ");
    }
  }

  /**
   *
   * @param criterion
   * @return
   */
  protected boolean isBracketBegin(Criterion criterion) {
    return (criterion != null && criterion.getPredicate() == Predicate.BRACKET_BEGIN);
  }

}
