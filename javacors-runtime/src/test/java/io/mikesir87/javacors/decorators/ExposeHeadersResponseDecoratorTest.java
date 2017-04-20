package io.mikesir87.javacors.decorators;

import io.mikesir87.javacors.CorsConfiguration;
import io.mikesir87.javacors.ResponseHandler;
import io.mikesir87.javacors.validators.CorsRequestContext;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Unit test for the {@link ExposeHeadersResponseDecorator} class.
 *
 * @author Michael Irwin
 */
public class ExposeHeadersResponseDecoratorTest {

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  @Mock
  CorsRequestContext requestContext;

  @Mock
  CorsConfiguration configuration;

  @Mock
  ResponseHandler responseHandler;

  private ExposeHeadersResponseDecorator decorator = new ExposeHeadersResponseDecorator();

  @Test
  public void validateHeaderNotAddedWhenPreFlightCheck() {
    context.checking(new Expectations() { {
      allowing(requestContext).isPreFlightRequest();
      will(returnValue(true));
    } });
    decorator.decorateResponse(responseHandler, requestContext, configuration);
  }

  @Test
  public void validateHeaderAddedWhenNotPreFlightCheckAndHeadersConfigured() {
    final List<String> headers = Arrays.asList("Content-Length", "Location");
    context.checking(new Expectations() { {
      allowing(requestContext).isPreFlightRequest();
      will(returnValue(false));
      allowing(configuration).getExposedHeaders();
      will(returnValue(headers));
      oneOf(responseHandler).addHeader("Access-Control-Expose-Headers", "Content-Length, Location");
    } });
    decorator.decorateResponse(responseHandler, requestContext, configuration);
  }

  @Test
  public void testDecorateResponseWithEmptyAccessControlExposeHeaders() {
    final List<String> headers = Collections.emptyList();
    context.checking(new Expectations() {
      {
        allowing(requestContext).isPreFlightRequest();
        will(returnValue(false));
        allowing(configuration).getExposedHeaders();
        will(returnValue(headers));
      }
    });
  }
}
