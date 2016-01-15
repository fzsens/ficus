package com.qiongsong.ficus.dal;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.qiongsong.ficus.dal.criteria.Criteria;
import com.qiongsong.ficus.dal.datasource.SqlType;
import com.qiongsong.ficus.dal.exceptions.BuilderException;
import com.qiongsong.ficus.dal.model.User;

/**
 * test case of Criteria Sql builder
 *
 * @ClassName: CriteriaTest
 * @author thierry.fu
 * @date Jan 2, 2016 8:13:05 PM
 */
public class CriteriaSqlBuilderTest {
  @Test
  public void basicGenerate() {
    User user = new User();
    user.setUserId(1);
    user.setUserName("ab");
    NestedSql sql = Criteria.update(User.class).build(user);
    Assert.assertEquals(sql.getSql(),
        "UPDATE USER SET USER_ID = ?,USER_NAME = ?  WHERE USER_ID =  ? ");
    sql = Criteria.insert(User.class).build(user);
    Assert.assertEquals(sql.getSql(),
        "INSERT INTO USER (USER_ID,USER_NAME) VALUES (?,?)");
    sql = Criteria.select(User.class).build(user);
    Assert.assertEquals(sql.getSql().trim(),
        "SELECT BLANCE,CREATE_TIME,TOTAL_BLANCE,USER_ID,USER_NAME FROM USER  WHERE USER_ID =  ? and USER_NAME =  ?");
    sql = Criteria.delete(User.class).build(user);
    Assert.assertEquals(sql.getSql().trim(),
        "DELETE FROM USER  WHERE USER_ID =  ? and USER_NAME =  ?");
  }

  @Test
  public void basicIncludeGenerate() {
    User user = new User();
    user.setUserId(1);
    user.setUserName("ab");
    NestedSql sql = Criteria.select(User.class)
        .include("userName", "blance", "createTime").build(user);
    Assert.assertEquals(sql.getSql().trim(),
        "SELECT BLANCE,CREATE_TIME,USER_NAME FROM USER  WHERE USER_ID =  ? and USER_NAME =  ?");
    sql = Criteria.select(User.class)
        .exclude("userName", "blance", "createTime").build(user);
    Assert.assertEquals(sql.getSql().trim(),
        "SELECT TOTAL_BLANCE,USER_ID FROM USER  WHERE USER_ID =  ? and USER_NAME =  ?");
  }

  // @Test(timeout = 60000)
  public void basicDeleteGenerate() {

    for (int i = 0; i < 10000000; i++) {
      Criteria.delete(User.class).where("userId", "1")
          .or("userName", "in", new Object[] { "name", "user" })
          .and("createTime", new Date()).build();
    }

  }

  @Test(expected = UnsupportedOperationException.class)
  public void baiscInsertUnSpExGenerate() {
    Criteria.insert(User.class).where("userId", 1).build();
  }

  @Test
  public void baiscInsertGenerate() {
    NestedSql sql = Criteria.insert(User.class).set("userName", "iamwhoiam")
        .set("createTime", new Date()).build(null);
    Assert.assertEquals(sql.getSql(),
        "INSERT INTO USER (USER_NAME,CREATE_TIME) VALUES (?,?)");
    User user = new User();
    user.setUserId(1);
    user.setUserName("ab");
    user.setBlance(99D);
    user.setTotalBlance(new BigDecimal("99999999"));
    user.setCreateTime(new Date());
    sql = Criteria.insert(User.class).build(user);
    Assert.assertEquals(sql.getSql(),
        "INSERT INTO USER (BLANCE,CREATE_TIME,TOTAL_BLANCE,USER_ID,USER_NAME) VALUES (?,?,?,?,?)");
  }

  @Test
  public void baiscUpdateGenerate() {
    User user = new User();
    user.setUserId(1);
    user.setUserName("ab");
    NestedSql sql = Criteria.update(User.class).build(user);
    Assert.assertEquals(sql.getSql(),
        "UPDATE USER SET USER_ID = ?,USER_NAME = ?  WHERE USER_ID =  ? ");
    sql = Criteria.update(User.class).set("userName", "iam").set("userId", 3)
        .build(null);
    Assert.assertEquals(sql.getSql(),
        "UPDATE USER SET USER_NAME = ?,USER_ID = ?  WHERE USER_ID =  ? ");
    sql = Criteria.update(User.class).set("userName", "iam")
        .and("createTime", new Date()).and("userName", "22").build();
    Assert.assertEquals(sql.getSql(),
        "UPDATE USER SET USER_NAME = ?  WHERE CREATE_TIME =  ? AND USER_NAME =  ? ");
  }

  @Test(expected = BuilderException.class)
  public void baiscQueryGenerate() {
    Criteria.select(User.class).set("createTime", new Date()).build();
  }

  // @Test(timeout = 150000)
  public void complexQueryGenerate() {
    for (int i = 0; i < 10000000; i++) {
      User user = new User();
      user.setUserName("ab");
      user.setTotalBlance(new BigDecimal("99988"));
      Criteria.select(User.class).and("userId", ">", 1000)
          .include("userId", "userName").exclude("createTime").desc("blance")
          .desc("blance").asc("userId").build(user);
    }

  }

  @Test
  public void simpleQueryGenerate() {
    NestedSql sql = Criteria.select(User.class).and("userId", ">", 1000)
        .build();
    Assert.assertEquals(sql.getSql().trim(),
        "SELECT BLANCE,CREATE_TIME,TOTAL_BLANCE,USER_ID,USER_NAME FROM USER  WHERE USER_ID >  ?");
  }

  @Test(expected = BuilderException.class)
  public void updateError() {
    Criteria.update(User.class).build();
  }

  @Test
  public void selectFunc() {

    Criteria.select(User.class).addSelectFunc("count(1)").build();
  }

  @Test
  public void testSqlType() {
    NestedSql sql = Criteria.select(User.class).addSelectFunc("count(1)")
        .build();
    Assert.assertEquals(sql.getSqlType(), SqlType.READ);
    sql = Criteria.update(User.class).set("userName", "iam").set("userId", 3)
        .build(null);
    Assert.assertEquals(sql.getSqlType(), SqlType.WRITE);
  }

}
