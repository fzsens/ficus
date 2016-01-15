package com.qiongsong.ficus.dal.spring;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.qiongsong.ficus.dal.BaseDao;
import com.qiongsong.ficus.dal.BaseTest;
import com.qiongsong.ficus.dal.criteria.Criteria;
import com.qiongsong.ficus.dal.model.EoCOrder;
import com.qiongsong.ficus.dal.support.PagingInfo;

public class JdbcTemplateTest5 extends BaseTest {

  @Autowired
  @Qualifier("jdbcDao")
  private BaseDao baseDao;

  // @Test
  public void queryPaging() {
    for (int i = 0; i < 10000; i++) {
      PagingInfo pagingInfo = new PagingInfo(200, 100);
      this.baseDao.query(EoCOrder.class, pagingInfo);
    }
  }

  @Test
  public void oracleQuery() {
    PagingInfo pagingInfo = new PagingInfo(2000, 1);
    @SuppressWarnings("unused")
    List<EoCOrder> orders = this.baseDao.query(EoCOrder.class, pagingInfo);
    Criteria criteria = Criteria.select(EoCOrder.class).where("eoorId", "1200");
    orders = this.baseDao.query(criteria);

  }

}
