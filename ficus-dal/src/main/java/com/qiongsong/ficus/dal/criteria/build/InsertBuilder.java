package com.qiongsong.ficus.dal.criteria.build;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.qiongsong.ficus.dal.NestedSql;
import com.qiongsong.ficus.dal.criteria.criterion.Criterion;
import com.qiongsong.ficus.dal.criteria.criterion.CriterionNestedSql;
import com.qiongsong.ficus.dal.criteria.criterion.Predicate;
import com.qiongsong.ficus.dal.exceptions.BuilderException;
import com.qiongsong.ficus.dal.support.handler.NameHandler;

/**
 * build insert sql.
 *
 * @ClassName: InsertBuilder
 * @author thierry.fu
 * @date Dec 30, 2015 9:02:53 PM
 */
public class InsertBuilder extends AbstractSqlBuilder {

  @Override
  public void addCriterion(Predicate predic, String relation, String property,
      String op, Object value) {
    Criterion criterion = buildCriterion(predic, relation, property, op, value);
    this.getCriterions().put(property, criterion);
  }

  @Override
  public void addCondition(Predicate predic, String relation, String property,
      String op, Object value) {
    throw new BuilderException(
        "Unsupprt Operation , To AddCondition In InsertBuilder.");
  }

  @Override
  public NestedSql build(Class<?> clazz, Object entity, boolean isIgnoreNull,
      NameHandler nameHandler) {
    mergeEntityFields(entity, Predicate.INSERT, isIgnoreNull);
    StringBuilder sb = new StringBuilder(INSERT);
    StringBuilder args = new StringBuilder("(");
    List<Object> params = new ArrayList<Object>();
    String tableName = nameHandler.getTableName(clazz);
    sb.append(tableName).append(" (");
    for (Map.Entry<String, Criterion> entry : getCriterions().entrySet()) {
      Criterion criterion = entry.getValue();
      if (criterion.getValue() == null && isIgnoreNull) {
        continue;
      }
      if (criterion.isNativeField()) {
        String nativeFieldName = tokenParse(criterion.getPropertyName(), clazz,
            nameHandler);
        String nativeValue = tokenParse(String.valueOf(criterion.getValue()),
            clazz, nameHandler);
        sb.append(nativeFieldName).append(" = ").append(nativeValue)
            .append(",");
      }
      else {
        String columnName = nameHandler.getColumnName(clazz, entry.getKey());
        sb.append(columnName).append(",");
        args.append("?");
      }
      args.append(",");
      params.add(criterion.getValue());
    }
    args.deleteCharAt(args.length() - 1);
    sb.deleteCharAt(sb.length() - 1);
    sb.append(")").append(VALUES).append(args.append(")"));
    return new CriterionNestedSql(sb.toString(), params);
  }
}
