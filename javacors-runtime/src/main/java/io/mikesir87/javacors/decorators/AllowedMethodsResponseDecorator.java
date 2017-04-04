package io.mikesir87.javacors.decorators;

import io.mikesir87.javacors.CorsConfiguration;
import io.mikesir87.javacors.ResponseHandler;
import io.mikesir87.javacors.validators.CorsRequestContext;

import java.util.Arrays;
import java.util.List;

/**
 * A {@link ResponseDecorator} that adds the <code>Access-Control-Allow-Methods</code> header if the request is
 * not a simple method (defined as GET, HEAD, and POST).
 *
 * @author Michael Irwin
 */
public class AllowedMethodsResponseDecorator implements ResponseDecorator {

  private static final String HEADER_NAME = "Access-Control-Allow-Methods";
  private static final List<String> SIMPLE_METHODS = Arrays.asList("GET", "HEAD", "POST");

  @Override
  public void decorateResponse(ResponseHandler responseHandler,
                               CorsRequestContext requestContext,
                               CorsConfiguration configuration) {
    if (!requestContext.isPreFlightRequest() || requestContext.getRequestedMethod() == null)
      return;

    if (!SIMPLE_METHODS.contains(requestContext.getRequestedMethod().toUpperCase())) {
      responseHandler.addHeader(HEADER_NAME, requestContext.getRequestedMethod());
    }
  }

}
