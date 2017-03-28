package io.mikesir87.cors.decorators;

import io.mikesir87.cors.CorsConfiguration;
import io.mikesir87.cors.ResponseHandler;
import io.mikesir87.cors.validators.CorsRequestContext;

/**
 * Definition for a single decorator that adds a single header to a CORS-based response.
 *
 * It is assumed that all validation has been performed prior and that the decorators are invoked if and only if all
 * validation succeeds.
 *
 * @author Michael Irwin
 */
public interface ResponseDecorator {

  /**
   * Decorate the response based on the provided request context and configuration.
   * @param responseHandler The response to decorate
   * @param requestContext Context information for the request.
   * @param configuration CORS configuration
   */
  void decorateResponse(ResponseHandler responseHandler,
                        CorsRequestContext requestContext,
                        CorsConfiguration configuration);
}
