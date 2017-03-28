package io.mikesir87.cors;

/**
 * A handler that is used to manipulate a HTTP response.
 *
 * @author Michael Irwin
 */
public interface ResponseHandler {

  /**
   * Add a response header with the provided name and value.
   * @param name The name of the header
   * @param value The value of the header
   */
  void addHeader(String name, String value);
}
