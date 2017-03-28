package io.mikesir87.cors;

/**
 * Context provider definition related to a specific request.
 *
 * @author Michael Irwin
 */
public interface RequestContext {

  /**
   * Get the value for the origin header.
   * @return The origin, if one is set. Otherwise, null.
   */
  String getOriginHeader();

  /**
   * Get the HTTP method used for the request (GET, POST, etc.)
   * @return The HTTP method.
   */
  String getRequestMethod();

  /**
   * Get the value of the <code>Access-Control-Request-Method</code> header, if one is set.
   * @return The value of the header, if set. Otherwise, null.
   */
  String getRequestedMethod();

  /**
   * Get the value of the <code>Access-Control-Request-Headers</code> header, if one is set.
   * @return The value of the header, if set. Otherwise, null.
   */
  String getRequestedHeaders();
}
