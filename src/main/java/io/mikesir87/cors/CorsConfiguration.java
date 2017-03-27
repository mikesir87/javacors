package io.mikesir87.cors;

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
}
