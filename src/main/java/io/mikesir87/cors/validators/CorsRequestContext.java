package io.mikesir87.cors.validators;

import io.mikesir87.cors.RequestContext;

import java.util.List;

/**
 * An extension to the {@link RequestContext} that adds CORS-related methods.
 *
 * This was separated out to encapsulate the CORS-related knowledge from the vendor-provided {@link RequestContext}s.
 *
 * @author Michael Irwin
 */
public interface CorsRequestContext extends RequestContext {

  /**
   * Is this request for a pre-flight request?
   * @return True if pre-flight. Otherwise, false.
   */
  boolean isPreFlightRequest();

  /**
   * Get the requested headers as a list.
   * @return The requested headers, as a list. May be empty, but never null.
   */
  List<String> getRequestedHeadersAsList();
}
