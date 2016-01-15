package com.qiongsong.ficus.dal.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.qiongsong.ficus.dal.BaseDao;
import com.qiongsong.ficus.dal.BaseTest;
import com.qiongsong.ficus.dal.NestedSql;
import com.qiongsong.ficus.dal.criteria.Criteria;
import com.qiongsong.ficus.dal.model.User;
import com.qiongsong.ficus.dal.support.PagingInfo;

public class JdbcTemplateTest2 extends BaseTest {

  @Autowired
  @Qualifier("jdbcDao")
  private BaseDao baseDao;

  @Test
  public void save() {
    List<Object[]> params = new ArrayList<Object[]>();
    NestedSql sql = null;
    for (int i = 0; i < 100; i++) {
      User u = new User();
      u.setBlance(i + 0D);
      u.setUserName("fqs");
      u.setCreateTime(new Date());
      sql = Criteria.insert(User.class).build(u);
      params.add(sql.getParameters());
    }
    int[] results = this.baseDao.batchUpdateBySql(sql.getSql(), params);
    Assert.assertEquals(results.length, params.size());
  }

  @Test
  public void save2() throws InterruptedException {
    ExecutorService services = Executors.newFixedThreadPool(20);
    final BaseDao baseDao2 = baseDao;
    for (int i = 0; i < 100; i++) {
      Runnable run = new Runnable() {
        @Override
        public void run() {
          User u = new User();
          u.setBlance(0D);
          u.setUserName("fqs");
          u.setCreateTime(new Date());
          long j = baseDao2.insert(u);
          Assert.assertEquals(j, 1);
        }
      };
      services.execute(run);
    }
    services.shutdown();
    services.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
  }

  @Test
  public void save3() throws InterruptedException {
    ExecutorService services = Executors.newFixedThreadPool(20);
    final BaseDao baseDao2 = baseDao;
    NestedSql sql = null;
    List<List<Object[]>> objects = new ArrayList<>();
    List<Object[]> params = new ArrayList<>();
    for (int i = 1; i <= 100; i++) {
      if (i % 1000 == 0) {
        objects.add(params);
        params = new ArrayList<>();
      }
      User u = new User();
      u.setBlance(0D);
      u.setUserName("fqs");
      u.setCreateTime(new Date());
      sql = Criteria.insert(User.class).build(u);
      params.add(sql.getParameters());
    }
    String insertSql = sql.getSql();
    for (final List<Object[]> objs : objects) {
      Runnable run = new Runnable() {
        @Override
        public void run() {
          baseDao2.batchUpdateBySql(insertSql, objs);
        }
      };
      services.execute(run);
    }
    services.shutdown();
    services.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
  }

  @Test
  public void save4() throws InterruptedException {
    NestedSql sql = null;
    List<Object[]> params = new ArrayList<>();
    for (int i = 1; i <= 200; i++) {
      User u = new User();
      u.setBlance(0D);
      u.setUserName("fqs");
      u.setCreateTime(new Date());
      sql = Criteria.insert(User.class).build(u);
      params.add(sql.getParameters());
    }
    String insertSql = sql.getSql();
    int[] reuslt = baseDao.batchUpdateBySql(insertSql, params);
    System.out.println(reuslt.length);
  }

  @Test
  public void query() {
    for (int i = 0; i < 100; i++) {
      PagingInfo pagingInfo = new PagingInfo(200, 1000);
      List<User> users = this.baseDao.query(User.class, pagingInfo);
      System.out.println(":" + users.size());
    }
  }

  // @Test
  public void queryPaging() {
    for (int i = 0; i < 100; i++) {
      PagingInfo pagingInfo = new PagingInfo(200, 20);
      this.baseDao.queryForListBySql("select * from user", new Object[] {},
          pagingInfo);
    }

  }

}
