package com.qiongsong.ficus.dal.jdbc;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.qiongsong.ficus.dal.BaseDao;
import com.qiongsong.ficus.dal.NestedSql;
import com.qiongsong.ficus.dal.criteria.Criteria;
import com.qiongsong.ficus.dal.criteria.criterion.CriterionNestedSql;
import com.qiongsong.ficus.dal.support.BaseObject;
import com.qiongsong.ficus.dal.support.BaseQueryCondition;
import com.qiongsong.ficus.dal.support.BaseQueryItem;
import com.qiongsong.ficus.dal.support.PagingInfo;

/**
 * JdbcDao Implement.
 *
 * @ClassName: JdbcDaoImpl
 * @author thierry.fu
 * @date Jan 3, 2016 2:11:57 PM
 */
@Repository
@Transactional
public class JdbcDaoImpl extends AbstractJdbcDaoImpl implements BaseDao {

  @Override
  public int insert(BaseObject model) {
    Criteria criteria = Criteria.insert(model.getClass());
    NestedSql nestedSql = criteria.build(model, true, getNameHandler());
    return update(nestedSql);
  }

  @Override
  public int insert(Criteria criteria) {
    NestedSql nestedSql = criteria.build(true, getNameHandler());
    return update(nestedSql);
  }

  @Override
  public int delete(BaseObject model) {
    NestedSql nestedSql = Criteria.delete(model.getClass()).build(model,
        getNameHandler());
    return update(nestedSql);
  }

  @Override
  public int delete(Criteria criteria) {
    NestedSql nestedSql = criteria.build(true, getNameHandler());
    return update(nestedSql);
  }

  @Override
  public <T extends BaseObject> T get(Class<T> clazz, Integer id) {
    System.out.println("&&");
    String primaryKey = getNameHandler().getPkFieldName(clazz);
    NestedSql nestedSql = Criteria.select(clazz).where(primaryKey, id).build();
    return queryForObject(nestedSql, this.getRowMapper(clazz));
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T extends BaseObject> List<T> query(Class<T> clazz) {
    NestedSql nestedSql = Criteria.select(clazz).build(true, getNameHandler());
    List<?> list = query(nestedSql, this.getRowMapper(clazz));
    return (List<T>) list;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T extends BaseObject> List<T> query(Criteria criteria) {
    NestedSql nestedSql = criteria.build(true, getNameHandler());
    List<?> list = query(nestedSql,
        this.getRowMapper(criteria.getEntityClass()));
    return (List<T>) list;
  }

  @Override
  public <T extends BaseObject> T querySingleResult(Class<T> clazz) {
    List<T> list = this.query(clazz);
    return CollectionUtils.isEmpty(list) ? null : list.get(0);
  }

  @Override
  public <T extends BaseObject> T querySingleResult(Criteria criteria) {
    List<T> list = this.query(criteria);
    return CollectionUtils.isEmpty(list) ? null : list.get(0);
  }

  @Override
  public List<Map<String, Object>> queryForList(Criteria criteria) {
    NestedSql nestedSql = criteria.build(true, getNameHandler());
    return queryForList(nestedSql);
  }

  @Override
  public List<Map<String, Object>> queryForListBySql(String sql,
      Object[] params) {
    return queryForList(new CriterionNestedSql(sql, params));
  }

  @Override
  public int update(BaseObject model) {
    NestedSql nestedSql = Criteria.update(model.getClass()).build(model,
        getNameHandler());
    return update(nestedSql);
  }

  @Override
  public int update(Criteria criteria) {
    NestedSql nestedSql = criteria.build(true, getNameHandler());
    return update(nestedSql);
  }

  @Override
  public int updateBySql(String sql, Object[] params) {
    return update(new CriterionNestedSql(sql, params));
  }

  @Override
  public int[] batchUpdateBySql(String sql, List<Object[]> params) {
    return batchUpdate(sql, params);
  }

  @Override
  public <T extends BaseObject> List<T> query(Class<T> clazz,
      PagingInfo pagingInfo) {
    NestedSql nestedSql = Criteria.select(clazz).build(true, getNameHandler());
    List<T> list = query(nestedSql, pagingInfo, this.getRowMapper(clazz));
    return list;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T extends BaseObject> List<T> query(Criteria criteria,
      PagingInfo pagingInfo) {
    NestedSql nestedSql = criteria.build(true, getNameHandler());
    List<T> list = query(nestedSql, pagingInfo,
        this.getRowMapper(criteria.getEntityClass()));
    return (List<T>) list;
  }

  @Override
  public List<Map<String, Object>> queryForList(Criteria criteria,
      PagingInfo pagingInfo) {
    NestedSql nestedSql = criteria.build(true, getNameHandler());
    return queryForList(nestedSql, pagingInfo);
  }

  @Override
  public List<Map<String, Object>> queryForListBySql(String sql,
      Object[] params, PagingInfo pagingInfo) {
    return queryForList(new CriterionNestedSql(sql, params), pagingInfo);
  }

  @Override
  public <T extends BaseQueryItem> List<T> query(BaseQueryCondition condition,
      Class<T> itemClass) {
    NestedSql nestedSql = this.sqlFactory.getBoundSql(condition);
    List<T> list = query(nestedSql, this.getRowMapper(itemClass));
    return (List<T>) list;
  }

  @Override
  public <T extends BaseQueryItem> List<T> query(BaseQueryCondition condition,
      Class<T> itemClass, PagingInfo paginInfo) {
    NestedSql nestedSql = this.sqlFactory.getBoundSql(condition);
    List<T> list = query(nestedSql, paginInfo, this.getRowMapper(itemClass));
    return (List<T>) list;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T extends BaseQueryItem> List<T> queryByMapper(
      BaseQueryCondition condition, Class<T> itemClass, Object mapper) {
    if (!(mapper instanceof RowMapper)) {
      throw new IllegalArgumentException(mapper
          + " expected type is <org.springframework.jdbc.core.RowMapper> , but actualy get "
          + mapper.getClass());
    }
    NestedSql nestedSql = this.sqlFactory.getBoundSql(condition);
    List<T> list = query(nestedSql, (RowMapper<T>) mapper);
    return (List<T>) list;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T extends BaseQueryItem> List<T> queryByMapper(
      BaseQueryCondition condition, PagingInfo paginInfo, Object mapper) {
    if (!(mapper instanceof RowMapper)) {
      throw new IllegalArgumentException(mapper
          + " expected type is <org.springframework.jdbc.core.RowMapper> , but actualy get "
          + mapper.getClass());
    }
    NestedSql nestedSql = this.sqlFactory.getBoundSql(condition);
    List<T> list = query(nestedSql, paginInfo, (RowMapper<T>) mapper);
    return (List<T>) list;
  }

}
