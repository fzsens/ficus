package com.qiongsong.ficus.sqlparser;

import com.qiongsong.ficus.sqlparser.support.DbVendor;

/**
 * SQL 解析器
 * @ClassName: SqlParser
 * @author thierry.fu
 * @date Jan 8, 2016 3:05:11 PM
 */
public class SqlParser {

  enum SqlType {
    function, procedure, package_spec, package_body, block_with_begin, block_with_declare, create_trigger, create_library, others;
  }

  enum SqlStructrue {
    start, is_as, body, bodyend, end;
  }

  public String sqltext;

  private DbVendor vender;

}
