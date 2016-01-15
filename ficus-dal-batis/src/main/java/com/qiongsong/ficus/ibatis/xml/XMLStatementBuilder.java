/**
 *    Copyright 2009-2015 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.qiongsong.ficus.ibatis.xml;

import com.qiongsong.ficus.ibatis.builder.BaseBuilder;
import com.qiongsong.ficus.ibatis.builder.Configuration;
import com.qiongsong.ficus.ibatis.builder.MapperBuilderAssistant;
import com.qiongsong.ficus.ibatis.builder.SqlSource;

/**
 * SQL Statements 构建器
 * @author Clinton Begin
 */
public class XMLStatementBuilder extends BaseBuilder {

  private MapperBuilderAssistant builderAssistant;
  private XNode context;

  public XMLStatementBuilder(Configuration configuration, MapperBuilderAssistant builderAssistant, XNode context) {
    super(configuration);
    this.builderAssistant = builderAssistant;
    this.context = context;
  }
    /**
     * 解析 select/update/delete/insert 语句
     */
  public void parseStatementNode() {
    String id = context.getStringAttribute("id");

    //开始解析前，引入<include refid="sqlId" /> 中的SQL Fragments.
    XMLIncludeTransformer includeParser = new XMLIncludeTransformer(configuration, builderAssistant);
    includeParser.applyIncludes(context.getNode());

    // Parse the SQL (pre: <selectKey> and <include> were parsed and
    // removed)
    // SqlSource sqlSource = new
    // XMLLanguageDriver().createSqlSource(configuration, xNode);

    XMLScriptBuilder builder = new XMLScriptBuilder(configuration, context);
    SqlSource sqlSource = builder.parseScriptNode();
    builderAssistant.addMappedStatement(id, sqlSource);
  }
}
