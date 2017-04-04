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
 * Unit test for the {@link AllowedMethodsResponseDecorator} class.
 *
 * @author Michael Irwin
 */
public class AllowedMethodsResponseDecoratorTest {

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  @Mock
  ResponseHandler responseHandler;

  @Mock
  CorsRequestContext requestContext;

  @Mock
  CorsConfiguration corsConfiguration;

  private AllowedMethodsResponseDecorator decorator = new AllowedMethodsResponseDecorator();

  @Test
  public void validateNonPreFlightRequest() {
    context.checking(requestContextExpectations(false, null));
    decorator.decorateResponse(responseHandler, requestContext, corsConfiguration);
  }

  @Test
  public void validateWhenRequestedMethodNotSet() {
    context.checking(requestContextExpectations(true, null));
    decorator.decorateResponse(responseHandler, requestContext, corsConfiguration);
  }

  @Test
  public void validateWhenRequestIsSimpleMethod() {
    context.checking(requestContextExpectations(true, "GET"));
    decorator.decorateResponse(responseHandler, requestContext, corsConfiguration);
  }

  @Test
  public void validateWhenRequestIsSimpleMethodButDifferentCase() {
    context.checking(requestContextExpectations(true, "get"));
    decorator.decorateResponse(responseHandler, requestContext, corsConfiguration);
  }

  @Test
  public void validateWhenRequestMethodIsNotSimple() {
    context.checking(requestContextExpectations(true, "PUT"));
    context.checking(new Expectations() { {
      oneOf(responseHandler).addHeader("Access-Control-Allow-Methods", "PUT");
    } });

    decorator.decorateResponse(responseHandler, requestContext, corsConfiguration);
  }

  private Expectations requestContextExpectations(final boolean isPreFlight, final String requestedMethod) {
    return new Expectations() { {
      allowing(requestContext).isPreFlightRequest();
      will(returnValue(isPreFlight));
      allowing(requestContext).getRequestedMethod();
      will(returnValue(requestedMethod));
    } };
  }
}
