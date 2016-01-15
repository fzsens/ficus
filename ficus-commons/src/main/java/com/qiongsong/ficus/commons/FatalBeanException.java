package com.qiongsong.ficus.commons;

/**
 * Thrown on an unrecoverable problem encountered in the beans packages or
 * sub-packages: e.g. bad class or field.
 *
 * @ClassName: FatalBeanException
 * @author thierry.fu
 * @date 2015年12月23日 下午8:58:20
 */
public class FatalBeanException extends RuntimeException {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * Constructs a <code>FatalBeanException</code> with the specified message.
   *
   * @param msg
   *            the detail message.
   */
  public FatalBeanException(String msg) {
    super(msg);
  }

  /**
   * Constructs a <code>FatalBeanException</code> with the specified message
   * and root cause.
   *
   * @param msg
   *            the detail message.
   * @param t
   *            root cause
   */
  public FatalBeanException(String msg, Throwable t) {
    super(msg, t);
  }

}
