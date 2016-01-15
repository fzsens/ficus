package com.qiongsong.ficus.dal.model;

import java.math.BigDecimal;
import java.util.Date;

import com.qiongsong.ficus.dal.support.BaseModel;

/**
 *
 * @ClassName: User
 * @author thierry.fu
 * @date Dec 31, 2015 12:01:38 PM
 */
/**
 *
 * @ClassName: User
 * @author thierry.fu
 * @date Dec 31, 2015 12:01:38 PM
 */
public class User extends BaseModel {

  /**
  *
  */
  private static final long serialVersionUID = 8700371519593787098L;

  private Integer userId;

  private String userName;

  private Double blance;

  private BigDecimal totalBlance;

  private Date createTime;

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public Double getBlance() {
    return blance;
  }

  public void setBlance(Double blance) {
    this.blance = blance;
  }

  public BigDecimal getTotalBlance() {
    return totalBlance;
  }

  public void setTotalBlance(BigDecimal totalBlance) {
    this.totalBlance = totalBlance;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

}
