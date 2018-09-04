package io.mikesir87.javacors.validators;

import io.mikesir87.javacors.CorsConfiguration;

import java.util.List;

/**
 * A {@link CorsValidator} that validates that the requested request has an Origin header that is allowed by the
 * server configuration.
 *
 * @author Michael Irwin
 */
public class OriginValidator implements CorsValidator {

  private static final String WILDCARD_ORIGIN = "*";

  public boolean shouldAddHeaders(CorsRequestContext requestContext, CorsConfiguration configuration) {
    String origin = requestContext.getOriginHeader();

    // Origin header MUST be provided (Spec 6.2, Step #1)
    if (origin == null)
      return false;

    List<String> authorizedOrigins = configuration.getAuthorizedOrigins();
    if (authorizedOrigins.size() == 1 && authorizedOrigins.get(0).equals(WILDCARD_ORIGIN))
      return true;

    for (String authorizedOrigin : authorizedOrigins) {
      if (origin.equals(authorizedOrigin))
        return true;
      if (authorizedOrigin.startsWith(WILDCARD_ORIGIN + ".") &&
          origin.endsWith(authorizedOrigin.substring(WILDCARD_ORIGIN.length())))
        return true;
    }

    return false;
  }
}
