package com.qiongsong.ficus.dal.ibatis;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;

import com.qiongsong.ficus.dal.BaseDao;
import com.qiongsong.ficus.dal.BaseTest;
import com.qiongsong.ficus.dal.support.PagingInfo;

public class IbatisTest2 extends BaseTest {
  @Autowired
  @Qualifier("jdbcDao")
  private BaseDao baseDao;

  @Test
  public void queryByRefSql() {
    for (int i = 0; i < 1000; i++) {
      String sql = "select eoor_id,eoor_order_no from eo_c_order WHERE eoor_order_type_code = ?";
      Object[] param = new Object[] { "PO" };
      PagingInfo pagingInfo = new PagingInfo(4000, 3000);
      List<?> item = this.baseDao.queryForListBySql(sql, param, pagingInfo);
      System.out.println(item.size());
    }
    System.out.println("**");
  }

  @Test
  public void queryByRefSql0() {
    for (int i = 0; i < 1000; i++) {
      GetOrderQueryCondition condition = new GetOrderQueryCondition();
      condition.setOrderType("PO");
      PagingInfo pagingInfo = new PagingInfo(8000, 7000);
      List<GetOrderQueryItem> item = this.baseDao.queryByMapper(condition,
          pagingInfo, new RowMapper<GetOrderQueryItem>() {
            @Override
            public GetOrderQueryItem mapRow(ResultSet rs, int rowNum)
                throws SQLException {
              GetOrderQueryItem item = new GetOrderQueryItem();
              item.setEoorId(rs.getInt(1));
              item.setEoorOrderNo(rs.getString(2));
              return item;
            }
          });
      System.out.println(item.size());
    }
    System.out.println("**");
  }

  @Test
  public void queryByRefSql1() {
    for (int i = 0; i < 1000; i++) {
      GetOrderQueryCondition condition = new GetOrderQueryCondition();
      condition.setOrderType("PO");
      PagingInfo pagingInfo = new PagingInfo(2000, 1000);
      List<GetOrderQueryItem> item = this.baseDao.query(condition,
          GetOrderQueryItem.class, pagingInfo);
      System.out.println(item.size());
    }
    System.out.println("**");
  }

}
