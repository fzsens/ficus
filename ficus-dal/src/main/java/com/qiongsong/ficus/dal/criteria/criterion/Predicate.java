package com.qiongsong.ficus.dal.criteria.criterion;

/**
 * predicate
 *
 * @ClassName: Predicate
 * @author thierry.fu
 * @date Dec 31, 2015 6:45:12 PM
 */
public enum Predicate {

  INSERT, UPDATE, WHERE,

  INCLUDE, EXCLUDE,

  ORDER_BY_ASC, ORDER_BY_DESC,

  BRACKET_BEGIN, BRACKET_END, FUNC, NATIVE,
}
