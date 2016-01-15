package com.qiongsong.ficus.ibatis.builder;

/**
*
* @ClassName: PropertyTokenizer
* @author thierry.fu
* @date Jan 7, 2016 10:35:15 PM
*/
public class ParameterMapping {

    private String property;
//    private String expression;

    private ParameterMapping() {
    }

    public static class Builder {

        private ParameterMapping parameterMapping = new ParameterMapping();

        public Builder(String property) {
            parameterMapping.property = property;
        }

        public ParameterMapping build() {
            return parameterMapping;
        }
    }

    public String getProperty() {
        return property;
    }

}
