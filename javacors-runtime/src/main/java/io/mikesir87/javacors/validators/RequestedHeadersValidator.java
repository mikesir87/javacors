package io.mikesir87.javacors.validators;

import io.mikesir87.javacors.CorsConfiguration;

import java.util.List;
import java.util.stream.Collectors;

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

    List<String> normalizedAuthorizedHeaders = configuration.getAuthorizedHeaders()
      .stream()
      .map(String::toLowerCase)
      .collect(Collectors.toList());

    return requestContext.getRequestedHeadersAsList().stream()
      .map(String::toLowerCase)
      .filter(requestedHeader -> !normalizedAuthorizedHeaders.contains(requestedHeader))
      .map(unauthorizedHeader -> false)
      .findFirst().orElse(true);
  }

}
