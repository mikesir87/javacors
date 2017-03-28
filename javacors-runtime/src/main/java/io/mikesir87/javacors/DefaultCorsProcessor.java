package io.mikesir87.javacors;

import io.mikesir87.javacors.decorators.CredentialResponseDecorator;
import io.mikesir87.javacors.decorators.OriginResponseDecorator;
import io.mikesir87.javacors.decorators.ResponseDecorator;
import io.mikesir87.javacors.validators.*;

import java.util.Arrays;
import java.util.List;

/**
 * A default {@CorsProcessor} that uses {@link CorsValidator}s to validate a request and then applies headers
 * as necessary.
 *
 * @author Michael Irwin
 */
public class DefaultCorsProcessor implements CorsProcessor {

  private final CorsConfiguration configuration;
  private final List<CorsValidator> validators;
  private final List<ResponseDecorator> decorators;

  public DefaultCorsProcessor(CorsConfiguration configuration) {
    this.configuration = configuration;

    validators = Arrays.asList(
      new OriginValidator(),
      new RequestedHeadersValidator(),
      new RequestedMethodValidator()
    );

    decorators = Arrays.asList(
      new CredentialResponseDecorator(),
      new OriginResponseDecorator()
    );
  }

  DefaultCorsProcessor(CorsConfiguration configuration,
                       List<CorsValidator> validators,
                       List<ResponseDecorator> decorators) {
    this.configuration = configuration;
    this.validators = validators;
    this.decorators = decorators;
  }

  @Override
  public boolean processRequest(RequestContext requestContext, ResponseHandler responseHandler) {
    CorsRequestContext context = new DelegatingCorsRequestContext(requestContext);
    for (CorsValidator validator : validators) {
      if (!validator.shouldAddHeaders(context, configuration))
        return false;
    }

    for (ResponseDecorator decorator : decorators)
      decorator.decorateResponse(responseHandler, context, configuration);

    return true;
  }
}
