package io.mikesir87.javacors;

import io.mikesir87.javacors.decorators.*;
import io.mikesir87.javacors.validators.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A default {@CorsProcessor} that uses {@link CorsValidator}s to validate a request and then applies headers
 * as necessary.
 *
 * @author Michael Irwin
 */
public class DefaultCorsProcessor implements CorsProcessor {

  private static final Logger logger = LoggerFactory.getLogger(DefaultCorsProcessor.class);

  private final CorsConfiguration configuration;
  private final List<CorsValidator> validators;
  private final List<ResponseDecorator> decorators;

  public DefaultCorsProcessor(CorsConfiguration configuration) {
    this.configuration = configuration;

    validators = Stream.of(
      new OriginValidator(),
      new RequestedHeadersValidator(),
      new RequestedMethodValidator()
    ).map(LoggingValidator::new)
    .collect(Collectors.toList());

    decorators = Arrays.asList(
      new CredentialResponseDecorator(),
      new OriginResponseDecorator(),
      new AllowedHeadersResponseDecorator(),
      new AllowedMethodsResponseDecorator(),
      new MaxAgeResponseDecorator(),
      new ExposeHeadersResponseDecorator()
    );
  }

  DefaultCorsProcessor(CorsConfiguration configuration,
                       List<CorsValidator> validators,
                       List<ResponseDecorator> decorators) {
    this.configuration = configuration;
    this.validators = validators;
    this.decorators = decorators;

    logger.info("CORS config: authorized headers: {}", configuration.getAuthorizedHeaders());
    logger.info("CORS config: authorized methods: {}", configuration.getAuthorizedMethods());
    logger.info("CORS config: authorized origins: {}", configuration.getAuthorizedOrigins());
    logger.info("CORS config: exposed headers: {}", configuration.getExposedHeaders());
    logger.info("CORS config: max age cache value: {}", configuration.getMaxAgeCacheValue());
    logger.info("CORS config: allow credentials: {}", configuration.allowCredentials());
  }

  @Override
  public boolean processRequest(RequestContext requestContext, ResponseHandler responseHandler) {
    CorsRequestContext context = new DelegatingCorsRequestContext(requestContext);

    for (CorsValidator validator : validators) {
      if (!validator.shouldAddHeaders(context, configuration)) {
        logger.debug("CORS validation failed while using validator '{}'", validator.getName());

        if (logger.isTraceEnabled()) {
          logger.trace(
            "Request context info: {} {} {} {}",
            requestContext.getOriginHeader(),
            requestContext.getRequestedHeaders(),
            requestContext.getRequestedMethod(),
            requestContext.getRequestMethod()
          );
        }

        return false;
      }
    }

    for (ResponseDecorator decorator : decorators)
      decorator.decorateResponse(responseHandler, context, configuration);

    return true;
  }
}
