package com.qiongsong.ficus.dal.cache;

import java.util.Arrays;

import org.junit.Test;

import com.qiongsong.ficus.dal.NestedSql;
import com.qiongsong.ficus.dal.criteria.criterion.CriterionNestedSql;

public class NestedSqlEqualsTest {

  @Test
  public void eq() {

    Object[] expecteds = new Object[] { "a", "b", 3 };
    Object[] actuals = new Object[] { "a", "b", 3 };
    NestedSql sql = new CriterionNestedSql("select * from user", expecteds);
    NestedSql sql2 = new CriterionNestedSql("select * from user", actuals);
    System.out.println(sql.equals(sql2));
    System.out.println(sql.hashCode());
    System.out.println(sql2.hashCode());
    System.out.println("select * from user".hashCode());
    System.out.println("select * from user".hashCode());
    System.out.println("select * from user".hashCode());
    System.out.println(expecteds.hashCode());
    System.out.println(actuals.hashCode());
    System.out.println(Arrays.hashCode(expecteds));
    System.out.println(Arrays.hashCode(actuals));
  }

}
