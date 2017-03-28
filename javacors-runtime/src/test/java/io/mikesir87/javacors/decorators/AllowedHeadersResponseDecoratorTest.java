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
 * Unit test for the {@link AllowedHeadersResponseDecorator} class.
 *
 * @author Michael Irwin
 */
public class AllowedHeadersResponseDecoratorTest {

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  @Mock
  CorsRequestContext requestContext;

  @Mock
  CorsConfiguration configuration;

  @Mock
  ResponseHandler responseHandler;

  private AllowedHeadersResponseDecorator decorator = new AllowedHeadersResponseDecorator();

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
    final String requestedHeaders = "content-type, accepts";
    context.checking(new Expectations() { {
      allowing(requestContext).isPreFlightRequest();
      will(returnValue(true));
      allowing(requestContext).getRequestedHeaders();
      will(returnValue(requestedHeaders));
      oneOf(responseHandler).addHeader("Access-Control-Allow-Headers", requestedHeaders);
    } });
    decorator.decorateResponse(responseHandler, requestContext, configuration);
  }
}
