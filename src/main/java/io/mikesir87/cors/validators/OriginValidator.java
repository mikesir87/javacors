package io.mikesir87.cors.validators;

import io.mikesir87.cors.CorsConfiguration;
import io.mikesir87.cors.RequestContext;

/**
 * A {@link CorsValidator} that validates that the requested request has an Origin header that is allowed by the
 * server configuration.
 *
 * @author Michael Irwin
 */
public class OriginValidator implements CorsValidator {

  public boolean shouldAddHeaders(CorsRequestContext requestContext, CorsConfiguration configuration) {
    String origin = requestContext.getOriginHeader();

    // Origin header MUST be provided (Spec 6.2, Step #1)
    if (origin == null)
      return false;

    for (String authorizedOrigin : configuration.getAuthorizedOrigins()) {
      if (origin.equals(authorizedOrigin))
        return true;
    }

    return false;
  }
}
