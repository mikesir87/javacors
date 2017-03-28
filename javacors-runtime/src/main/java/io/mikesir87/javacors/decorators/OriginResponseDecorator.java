package io.mikesir87.javacors.decorators;

import io.mikesir87.javacors.CorsConfiguration;
import io.mikesir87.javacors.ResponseHandler;
import io.mikesir87.javacors.validators.CorsRequestContext;

/**
 * A {@link ResponseDecorator} that sets the <code>Access-Control-Allow-Origin</code> header.
 *
 * @author Michael Irwin
 */
public class OriginResponseDecorator implements ResponseDecorator {

  private static final String HEADER_NAME = "Access-Control-Allow-Origin";

  @Override
  public void decorateResponse(ResponseHandler responseHandler,
                               CorsRequestContext requestContext,
                               CorsConfiguration configuration) {
    responseHandler.addHeader(HEADER_NAME, requestContext.getOriginHeader());
  }
}
