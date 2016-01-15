package com.qiongsong.ficus.ibatis.builder;

import com.qiongsong.ficus.ibatis.meta.MetaObject;

/**
 *
 */
public final class SystemMetaObject {

  public static final MetaObject NULL_META_OBJECT = MetaObject.forObject(NullObject.class);

  private SystemMetaObject() {
    // Prevent Instantiation of Static Class
  }

  private static class NullObject {
  }

}
