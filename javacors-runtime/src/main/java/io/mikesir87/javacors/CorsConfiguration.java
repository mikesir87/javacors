package io.mikesir87.javacors;

import java.util.List;

/**
 * Configuration context to be used during validation.
 *
 * @author Michael Irwin
 */
public interface CorsConfiguration {

  /**
   * Get the list of origins that are authorized for CORS requested.
   * @return A list of origins that should be authorized for requests.
   */
  List<String> getAuthorizedOrigins();

  /**
   * Get the authorized methods that can make CORS requests.
   * @return A list of authorized HTTP methods
   */
  List<String> getAuthorizedMethods();

  /**
   * Get the authorized headers for CORS requests. All headers MUST be lower-cased.
   * @return A list of authorized headers
   */
  List<String> getAuthorizedHeaders();

  /**
   * Get the list of headers (comma-separated) that should be exposed to the client's browser. These headers will be
   * added to the <code>Access-Control-Expose-Headers</code> response header.
   * @return A list of headers to expose
   */
  List<String> getExposedHeaders();

  /**
   * Are credentials allowed for CORS-based requests?
   * @return True if allowed. Otherwise, false.
   */
  boolean allowCredentials();

  /**
   * Get the value (in seconds) that a preflight check should be cached.
   * @return The number (in seconds) to cache a preflight check.
   */
  Integer getMaxAgeCacheValue();
}
