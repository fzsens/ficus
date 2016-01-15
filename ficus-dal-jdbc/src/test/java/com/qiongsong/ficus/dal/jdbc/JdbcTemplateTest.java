package com.qiongsong.ficus.dal.jdbc;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.qiongsong.ficus.dal.BaseTest;
import com.qiongsong.ficus.dal.NestedSql;
import com.qiongsong.ficus.dal.criteria.Criteria;
import com.qiongsong.ficus.dal.model.User;

@Transactional
public class JdbcTemplateTest extends BaseTest {

  @Autowired
  public JdbcTemplate jdbctemplate;

  @Test
  @Rollback(true)
  public void test() {

    User user = new User();
    user.setUserName("fas");
    user.setBlance(1000000D);
    NestedSql sql = Criteria.insert(User.class).build(user);
    System.out.println(sql.getParameters());
    jdbctemplate.update(sql.getSql(), sql.getParameters());

  }

  @Test
  public void test2() {
    NestedSql query = Criteria.select(User.class).include("userId", "userName").where("userName", "=", "fas")
        .build();
    List<Map<String, Object>> lists = jdbctemplate.queryForList(query.getSql(), query.getParameters());
    for (Map<String, Object> maps : lists) {
      for (String key : maps.keySet()) {
        System.out.println(key);
        System.out.println(maps.get(key));
      }
    }
  }

  @Test
  public void test3() {
    NestedSql deleteSql = Criteria.delete(User.class).build();
    jdbctemplate.update(deleteSql.getSql());
  }
}
