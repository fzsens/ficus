package com.qiongsong.ficus.dal.lock;

/**
 * Optimistic Locking Support
 *
 * 如果需要添加乐观锁支持，必须实现此接口，默认在数据库中需要有 VERSION 字段
 * @ClassName: Version
 * @author thierry.fu
 * @date Jan 11, 2016 11:02:59 AM
 */
public interface Versionable {

  int getVersion();

  void setVersion(int version);

}
