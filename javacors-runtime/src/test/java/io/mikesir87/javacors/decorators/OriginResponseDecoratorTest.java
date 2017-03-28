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
 * Unit test for the {@link OriginResponseDecorator} class.
 *
 * @author Michael Irwin
 */
public class OriginResponseDecoratorTest {

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  @Mock
  CorsRequestContext requestContext;

  @Mock
  CorsConfiguration configuration;

  @Mock
  ResponseHandler responseHandler;

  private OriginResponseDecorator decorator = new OriginResponseDecorator();

  @Test
  public void validateHeaderAdded() {
    final String origin = "http://localhost";
    context.checking(new Expectations() { {
      allowing(requestContext).getOriginHeader();
      will(returnValue(origin));
      oneOf(responseHandler).addHeader("Access-Control-Allow-Origin", origin);
    } });
    decorator.decorateResponse(responseHandler, requestContext, configuration);
  }
}
