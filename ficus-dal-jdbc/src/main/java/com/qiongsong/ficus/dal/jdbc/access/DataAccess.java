package com.qiongsong.ficus.dal.jdbc.access;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.KeyHolder;

import com.qiongsong.ficus.dal.support.BaseObject;

/**
 * 数据执行入口
 * @ClassName: DataAccess
 * @author thierry.fu
 * @date Jan 9, 2016 4:47:40 PM
 */
public interface DataAccess {

  DataSource getDataSource();

  <T extends BaseObject> List<T> query(String sql, Object[] params,
      RowMapper<T> rowMapper);

  List<Map<String, Object>> queryForList(String sql, Object[] params);

  <T extends BaseObject> T queryForObject(String sql, Object[] params,
      RowMapper<T> rowMapper);

  int update(String sql, Object[] params, KeyHolder generatedKeyHolder);

  int update(String sql, Object[] params);

  int[] batchUpdate(String sql, List<Object[]> parameters);

}
