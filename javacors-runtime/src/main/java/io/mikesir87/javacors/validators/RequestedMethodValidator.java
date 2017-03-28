package io.mikesir87.javacors.validators;

import io.mikesir87.javacors.CorsConfiguration;

/**
 * A {@link CorsValidator} that validates the <code>Access-Control-Request-Method</code> header on pre-flight
 * requests.
 *
 * @author Michael Irwin
 */
public class RequestedMethodValidator implements CorsValidator {

  @Override
  public boolean shouldAddHeaders(CorsRequestContext requestContext, CorsConfiguration configuration) {
    if (!requestContext.isPreFlightRequest())
      return true;

    // Access-Control-Request-Method header is required and must match configured methods
    return requestContext.getRequestedMethod() != null &&
      configuration.getAuthorizedMethods().contains(requestContext.getRequestedMethod());
  }

}
