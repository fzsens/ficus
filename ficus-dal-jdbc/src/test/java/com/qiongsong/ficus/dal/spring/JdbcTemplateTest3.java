package com.qiongsong.ficus.dal.spring;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.qiongsong.ficus.dal.BaseDao;
import com.qiongsong.ficus.dal.BaseTest;
import com.qiongsong.ficus.dal.support.PagingInfo;

public class JdbcTemplateTest3 extends BaseTest {

  @Autowired
  @Qualifier("jdbcDao")
  private BaseDao baseDao;

  @Test
  public void queryPaging() {
    for (int i = 0; i < 10000; i++) {
      PagingInfo pagingInfo = new PagingInfo(200, 1000);
      this.baseDao.queryForListBySql("select * from user", new Object[] {}, pagingInfo);
    }

  }

}
