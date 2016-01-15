package com.qiongsong.ficus.dal.query;

import java.util.Map;

import org.junit.Test;

import com.qiongsong.ficus.commons.untils.ClassUtils;
import com.qiongsong.ficus.dal.support.BaseQueryCondition;

public class QueryConditionnTest {

  @Test
  public void getName() {
    TestQueryCondition condition = new TestQueryCondition();
    String queryName = condition.getQueryName();
    System.out.println(queryName);
    // Assert.assertEquals("testQuery", queryName);
  }

  @Test
  public void getMap() {
    TestQueryCondition condition = new TestQueryCondition();
    condition.setAge(99);
    condition.setName("faq");
    Map<String, Object> maps = ClassUtils.getBeanPropMap(condition);
    for (String key : maps.keySet()) {
      System.out.println(key);
      System.out.println(maps.get(key));
    }
  }

}

class TestQueryCondition extends BaseQueryCondition {

  /**
   *
   */
  private static final long serialVersionUID = -5110718631493685241L;

  private String name;

  private Integer age;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

}
