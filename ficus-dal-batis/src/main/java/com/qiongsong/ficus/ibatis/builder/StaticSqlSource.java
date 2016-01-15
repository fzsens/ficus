package com.qiongsong.ficus.ibatis.builder;

import java.util.List;

/**
 *
 * @ClassName: StaticSqlSource
 * @author thierry.fu
 * @date Jan 7, 2016 10:34:11 PM
 */
public class StaticSqlSource implements SqlSource {

    private String                 sql;
    private List<ParameterMapping> parameterMappings;

    public StaticSqlSource(String sql) {
        this(sql, null);
    }

    public StaticSqlSource(String sql, List<ParameterMapping> parameterMappings) {
        this.sql = sql;
        this.parameterMappings = parameterMappings;
    }

    public BoundSql getBoundSql(Object parameterObject) {
        return new BoundSql(sql, parameterMappings);
    }

}
