package com.qiongsong.ficus.dal.support;

import com.qiongsong.ficus.dal.lock.Versionable;

/**
 * 如果需要使用支持乐观锁，继承此类
 * @ClassName: BaseModel
 * @author thierry.fu
 * @date Jan 11, 2016 11:11:02 AM
 */
public class BaseModel extends BaseObject implements Versionable {

  /**
   *
   */
  private static final long serialVersionUID = -8787441938780703740L;

  /**
   * default column is version
   */
  private int version;

  @Override
  public int getVersion() {
    return version;
  }

  @Override
  public void setVersion(int version) {
    this.version = version;
  }

}
