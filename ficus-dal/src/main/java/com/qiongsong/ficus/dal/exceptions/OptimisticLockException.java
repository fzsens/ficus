package com.qiongsong.ficus.dal.exceptions;

/**
 * 更新异常
 * @ClassName: OptimisticLockException
 * @author thierry.fu
 * @date Jan 11, 2016 3:19:58 PM
 */
public class OptimisticLockException extends PersistenceException {

  /**
   *
   */
  private static final long serialVersionUID = 4710611978152032377L;

  public OptimisticLockException() {
    super();
  }

  public OptimisticLockException(String message) {
    super(message);
  }

  public OptimisticLockException(String message, Throwable cause) {
    super(message, cause);
  }

  public OptimisticLockException(Throwable cause) {
    super(cause);
  }
}
