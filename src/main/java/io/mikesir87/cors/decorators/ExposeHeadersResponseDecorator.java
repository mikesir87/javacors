package io.mikesir87.cors.decorators;

import io.mikesir87.cors.CorsConfiguration;
import io.mikesir87.cors.ResponseHandler;
import io.mikesir87.cors.validators.CorsRequestContext;

/**
 * A {@link ResponseDecorator} that sets the <code>Access-Control-Expose-Headers</code> header.
 * Since validation has occurred prior to the decorator and all requested headers must be authorized, the header value
 * is simply the requested headers.
 *
 * @author Michael Irwin
 */
public class ExposeHeadersResponseDecorator implements ResponseDecorator {

  private static final String HEADER_NAME = "Access-Control-Expose-Headers";

  @Override
  public void decorateResponse(ResponseHandler responseHandler,
                               CorsRequestContext requestContext,
                               CorsConfiguration configuration) {
    if (!requestContext.isPreFlightRequest() && configuration.getExposedHeaders() != null) {
      responseHandler.addHeader(HEADER_NAME, String.join(", ", configuration.getExposedHeaders()));
    }
  }
}
