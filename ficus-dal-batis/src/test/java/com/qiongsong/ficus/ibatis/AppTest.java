package com.qiongsong.ficus.ibatis;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.qiongsong.ficus.dal.internal.FormatStyle;
import com.qiongsong.ficus.ibatis.MyBatisSqlFactory;
import com.qiongsong.ficus.ibatis.builder.Configuration;
import com.qiongsong.ficus.ibatis.builder.MappedStatement;
import com.qiongsong.ficus.ibatis.xml.XMLMapperBuilder;

/**
 * Unit test for simple App.
 */
public class AppTest {
  @Test
  public void main() throws IOException {
    Configuration config = new Configuration();
    InputStream inputstream = AppTest.class.getClassLoader()
        .getResourceAsStream("user-sql.xml");
    XMLMapperBuilder mapperParser = new XMLMapperBuilder(inputstream, config,
        "user");
    mapperParser.parse();
    MyBatisSqlFactory sqlFactory = new MyBatisSqlFactory(config);
    Map<String, MappedStatement> ms = sqlFactory.getConfiguration()
        .getMappedStatements();
    for (String s : ms.keySet()) {
      System.out.println(s);
      Map<String, Object> p = new HashMap<String, Object>();
      p.put("id", 200);
      p.put("names", new String[] { "张三", "lisi" });
      String query = ms.get(s).getSqlSource().getBoundSql(p).getSql();
      Object[] objects = ms.get(s).getSqlSource().getBoundSql(p)
          .getParameters();
      for (Object object : objects) {
        System.out.println(object);
      }
      query = fomatter(query);
    }
  }

  public static String fomatter(String query) {
    String formattedQuery = FormatStyle.BASIC.getFormatter().format(query);
    System.out.println("Original: " + query);
    System.out.println("Formatted: " + formattedQuery);
    return formattedQuery;
  }
}
