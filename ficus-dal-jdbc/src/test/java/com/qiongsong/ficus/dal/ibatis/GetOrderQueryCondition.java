package com.qiongsong.ficus.dal.ibatis;

import com.qiongsong.ficus.dal.support.BaseQueryCondition;

public class GetOrderQueryCondition extends BaseQueryCondition {

  /**
   *
   */
  private static final long serialVersionUID = -5870455388566573057L;

  private String[] orderNos;

  private String orderType;

  public String[] getOrderNos() {
    return orderNos;
  }

  public void setOrderNos(String[] orderNos) {
    this.orderNos = orderNos;
  }

  public String getOrderType() {
    return orderType;
  }

  public void setOrderType(String orderType) {
    this.orderType = orderType;
  }

}
