package com.qiongsong.ficus.dal.support;

import java.io.Serializable;

/**
 * base item be implement by another query result.
 *
 * @ClassName: BaseItem
 * @author thierry.fu
 * @date Jan 6, 2016 2:38:54 PM
 */
public class BaseQueryItem extends BaseObject implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 3589929479274789034L;

  protected Integer version;

  protected Integer getVersion() {
    return version;
  }

  protected void setVersion(Integer version) {
    this.version = version;
  }

}
