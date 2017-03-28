package io.mikesir87.javacors.decorators;

import io.mikesir87.javacors.CorsConfiguration;
import io.mikesir87.javacors.ResponseHandler;
import io.mikesir87.javacors.validators.CorsRequestContext;

/**
 * A {@link ResponseDecorator} that sets the <code>Access-Control-Allow-Headers</code> header if a pre-flight check.
 * Since validation has occurred prior to the decorator and all requested headers must be authorized, the header value
 * is simply the requested headers.
 *
 * @author Michael Irwin
 */
public class AllowedHeadersResponseDecorator implements ResponseDecorator {

  private static final String HEADER_NAME = "Access-Control-Allow-Headers";

  @Override
  public void decorateResponse(ResponseHandler responseHandler,
                               CorsRequestContext requestContext,
                               CorsConfiguration configuration) {
    if (requestContext.isPreFlightRequest()) {
      responseHandler.addHeader(HEADER_NAME, requestContext.getRequestedHeaders());
    }
  }
}
