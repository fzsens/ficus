package com.qiongsong.ficus.dal.jdbc.access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.jdbc.core.SqlTypeValue;
import org.springframework.jdbc.core.StatementCreatorUtils;
import org.springframework.jdbc.support.KeyHolder;

import com.qiongsong.ficus.dal.exceptions.OptimisticLockException;
import com.qiongsong.ficus.dal.internal.FormatStyle;
import com.qiongsong.ficus.dal.support.BaseObject;

/**
 * 不直接使用jdbcTemplate访问数据库，用于添加多数据源和分表支持
 *
 * @ClassName: DefaultDataAccess
 * @author thierry.fu
 * @date Jan 9, 2016 7:46:04 PM
 */
public class DataAccessImpl implements DataAccess {

  private static final Log logger = LogFactory.getLog(DataAccessImpl.class);

  private final JdbcTemplate jdbcTemplate;

  public DataAccessImpl(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  @Override
  public DataSource getDataSource() {
    return jdbcTemplate.getDataSource();
  }

  @Override
  public <T extends BaseObject> List<T> query(String sql, Object[] params,
      RowMapper<T> rowMapper) {
    if (logger.isDebugEnabled()) {
      logging(sql, params);
    }
    return jdbcTemplate.query(sql, params, rowMapper);
  }

  @Override
  public List<Map<String, Object>> queryForList(String sql, Object[] params) {
    if (logger.isDebugEnabled()) {
      logging(sql, params);
    }
    return jdbcTemplate.queryForList(sql, params);
  }

  @Override
  public <T extends BaseObject> T queryForObject(String sql, Object[] params,
      RowMapper<T> rowMapper) {
    if (logger.isDebugEnabled()) {
      logging(sql, params);
    }
    return jdbcTemplate.queryForObject(sql, params, rowMapper);
  }

  @Override
  public int update(String sql, Object[] params, KeyHolder generatedKeyHolder) {
    if (logger.isDebugEnabled()) {
      logging(sql, params);
    }
    boolean isReturnKeys = generatedKeyHolder != null;
    int affectedRow = 0;
    PreparedStatementCreator psc = getPreparedStatementCreator(sql, params,
        isReturnKeys);
    if (generatedKeyHolder == null) {
      affectedRow = jdbcTemplate.update(psc);
    }
    else {
      affectedRow = jdbcTemplate.update(psc, generatedKeyHolder);
    }
    if (affectedRow != 1) {
      throw new OptimisticLockException(
          "Update Failed. May be data expired or Other reasons. affected "
              + affectedRow + " Row(s)");
    }
    return affectedRow;
  }

  @Override
  public int update(String sql, Object[] params) {
    return update(sql, params, null);
  }

  @Override
  public int[] batchUpdate(String sql, List<Object[]> parameters) {
    if (logger.isDebugEnabled()) {
      String formattedSql = FormatStyle.BASIC.getFormatter().format(sql);
      StringBuilder logSql = new StringBuilder()
          .append(" @ Executing SQL statement [").append(formattedSql)
          .append("]").append(" @ first parameters is {");
      Object[] params = parameters == null ? new Object[0] : parameters.get(0);
      for (Object param : params) {
        logSql.append(param).append(",");
      }
      logger.debug(logSql.append("}").toString());
    }
    return jdbcTemplate.batchUpdate(sql, parameters);
  }

  private PreparedStatementCreator getPreparedStatementCreator(//
      final String sql, final Object[] args, final boolean returnKeys) {
    PreparedStatementCreator creator = new PreparedStatementCreator() {

      @Override
      public PreparedStatement createPreparedStatement(Connection con)
          throws SQLException {
        PreparedStatement ps = con.prepareStatement(sql);
        if (returnKeys) {
          ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        }
        else {
          ps = con.prepareStatement(sql);
        }

        if (args != null) {
          for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            if (arg instanceof SqlParameterValue) {
              SqlParameterValue paramValue = (SqlParameterValue) arg;
              StatementCreatorUtils.setParameterValue(ps, i + 1, paramValue,
                  paramValue.getValue());
            }
            else {
              StatementCreatorUtils.setParameterValue(ps, i + 1,
                  SqlTypeValue.TYPE_UNKNOWN, arg);
            }
          }
        }
        return ps;
      }
    };
    return creator;
  }

  /**
   * @param sql
   * @param params
   */
  private void logging(String sql, Object[] params) {
    String formattedSql = FormatStyle.BASIC.getFormatter().format(sql);
    StringBuilder logSql = new StringBuilder()
        .append("@Executing SQL statement [").append(formattedSql).append("]")
        .append(" @parameters is {");
    for (Object param : params) {
      logSql.append(param).append(",");
    }
    logger.debug(logSql.append("}").toString());
  }

}
