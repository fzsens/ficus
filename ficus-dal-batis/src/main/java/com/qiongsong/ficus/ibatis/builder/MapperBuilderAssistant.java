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
package com.qiongsong.ficus.ibatis.builder;

import com.qiongsong.ficus.dal.exceptions.BuilderException;

/**
 * 用于构建Mapper. 删除关于缓存部分的支持
 * @author Clinton Begin
 */
public class MapperBuilderAssistant extends BaseBuilder {

  private String currentNamespace;
  private String resource;
//	  private Cache currentCache;
//	  private boolean unresolvedCacheRef; // issue #676
  public MapperBuilderAssistant(Configuration configuration, String resource) {
    super(configuration);
    this.resource = resource;
  }

  public void setCurrentNamespace(String currentNamespace) {
    if (currentNamespace == null) {
      throw new BuilderException(
          "The mapper element requires a namespace attribute to be specified.");
    }

    if (this.currentNamespace != null && !this.currentNamespace.equals(currentNamespace)) {
      throw new BuilderException(
          "Wrong namespace. Expected '" + this.currentNamespace + "' but found '" + currentNamespace + "'.");
    }

    this.currentNamespace = currentNamespace;
  }

  public String applyCurrentNamespace(String base, boolean isReference) {
    if (base == null) {
      return null;
    }
    if (isReference) {
      // is it qualified with any namespace yet?
      if (base.contains(".")) {
        return base;
      }
    } else {
      // is it qualified with this namespace yet?
      if (base.startsWith(currentNamespace + ".")) {
        return base;
      }
      if (base.contains(".")) {
        throw new BuilderException(
            "Dots are not allowed in element names, please remove it from " + base);
      }
    }
    return currentNamespace + "." + base;
  }

  public MappedStatement addMappedStatement(String id, SqlSource sqlSource) {

    id = applyCurrentNamespace(id, false);
    MappedStatement.Builder statementBuilder = new MappedStatement.Builder(configuration, id, sqlSource);
    MappedStatement statement = statementBuilder.build();
    configuration.addMappedStatement(statement);
    return statement;
  }

  public String getResource() {
    return resource;
  }
}
