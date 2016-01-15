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

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.qiongsong.ficus.dal.exceptions.BuilderException;
import com.qiongsong.ficus.ibatis.builder.BaseBuilder;
import com.qiongsong.ficus.ibatis.builder.Configuration;
import com.qiongsong.ficus.ibatis.builder.MapperBuilderAssistant;

/**
 * Mybatis XML Wapper entry.
 * 删除Mybatis部分高级功能如缓存等的支持
 *
 * @ClassName: XMLMapperBuilder
 * @author Clinton Begin
 * @author thierry.fu
 * @date Jan 7, 2016 9:09:57 AM
 */
public class XMLMapperBuilder extends BaseBuilder {

  private XPathParser parser;
  private MapperBuilderAssistant builderAssistant;
  private Map<String, XNode> sqlFragments;
  private String resource;

  /**
   * 构建XMLMapperBuilder对象，解析的文件名称为resource，inputstream 用于构建流对象
   * @param inputStream
   * @param configuration
   * @param resource
   * @throws IOException
   */
  public XMLMapperBuilder(InputStream inputStream, Configuration configuration, String resource) throws IOException {
    super(configuration);
    this.builderAssistant = new MapperBuilderAssistant(configuration, resource);
    this.sqlFragments = configuration.getSqlFragments();
    this.resource = resource;
    this.parser = new XPathParser(inputStream, true, configuration.getVariables(),
        new XMLMapperEntityResolver());
  }

  public void parse() {
    if (!configuration.isResourceLoaded(resource)) {
      //解析节点中的mapper.即为Mapper文件中的根节点
      configurationElement(parser.evalNode("/mapper"));
      configuration.addLoadedResource(resource);
    }
  }

  private void configurationElement(XNode context) {
    try {
      String namespace = context.getStringAttribute("namespace");
      if (namespace == null || namespace.equals("")) {
        throw new UnsupportedOperationException("Mapper's namespace cannot be empty");
      }
      builderAssistant.setCurrentNamespace(namespace);
      sqlElement(context.evalNodes("/mapper/sql"));
      buildStatementFromContext(context.evalNodes("select|insert|update|delete"));
    } catch (Exception e) {
      throw new BuilderException("Error parsing Mapper XML. Cause: " + e, e);
    }
  }

  /**
   * 遍历处理select|insert|update|delete节点
   * @param list
   */
  private void buildStatementFromContext(List<XNode> list) {
    for (XNode context : list) {
      final XMLStatementBuilder statementParser = new XMLStatementBuilder(configuration, builderAssistant, context);
      statementParser.parseStatementNode();
    }
  }

  private void sqlElement(List<XNode> list) throws Exception {
    for (XNode xNode : list) {
      String id = xNode.getStringAttribute("id");
      id = builderAssistant.applyCurrentNamespace(id, false);
      sqlFragments.put(id, xNode);
    }
  }
}
