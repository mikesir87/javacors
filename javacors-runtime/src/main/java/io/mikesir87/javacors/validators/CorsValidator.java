package io.mikesir87.javacors.validators;

import io.mikesir87.javacors.CorsConfiguration;

/**
 * Definition for a validator that handles a single validation for a pre-flight request. Each step is defined in the
 * CORS specification (https://www.w3.org/TR/cors).
 *
 * Each handler is given the ability to accept/reject the request. CORS headers should be be added if ALL validators
 * authorize the addition of headers.  If a single validator rejects, no headers should be added.
 *
 * @author Michael Irwin
 */
public interface CorsValidator {

  /**
   * Should the CORS headers be added to the provided request?
   * @param requestContext Details about the request in question.
   * @param configuration Server-based configuration for CORS-handling.
   * @return True if headers should be added. False if not.
   */
  boolean shouldAddHeaders(CorsRequestContext requestContext, CorsConfiguration configuration);
}
