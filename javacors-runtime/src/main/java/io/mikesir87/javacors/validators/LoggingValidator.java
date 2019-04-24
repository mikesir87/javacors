package io.mikesir87.javacors.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.mikesir87.javacors.CorsConfiguration;

/**
 * A {@link CorsValidator} that wraps another {@link CorsValidator} and adds
 * log statements to the results.
 *
 * @author Michael Irwin
 */
public class LoggingValidator implements CorsValidator {

  private static final Logger logger = LoggerFactory.getLogger(LoggingValidator.class);

  private final CorsValidator delegate;

  /**
   * Create a new validator using the provided validator as the delegate.
   * @param delegate The validator to perform actual validation
   */
  public LoggingValidator(CorsValidator delegate) {
    this.delegate = delegate;
  }

  @Override
  public String getName() {
    return delegate.getName();
  }

  @Override
  public boolean shouldAddHeaders(CorsRequestContext requestContext, CorsConfiguration configuration) {
    final boolean result = delegate.shouldAddHeaders(requestContext, configuration);

    if (logger.isTraceEnabled()) {
      logger.trace("Validation result for {} is {}", getName(), result);
    }

    return result;
  }
}
