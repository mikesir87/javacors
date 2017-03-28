package io.mikesir87.javacors.validators;

import io.mikesir87.javacors.CorsConfiguration;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Unit test for the {@link RequestedMethodValidator} class.
 *
 * @author Michael Irwin
 */
public class RequestedMethodValidatorTest {

  private static final String AUTHORIZED_METHOD = "GET";

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  @Mock
  private CorsRequestContext requestContext;

  @Mock
  private CorsConfiguration corsConfiguration;

  private RequestedMethodValidator validator = new RequestedMethodValidator();

  @Test
  public void validateNonPreFlightRequestsAreAllowed() {
    context.checking(createExpectations(false, null));
    assertThat(validator.shouldAddHeaders(requestContext, corsConfiguration), is(true));
  }

  @Test
  public void validateRequestWhenRequestedMethodNotSet() {
    context.checking(createExpectations(true, null));
    assertThat(validator.shouldAddHeaders(requestContext, corsConfiguration), is(false));
  }

  @Test
  public void validateRequestWhenRequestedMethodMatches() {
    context.checking(createExpectations(true, AUTHORIZED_METHOD));
    assertThat(validator.shouldAddHeaders(requestContext, corsConfiguration), is(true));
  }

  @Test
  public void validateRequestWhenRequestedMethodDoesNotMatch() {
    context.checking(createExpectations(true, "SOME_OTHER_METHOD"));
    assertThat(validator.shouldAddHeaders(requestContext, corsConfiguration), is(false));
  }

  private Expectations createExpectations(final boolean isPreFlight, final String requestedMethod) {
    return new Expectations() { {
      allowing(requestContext).isPreFlightRequest();
      will(returnValue(isPreFlight));

      allowing(requestContext).getRequestedMethod();
      will(returnValue(requestedMethod));

      allowing(corsConfiguration).getAuthorizedMethods();
      will(returnValue(Collections.singletonList(AUTHORIZED_METHOD)));
    } };
  }

}
