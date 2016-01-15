package com.qiongsong.ficus.dal;

import java.util.List;
import java.util.Map;

import com.qiongsong.ficus.dal.criteria.Criteria;
import com.qiongsong.ficus.dal.support.BaseObject;
import com.qiongsong.ficus.dal.support.BaseQueryCondition;
import com.qiongsong.ficus.dal.support.BaseQueryItem;
import com.qiongsong.ficus.dal.support.PagingInfo;

/**
 * basic data object access.
 *
 * @ClassName: BaseDao
 * @author thierry.fu
 * @date Dec 28, 2015 9:54:45 PM
 */
public interface BaseDao {

  /**
   * insert
   *
   * @param model
   * @return
   */
  int insert(BaseObject model);

  int insert(Criteria criteria);

  /**
   * delete
   *
   * @param model
   * @return
   */
  int delete(BaseObject model);

  int delete(Criteria criteria);

  /**
   * query
   *
   * @param clazz
   * @param id
   * @return
   */

  <T extends BaseObject> T get(Class<T> clazz, Integer id);

  <T extends BaseObject> List<T> query(Class<T> clazz);

  <T extends BaseObject> List<T> query(Class<T> clazz, PagingInfo pagingInfo);

  <T extends BaseObject> List<T> query(Criteria criteria);

  <T extends BaseObject> List<T> query(Criteria criteria,
      PagingInfo pagingInfo);

  <T extends BaseObject> T querySingleResult(Class<T> clazz);

  <T extends BaseObject> T querySingleResult(Criteria criteria);

  List<Map<String, Object>> queryForList(Criteria criteria);

  List<Map<String, Object>> queryForList(Criteria criteria,
      PagingInfo pagingInfo);

  List<Map<String, Object>> queryForListBySql(String sql, Object[] params);

  List<Map<String, Object>> queryForListBySql(String sql, Object[] params,
      PagingInfo pagingInfo);

  /**
   * query by condition, your could implements this method in any way . by
   * default jdbc . BaseQueryCondition will be parse with baseQuery and find the
   * query sql file in ibatis mapper file.
   */
  <T extends BaseQueryItem> List<T> query(BaseQueryCondition condition,
      Class<T> itemClass);

  /**
   * query by condition, your could implements this method in any way . by
   * default jdbc . BaseQueryCondition will be parse with baseQuery and find the
   * query sql file in ibatis mapper file.
   */
  <T extends BaseQueryItem> List<T> query(BaseQueryCondition condition,
      Class<T> itemClass, PagingInfo paginInfo);

  /**
   * for improve performance by manual relation <=> object mapping. in spring
   * jdbc template this will improve +/- 5% compare with
   * {@link BaseDao#query(BaseQueryCondition,Class)}
   */
  <T extends BaseQueryItem> List<T> queryByMapper(BaseQueryCondition condition,
      Class<T> itemClass, Object mapper);

  /**
   * for improve performance by manual relation <=> object mapping. in spring
   * jdbc template this will improve +/- 5% compare with
   * {@link BaseDao#query(BaseQueryCondition,Class,PagingInfo)}
   */
  <T extends BaseQueryItem> List<T> queryByMapper(BaseQueryCondition condition,
      PagingInfo paginInfo, Object mapper);

  /**
   * update
   *
   * @param model
   * @return
   */
  int update(BaseObject model);

  int update(Criteria criteria);

  int updateBySql(String sql, Object[] params);

  int[] batchUpdateBySql(String sql, List<Object[]> params);

}
