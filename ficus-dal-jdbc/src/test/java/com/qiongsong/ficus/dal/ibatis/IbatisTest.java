package com.qiongsong.ficus.dal.ibatis;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.qiongsong.ficus.dal.BaseDao;
import com.qiongsong.ficus.dal.BaseTest;
import com.qiongsong.ficus.dal.criteria.Criteria;
import com.qiongsong.ficus.dal.model.EoCOrder;
import com.qiongsong.ficus.dal.support.PagingInfo;

public class IbatisTest extends BaseTest {
  @Autowired
  @Qualifier("jdbcDao")
  private BaseDao baseDao;

  @Test
  public void queryByRefSql() {
    for (int i = 0; i < 5000; i++) {
      GetOrderQueryCondition condition = new GetOrderQueryCondition();
      condition.setOrderType("PO");
      // condition
      // .setOrderNos(new String[] { "AD15122800000301", "AD15122800000302" });
      PagingInfo pagingInfo = new PagingInfo(4000, 1000);
      List<GetOrderQueryItem> items = baseDao.query(condition,
          GetOrderQueryItem.class, pagingInfo);
      System.out.println(items.size());
    }
    System.out.println("**");
  }

  @Test
  public void queryByRefSql0() {
    for (int i = 0; i < 5000; i++) {
      PagingInfo pagingInfo = new PagingInfo(5000, 1000);
      Criteria criteria = Criteria.select(EoCOrder.class)
          .include("eoorOrderNo", "eoorId").where("eoorOrderTypeCode", "PO");
      List<EoCOrder> items = baseDao.query(criteria, pagingInfo);
      System.out.println(
          items.get(0).getEoorId() + " : " + items.get(0).getEoorOrderNo());
      System.out.println(items.size());
    }
    System.out.println("##");
  }

  @Test
  public void queryByRefSql1() {
    for (int i = 0; i < 5000; i++) {
      GetOrderQueryCondition condition = new GetOrderQueryCondition();
      condition.setOrderType("PO");
      // condition
      // .setOrderNos(new String[] { "AD15122800000301", "AD15122800000302" });
      PagingInfo pagingInfo = new PagingInfo(6000, 1000);
      List<GetOrderQueryItem> items = baseDao.query(condition,
          GetOrderQueryItem.class, pagingInfo);
      System.out.println(items.size());
    }
    System.out.println("$$");
  }

  @Test
  public void queryByRefSql2() {
    for (int i = 0; i < 5000; i++) {
      PagingInfo pagingInfo = new PagingInfo(7000, 1000);
      Criteria criteria = Criteria.select(EoCOrder.class)
          .include("eoorOrderNo", "eoorId", "companyName", "companyCode")
          .where("eoorOrderTypeCode", "PO");
      List<EoCOrder> items = baseDao.query(criteria, pagingInfo);
      System.out.println(items.size());
    }
    System.out.println("##");
  }

  // @Test
  public void queryByRefSql3() {
    GetOrderQueryCondition condition = new GetOrderQueryCondition();
    condition.setOrderType("PO");
    // condition
    // .setOrderNos(new String[] { "AD15122800000301", "AD15122800000302" });
    PagingInfo pagingInfo = new PagingInfo(15000000, 5000000);
    List<GetOrderQueryItem> items = baseDao.query(condition,
        GetOrderQueryItem.class, pagingInfo);
    System.out.println(items.size());

    // System.out.println(items.size());
  }

}
