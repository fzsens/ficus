package com.qiongsong.ficus.dal.support;

import java.io.Serializable;

/**
 * paginator helper.
 *
 * @ClassName: Paginator
 * @author thierry.fu
 * @date Jan 4, 2016 9:04:34 PM
 */
public class PagingInfo implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 8742175654162328267L;

  private int limit;

  private int offset;

  public PagingInfo() {
    this.limit = 20;
    this.offset = 1;
  }

  public PagingInfo(int offset, int limit) {
    super();
    this.limit = limit;
    this.offset = offset;
  }

  public int getLimit() {
    return limit;
  }

  public void setLimit(int limit) {
    this.limit = limit;
  }

  public int getOffset() {
    return offset;
  }

  public void setOffset(int offset) {
    this.offset = offset;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("PagingInfo");
    sb.append("{offset=").append(this.offset);
    sb.append(", limit=").append(this.limit);
    sb.append('}');
    return sb.toString();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + limit;
    result = prime * result + offset;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    PagingInfo other = (PagingInfo) obj;
    if (limit != other.limit)
      return false;
    if (offset != other.offset)
      return false;
    return true;
  }

}
