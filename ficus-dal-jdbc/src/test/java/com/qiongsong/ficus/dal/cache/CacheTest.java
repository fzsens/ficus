package com.qiongsong.ficus.dal.cache;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.qiongsong.ficus.dal.BaseDao;
import com.qiongsong.ficus.dal.BaseTest;
import com.qiongsong.ficus.dal.criteria.Criteria;
import com.qiongsong.ficus.dal.model.User;
import com.qiongsong.ficus.dal.support.PagingInfo;

public class CacheTest extends BaseTest {

  @Autowired
  @Qualifier("jdbcDao")
  private BaseDao dao;

  @Test
  public void test() {
    for (int i = 0; i < 100; i++) {
      Criteria criteria = Criteria.select(User.class).where("userName", "张老板");
      PagingInfo p = new PagingInfo();
      p.setLimit(1);
      p.setOffset(2);
      this.dao.query(criteria, p);
    }

  }

  @Test
  public void test2() {

    for (int i = 0; i < 100; i++) {
      User user = this.dao.get(User.class, 1);
      System.out.print(user.getUserName());
    }

  }

}
