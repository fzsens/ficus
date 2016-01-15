package com.qiongsong.ficus.dal.support;

import java.io.Serializable;

import com.qiongsong.ficus.commons.untils.NameUtils;

/**
 * @ClassName: BaseQueryCondition
 * @author thierry.fu
 * @date Jan 6, 2016 2:35:05 PM
 */
public class BaseQueryCondition extends BaseObject implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = -745805717224447496L;

  public String getQueryName() {
    String simpleName = this.getClass().getSimpleName();
    return this.getClass().getPackage().getName() + "." + NameUtils
        .uncapitalize(simpleName.substring(0, simpleName.length() - 9));
  }
}
