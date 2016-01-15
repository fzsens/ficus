package com.qiongsong.ficus.dal.criteria.build;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.qiongsong.ficus.commons.untils.ClassUtils;
import com.qiongsong.ficus.dal.criteria.Builder;
import com.qiongsong.ficus.dal.criteria.criterion.Criterion;
import com.qiongsong.ficus.dal.criteria.criterion.Predicate;
import com.qiongsong.ficus.dal.lock.Versionable;
import com.qiongsong.ficus.dal.support.handler.NameHandler;
import com.qiongsong.ficus.dal.support.handler.NoneNameHandler;
import com.qiongsong.ficus.dal.support.parser.GenericTokenParser;
import com.qiongsong.ficus.dal.support.parser.NativeTokenHandler;
import com.qiongsong.ficus.dal.support.parser.TokenHandler;

/**
 *
 * @ClassName: AbstractSqlBuilder
 * @author thierry.fu
 * @date Dec 28, 2015 11:02:03 PM
 */
public abstract class AbstractSqlBuilder implements Builder {
  /**
   * Operator Objects
   */
  private String tableAlias;

  /**
   * result for query
   */
  protected List<String> columnFields;

  /**
   * Criterion from Criteria
   */
  protected Map<String, Criterion> criterions;

  /**
   * parser map
   */
  protected Set<GenericTokenParser> tokenParsers;

  public String getTableAlias() {
    return tableAlias;
  }

  public void setTableAlias(String table) {
    this.tableAlias = table;
  }

  public AbstractSqlBuilder() {
    columnFields = new ArrayList<>();
    criterions = new LinkedHashMap<>();
  }

  public Map<String, Criterion> getCriterions() {
    return this.criterions;
  }

  public boolean hasCriterions() {
    return (!criterions.isEmpty());
  }

  public boolean containsCriterion(String criterion) {
    return hasCriterions() && this.criterions.containsKey(criterion);
  }

  protected Criterion buildCriterion(Predicate predic, String relation,
      String property, String op, Object val) {
    Criterion criterion = new Criterion(predic, relation, property, op, val);
    return criterion;
  }

  /**
   * name token parsers.
   *
   * @param clazz
   * @param nameHandler
   * @return
   */
  protected Set<GenericTokenParser> initTokenParsers(Class<?> clazz,
      NameHandler nameHandler) {
    if (tokenParsers == null) {
      tokenParsers = new HashSet<GenericTokenParser>(2);
      TokenHandler tokenHandler = new NativeTokenHandler(clazz, getTableAlias(),
          nameHandler);
      tokenParsers.add(new GenericTokenParser(Criterion.NATIVE_FIELD_L,
          Criterion.NATIVE_FIELD_R, tokenHandler));
      tokenHandler = new NativeTokenHandler(clazz, null, new NoneNameHandler());
      tokenParsers.add(new GenericTokenParser(Criterion.NATIVE_CODE_L,
          Criterion.NATIVE_CODE_R, tokenHandler));
    }
    return tokenParsers;
  }

  /**
   * parser content.
   *
   * @param content
   * @param clazz
   * @param nameHandler
   * @return
   */
  protected String tokenParse(String content, Class<?> clazz,
      NameHandler nameHandler) {
    Set<GenericTokenParser> tokenParsers = initTokenParsers(clazz, nameHandler);
    String result = content;
    for (GenericTokenParser tokenParser : tokenParsers) {
      result = tokenParser.parse(result);
    }
    return result;
  }

  protected void mergeEntityFields(Object entity, Predicate predic,
      boolean isIgnoreNull) {
    if (entity == null) {
      return;
    }
    BeanInfo selfBeanInfo = ClassUtils.getBeanInfo(entity.getClass());
    PropertyDescriptor[] propertyDescriptors = selfBeanInfo
        .getPropertyDescriptors();
    for (PropertyDescriptor pd : propertyDescriptors) {
      Method readMethod = pd.getReadMethod();
      if (readMethod == null) {
        continue;
      }
      String fieldName = pd.getName();
      columnFields.add(fieldName);
      Object value = ClassUtils.invokeMethod(readMethod, entity);
      // 忽略掉null
      if (value == null && isIgnoreNull) {
        continue;
      }
      // 已经有了，以Criteria中为准
      if (this.containsCriterion(fieldName)) {
        continue;
      }
      Criterion autoField = this.buildCriterion(predic, "and", fieldName, "=",
          value);
      this.criterions.put(fieldName, autoField);
    }
    /**
     * 处理乐观锁，在更新时，自动增加版本号.OPTIMISTIC LOCK
     */
    if (entity instanceof Versionable) {
      int version = ((Versionable) entity).getVersion();
      if (predic == Predicate.UPDATE) {
        version += 1;
      }
      Criterion versionField = this.buildCriterion(Predicate.UPDATE, "and",
          VERSION, "=", version);
      this.criterions.put(VERSION, versionField);
    }
  }

  /**
   * 处理column别名
   *
   * @param columnName
   * @return
   */
  protected String applyColumnAlias(String columnName) {
    if (StringUtils.isBlank(getTableAlias())) {
      return columnName;
    }
    return new StringBuilder(getTableAlias()).append(".").append(columnName)
        .toString();
  }

  /**
   * 处理table别名
   *
   * @param tableName
   * @return
   */
  protected String applyTableAlias(String tableName) {
    if (StringUtils.isBlank(getTableAlias())) {
      return tableName;
    }
    return new StringBuilder(tableName).append(" ").append(getTableAlias())
        .toString();
  }

  /**
   * Build Entity Columns.
   *
   * @param clazz
   */
  protected void fetchClassFields(Class<?> clazz) {
    BeanInfo selfBeanInfo = ClassUtils.getBeanInfo(clazz);
    PropertyDescriptor[] propertyDescriptors = selfBeanInfo
        .getPropertyDescriptors();
    for (PropertyDescriptor pd : propertyDescriptors) {
      Method readMethod = pd.getReadMethod();
      if (readMethod == null) {
        continue;
      }
      columnFields.add(pd.getName());
    }
  }
}
