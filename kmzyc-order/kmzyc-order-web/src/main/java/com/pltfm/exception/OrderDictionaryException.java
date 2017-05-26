package com.pltfm.exception;

public class OrderDictionaryException extends RuntimeException {

  /**
	 * 
	 */
  private static final long serialVersionUID = 4134550684550268475L;

  public OrderDictionaryException() {
    super();
  }

  public OrderDictionaryException(String message) {
    super(message);
  }

  public OrderDictionaryException(String message, Throwable cause) {
    super(message, cause);
  }

  public OrderDictionaryException(Throwable cause) {
    super(cause);
  }

}
