package io.mikesir87.javacors.decorators;

import io.mikesir87.javacors.CorsConfiguration;
import io.mikesir87.javacors.ResponseHandler;
import io.mikesir87.javacors.validators.CorsRequestContext;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

/**
 * Unit test for the {@link MaxAgeResponseDecorator} class.
 *
 * @author Michael Irwin
 */
public class MaxAgeResponseDecoratorTest {

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  @Mock
  CorsRequestContext requestContext;

  @Mock
  CorsConfiguration configuration;

  @Mock
  ResponseHandler responseHandler;

  private MaxAgeResponseDecorator decorator = new MaxAgeResponseDecorator();

  @Test
  public void validateHeaderNotAddedWhenNotPreFlightCheck() {
    context.checking(new Expectations() { {
      allowing(requestContext).isPreFlightRequest();
      will(returnValue(false));
    } });
    decorator.decorateResponse(responseHandler, requestContext, configuration);
  }

  @Test
  public void validateHeaderAddedWhenPreFlightCheck() {
    final Integer maxAge = 600;
    context.checking(new Expectations() { {
      allowing(requestContext).isPreFlightRequest();
      will(returnValue(true));
      allowing(configuration).getMaxAgeCacheValue();
      will(returnValue(maxAge));
      oneOf(responseHandler).addHeader("Access-Control-Max-Age", maxAge.toString());
    } });
    decorator.decorateResponse(responseHandler, requestContext, configuration);
  }
}
