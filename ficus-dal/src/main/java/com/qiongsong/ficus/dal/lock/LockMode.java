package com.qiongsong.ficus.dal.lock;

/**
 * 锁模式
 * @ClassName: LockMode
 * @author thierry.fu
 * @date Jan 11, 2016 12:02:08 PM
 */
public enum LockMode {
  /**
   * No lock required. If an object is requested with this lock mode, a
   * <tt>READ</tt> lock will be obtained if it is necessary to actually read the
   * state from the database, rather than pull it from a cache.<br>
   * <br>
   * This is the "default" lock mode.
   */
  NONE(0, "none"),
  /**
   * Optimistically assume that transaction will not experience contention for
   * entities. The entity version will be verified near the transaction end.
   */
  OPTIMISTIC(6, "optimistic"),
  /**
   * Transaction will obtain a database lock immediately.
   */
  PESSIMISTIC(13, "pessimistic_write");

  private final int level;

  private final String externalForm;

  private LockMode(int level, String externalForm) {
    this.level = level;
    this.externalForm = externalForm;
  }

  public String toExternalForm() {
    return externalForm;
  }

  /**
   * Check if this lock mode is more restrictive than the given lock mode.
   *
   * @param mode LockMode to check
   *
   * @return true if this lock mode is more restrictive than given lock mode
   */
  public boolean greaterThan(LockMode mode) {
    return level > mode.level;
  }

  /**
   * Check if this lock mode is less restrictive than the given lock mode.
   *
   * @param mode LockMode to check
   *
   * @return true if this lock mode is less restrictive than given lock mode
   */
  public boolean lessThan(LockMode mode) {
    return level < mode.level;
  }

  public static LockMode fromExternalForm(String externalForm) {
    if (externalForm == null) {
      return NONE;
    }

    for (LockMode lockMode : LockMode.values()) {
      if (lockMode.externalForm.equalsIgnoreCase(externalForm)) {
        return lockMode;
      }
    }

    throw new IllegalArgumentException(
        "Unable to interpret LockMode reference from incoming external form : "
            + externalForm);
  }

}
