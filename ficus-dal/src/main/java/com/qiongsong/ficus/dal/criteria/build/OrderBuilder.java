package com.qiongsong.ficus.dal.criteria.build;

import java.util.Map;

import com.qiongsong.ficus.dal.NestedSql;
import com.qiongsong.ficus.dal.criteria.criterion.Criterion;
import com.qiongsong.ficus.dal.criteria.criterion.CriterionNestedSql;
import com.qiongsong.ficus.dal.criteria.criterion.Predicate;
import com.qiongsong.ficus.dal.support.handler.NameHandler;

/**
 * build order by .
 *
 * @ClassName: OrderBuilder
 * @author thierry.fu
 * @date Dec 30, 2015 4:26:46 PM
 */
public class OrderBuilder extends AbstractSqlBuilder {

  @Override
  public void addCriterion(Predicate predic, String relation, String property,
      String op, Object value) {
    Criterion criterion = buildCriterion(predic, relation, property, op, value);
    this.criterions.put(property, criterion);
  }

  @Override
  public void addCondition(Predicate predic, String relation, String property,
      String op, Object value) {
    throw new UnsupportedOperationException();
  }

  @Override
  public NestedSql build(Class<?> clazz, Object entity, boolean isIgnoreNull,
      NameHandler nameHandler) {
    StringBuilder sb = new StringBuilder(ORDER_BY);
    if (!hasCriterions()) {
      sb.append(applyColumnAlias(nameHandler.getPkColumnName(clazz)))
          .append(DESC);
    }
    else {
      for (Map.Entry<String, Criterion> entry : getCriterions().entrySet()) {
        String columnName = applyColumnAlias(
            nameHandler.getColumnName(clazz, entry.getKey()));
        sb.append(columnName).append(BLANK_).append(entry.getValue().getOp())
            .append(COMMA);
      }
      sb.deleteCharAt(sb.lastIndexOf(COMMA));
    }

    return new CriterionNestedSql(sb.toString());
  }

}
