package com.qiongsong.ficus.ibatis.builder;

/**
*
* @ClassName: PropertyTokenizer
* @author thierry.fu
* @date Jan 7, 2016 10:35:15 PM
*/
public final class MappedStatement {

    private Configuration configuration;
    private String        id;
    private SqlSource     sqlSource;

    MappedStatement() {
        // constructor disabled
    }

    public static class Builder {
        private MappedStatement mappedStatement = new MappedStatement();

        public Builder(Configuration configuration, String id, SqlSource sqlSource) {
            mappedStatement.configuration = configuration;
            mappedStatement.id = id;
            mappedStatement.sqlSource = sqlSource;
        }

        public MappedStatement build() {
            assert mappedStatement.configuration != null;
            assert mappedStatement.id != null;
            assert mappedStatement.sqlSource != null;
            return mappedStatement;
        }
    }

    public String getId() {
        return id;
    }

    public SqlSource getSqlSource() {
        return sqlSource;
    }
}
