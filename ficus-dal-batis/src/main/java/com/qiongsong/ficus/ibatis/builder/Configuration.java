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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.qiongsong.ficus.ibatis.meta.MetaObject;
import com.qiongsong.ficus.ibatis.xml.XNode;

/**
 * 解析配置文件，删除Mybaits一些高级特性的支持
 * @author Clinton Begin
 * @author thierry.fu
 */
public class Configuration {

  //文件
  protected final Set<String> loadedResources = new HashSet<String>();

  //查询语句
  protected final Map<String, MappedStatement> mappedStatements = new HashMap<String, MappedStatement>();

  //sql片段
  protected final Map<String, XNode> sqlFragments = new HashMap<String, XNode>();

  protected Properties variables = new Properties();

  public Map<String, XNode> getSqlFragments() {
    return sqlFragments;
  }

  public Map<String, MappedStatement> getMappedStatements() {
    return mappedStatements;
  }

  public boolean isResourceLoaded(String resource) {
    return loadedResources.contains(resource);
  }

  public void addLoadedResource(String resource) {
    loadedResources.add(resource);
  }

  public Properties getVariables() {
    return variables;
  }

  public void addMappedStatement(MappedStatement ms) {
    mappedStatements.put(ms.getId(), ms);
  }

  public MetaObject newMetaObject(Object object) {
    return MetaObject.forObject(object);
  }
}
