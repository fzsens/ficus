package com.qiongsong.ficus.dal.jdbc;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.qiongsong.ficus.dal.exceptions.BuilderException;
import com.qiongsong.ficus.ibatis.MyBatisSqlFactory;
import com.qiongsong.ficus.ibatis.builder.Configuration;
import com.qiongsong.ficus.ibatis.xml.XMLMapperBuilder;

/**
 * Spring FactoryBean 用于生成 SqlFactory.
 * @ClassName: BatisSqlFactoryBean
 * @author thierry.fu
 * @date Jan 7, 2016 9:37:41 PM
 */
public class IbatisSqlFactoryBean implements FactoryBean<MyBatisSqlFactory>,
    InitializingBean, ResourceLoaderAware {

  private String[] sqlLocations;

  private Configuration configuration;

  private MyBatisSqlFactory sqlFactory;

  private ResourceLoader resourceLoader;

  public void afterPropertiesSet() throws Exception {
    this.configuration = new Configuration();
    for (String location : sqlLocations) {
      try {
        if (this.resourceLoader instanceof ResourcePatternResolver) {
          Resource[] resources = ((ResourcePatternResolver) this.resourceLoader)
              .getResources(location);
          readResources(resources);
        }
        else {
          Resource resource = resourceLoader.getResource(location);
          readResource(resource);
        }

      }
      catch (Exception e) {
        throw new BuilderException("read sqlLocation fail cause:" + location,
            e);
      }
    }
  }

  /**
   * 读取resource
   *
   * @param resources
   */
  private void readResources(Resource[] resources) {

    for (Resource resource : resources) {
      readResource(resource);
    }
  }

  private void readResource(Resource resource) {
    try {
      XMLMapperBuilder mapperParser = new XMLMapperBuilder(
          resource.getInputStream(), this.configuration,
          resource.getFilename());
      mapperParser.parse();
    }
    catch (Exception e) {
      throw new BuilderException("读取resource文件失败:" + resource.getFilename(), e);
    }
  }

  @Override
  public MyBatisSqlFactory getObject() throws Exception {
    this.sqlFactory = new MyBatisSqlFactory(this.configuration);
    return this.sqlFactory;
  }

  public Class<?> getObjectType() {
    return MyBatisSqlFactory.class;
  }

  public boolean isSingleton() {
    return true;
  }

  public void setSqlLocations(String[] sqlLocations) {
    this.sqlLocations = sqlLocations;
  }

  @Override
  public void setResourceLoader(ResourceLoader resourceLoader) {
    this.resourceLoader = resourceLoader;
  }
}
