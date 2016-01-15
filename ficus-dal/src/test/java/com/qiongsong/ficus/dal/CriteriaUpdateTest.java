package com.qiongsong.ficus.dal;

import java.util.Date;

import org.junit.Test;

import com.qiongsong.ficus.dal.criteria.Criteria;
import com.qiongsong.ficus.dal.model.User;

/**
 * test case of Criteria Sql builder
 *
 * @ClassName: CriteriaTest
 * @author thierry.fu
 * @date Jan 2, 2016 8:13:05 PM
 */
public class CriteriaUpdateTest {
  @Test
  public void basicGenerate() {
    User u = new User();
    u.setBlance(99D);
    u.setCreateTime(new Date());
    u.setUserId(1);
    NestedSql sql = Criteria.insert(User.class).build(u);
    System.out.println(sql.getSql());

    u.setUserName("myName");
    sql = Criteria.update(User.class).build(u);
    System.out.println(sql.getSql());
    for (Object obj : sql.getParameters()) {
      System.out.print(obj + " ,");
    }
    System.out.println();
    System.out.println("??");
    sql = Criteria.select(User.class).build(u);
    System.out.println(sql.getSql());
    for (Object obj : sql.getParameters()) {
      System.out.print(obj + " ,");
    }
    System.out.println();
    System.out.println("??");
    User u2 = new User();
    u2.setUserId(99);
    u2.setUserName("zqp");
    sql = Criteria.select(User.class).build(u2);
    System.out.println(sql.getSql());
    for (Object obj : sql.getParameters()) {
      System.out.print(obj + " ,");
    }
    u2.setVersion(88);
    u2.setUserName("fqs");
    sql = Criteria.update(User.class).build(u2);
    System.out.println(sql.getSql());
    for (Object obj : sql.getParameters()) {
      System.out.print(obj + " ,");
    }

  }

}
