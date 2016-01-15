package com.qiongsong.ficus.dal.criteria.criterion;

/**
 * An object-oriented representation of a query criterion
 *
 * @ClassName: Criterion
 * @author thierry.fu
 * @date Dec 28, 2015 9:13:55 PM
 */
public class Criterion {
  /**
   * 不以传参方式构建，符号中内容不会被转换
   */
  public static final String NATIVE_CODE_L = "{";

  public static final String NATIVE_CODE_R = "}";

  /**
   * 不以传参方式构建，符号中内容会被转换(field -> column)
   */
  public static final String NATIVE_FIELD_L = "[";

  public static final String NATIVE_FIELD_R = "]";

  /**
   * 查询条件的关系 AND / OR
   */
  private String relation;

  /**
   * property of table.
   */
  private final String propertyName;

  /**
   * value.
   */
  private final Object value;

  /**
   * property 和value的关系
   */
  private final String op;

  /**
   * 操作原语
   */
  private Predicate predicate;

  public static final Criterion WHERE_1_EQ_2 = new Criterion(Predicate.WHERE,
      "and", "1", "=", "2");

  public Criterion(Predicate predicate, String relation, String propertyName,
      String op, Object value) {
    this.predicate = predicate;
    this.relation = relation;
    this.propertyName = propertyName;
    this.op = op;
    this.value = value;
  }

  public String getOp() {
    return this.op;
  }

  public String getPropertyName() {
    return this.propertyName;
  }

  public Object getValue() {
    return this.value;
  }

  public Object getPredicate() {
    return this.predicate;
  }

  public String getRelation() {
    return this.relation;
  }

  /**
   * {properyName} or [propertyName]
   *
   * @return
   */
  public boolean isNativeField() {
    return propertyName.startsWith(NATIVE_CODE_L)
        && propertyName.endsWith(NATIVE_CODE_R)
        || propertyName.startsWith(NATIVE_FIELD_L)
            && propertyName.endsWith(NATIVE_FIELD_R);
  }

  public String toString() {
    return new StringBuilder().append(this.predicate).append(this.relation)
        .append(this.propertyName).append(this.op).append(this.value)
        .toString();
  }

}
