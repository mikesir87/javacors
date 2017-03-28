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
 * Unit test for the {@link CredentialResponseDecorator} class.
 *
 * @author Michael Irwin
 */
public class CredentialResponseDecoratorTest {

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  @Mock
  CorsRequestContext requestContext;

  @Mock
  CorsConfiguration configuration;

  @Mock
  ResponseHandler responseHandler;

  private CredentialResponseDecorator decorator = new CredentialResponseDecorator();

  @Test
  public void validateHeaderNotAddedWhenCredentialsNotAllowed() {
    context.checking(new Expectations() { {
      allowing(configuration).allowCredentials();
      will(returnValue(false));
    } });
    decorator.decorateResponse(responseHandler, requestContext, configuration);
  }

  @Test
  public void validateHeaderAddedWhenCredentialsAllowed() {
    context.checking(new Expectations() { {
      allowing(configuration).allowCredentials();
      will(returnValue(true));
      oneOf(responseHandler).addHeader("Access-Control-Allow-Credentials", "true");
    } });
    decorator.decorateResponse(responseHandler, requestContext, configuration);
  }
}
