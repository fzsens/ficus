package com.qiongsong.ficus.dal.jdbc.support;

/**
 * 自定义BeanPropertyRowMapper实现，便于进行BeanProteryRowWapper默认行为的更改
 *
 * 包括不限于 对象<=>数据库字段名称的映射规则 RowMapper对象数据获取方法改进等
 *
 * @ClassName: FicusBeanPropertyRowMapper
 * @author thierry.fu
 * @date Jan 4, 2016 10:20:04 AM
 */
public class BeanPropertyRowMapper<T>
    extends org.springframework.jdbc.core.BeanPropertyRowMapper<T> {

}
