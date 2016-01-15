package com.qiongsong.ficus.dal.ibatis;

import org.junit.Test;

import com.qiongsong.ficus.dal.criteria.Criteria;
import com.qiongsong.ficus.dal.model.EoCOrder;

public class Test2 {

  @Test
  public void test() {
    Criteria c1 = Criteria.select(EoCOrder.class)
        .include("eoorOrderNo", "eoorId", "companyName", "companyCode")
        .where("eoorOrderTypeCode", "PO");

    Criteria c2 = Criteria.select(EoCOrder.class)
        .include("eoorOrderNo", "eoorId", "companyName", "companyCode")
        .where("eoorOrderTypeCode", "PO");
    System.out.println(c1.equals(c2));
  }

}
