package io.mikesir87.javacors.validators;

import io.mikesir87.javacors.CorsConfiguration;

/**
 * A {@link CorsValidator} that validates the <code>Access-Control-Request-Headers</code> header.
 *
 * @author Michael Irwin
 */
public class RequestedHeadersValidator implements CorsValidator {

  @Override
  public boolean shouldAddHeaders(CorsRequestContext requestContext, CorsConfiguration configuration) {
    if (!requestContext.isPreFlightRequest())
      return true;

    return requestContext.getRequestedHeadersAsList().stream()
      .map(String::toLowerCase)
      .filter(requestedHeader -> !configuration.getAuthorizedHeaders().contains(requestedHeader))
      .map(unauthorizedHeader -> false)
      .findFirst().orElse(true);
  }

}
