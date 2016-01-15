package com.qiongsong.ficus.dal;

import org.junit.Assert;
import org.junit.Test;

import com.qiongsong.ficus.dal.dialect.MsSQLServerDialect;
import com.qiongsong.ficus.dal.dialect.MySQLDialect;
import com.qiongsong.ficus.dal.dialect.OracleDialect;

public class DialectTest {

  @Test
  public void testgetCount() {
    MySQLDialect mysql = new MySQLDialect();
    String querySql = "select * from user order by user_name ";
    String countSql = mysql.getCountString(querySql);
    Assert.assertEquals(countSql, "select count(1) from (select * from user order by user_name ) count_");
  }

  @Test
  public void testMySql() {
    MySQLDialect mysql = new MySQLDialect();
    String querySql = "select * from user order by user_name ";

    String pageSql = mysql.getLimitString(querySql, 0, 20);
    Assert.assertEquals(pageSql, "select * from user order by user_name  limit 20");
    pageSql = mysql.getLimitString(querySql, 1000, 20);
    Assert.assertEquals(pageSql, "select * from user order by user_name  limit 1000,20");
  }

  @Test
  public void testOracle() {
    OracleDialect oracle = new OracleDialect();
    String querySql = "select t.* from EO_C_ORDER t order by t.CREATE_TIME ";
    String pageSql = oracle.getLimitString(querySql, 1000, 20);
    Assert.assertEquals(pageSql,
        "select * from ( select rownum rownum_,rows_.* from (select t.* from EO_C_ORDER t order by t.CREATE_TIME) rows_ where rownum <= 1000) where rownum_ > 20");
  }

  @Test
  public void testMsSqlServer() {
    MsSQLServerDialect oracle = new MsSQLServerDialect();
    String querySql = "select t.* from EO_C_ORDER t  ";
    String pageSql = oracle.getLimitString(querySql, 200, 20);
    System.out.println(pageSql);
  }

  public static void main(String[] args) {
    OracleDialect oracle = new OracleDialect();
    String sql = oracle.getLimitString("select * from app.es_user", 100, 200);
    System.out.println(sql);
  }

}
