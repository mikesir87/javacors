package io.mikesir87.cors.decorators;

import io.mikesir87.cors.CorsConfiguration;
import io.mikesir87.cors.ResponseHandler;
import io.mikesir87.cors.validators.CorsRequestContext;

/**
 * A {@link ResponseDecorator} that sets the <code>Access-Control-Max-Age</code> header if a pre-flight check.
 *
 * @author Michael Irwin
 */
public class MaxAgeResponseDecorator implements ResponseDecorator {

  private static final String HEADER_NAME = "Access-Control-Max-Age";

  @Override
  public void decorateResponse(ResponseHandler responseHandler,
                               CorsRequestContext requestContext,
                               CorsConfiguration configuration) {
    if (requestContext.isPreFlightRequest()) {
      responseHandler.addHeader(HEADER_NAME, configuration.getMaxAgeCacheValue().toString());
    }
  }
}
