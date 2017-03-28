package io.mikesir87.javacors.validators;

import io.mikesir87.javacors.CorsConfiguration;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Unit test for the {@link RequestedHeadersValidator} class.
 *
 * @author Michael Irwin
 */
public class RequestedHeadersValidatorTest {

  private static final List<String> AUTHORIZED_HEADERS = Arrays.asList("content-type", "accepts", "authorization");

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  @Mock
  private CorsRequestContext requestContext;

  @Mock
  private CorsConfiguration corsConfiguration;

  private RequestedHeadersValidator validator = new RequestedHeadersValidator();

  @Test
  public void validateNonPreFlightRequestsAreAllowed() {
    context.checking(createExpectations(false, null));
    assertThat(validator.shouldAddHeaders(requestContext, corsConfiguration), is(true));
  }

  @Test
  public void validateRequestWhenNoHeaderRequestMade() {
    context.checking(createExpectations(true, Collections.emptyList()));
    assertThat(validator.shouldAddHeaders(requestContext, corsConfiguration), is(true));
  }

  @Test
  public void validateRequestWhenAllHeadersMatch() {
    context.checking(createExpectations(true, Arrays.asList("Content-Type", "authorization")));
    assertThat(validator.shouldAddHeaders(requestContext, corsConfiguration), is(true));
  }

  @Test
  public void validateRequestWhenRequestedHeaderNotAuthorized() {
    context.checking(createExpectations(true, Arrays.asList("Content-Type", "authorization", "cookie")));
    assertThat(validator.shouldAddHeaders(requestContext, corsConfiguration), is(false));
  }

  private Expectations createExpectations(final boolean isPreFlight, final List<String> requestedHeaders) {
    return new Expectations() { {
      allowing(requestContext).isPreFlightRequest();
      will(returnValue(isPreFlight));

      allowing(requestContext).getRequestedHeadersAsList();
      will(returnValue(requestedHeaders));

      allowing(corsConfiguration).getAuthorizedHeaders();
      will(returnValue(AUTHORIZED_HEADERS));
    } };
  }

}
