package io.mikesir87.javacors;

import io.mikesir87.javacors.decorators.ResponseDecorator;
import io.mikesir87.javacors.validators.CorsRequestContext;
import io.mikesir87.javacors.validators.CorsValidator;
import io.mikesir87.javacors.validators.DelegatingCorsRequestContext;
import org.hamcrest.CustomTypeSafeMatcher;
import org.hamcrest.Matcher;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Unit test for the {@link DefaultCorsProcessor} class.
 *
 * @author Michael Irwin
 */
public class DefaultCorsProcessorTest {

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  @Mock
  CorsConfiguration corsConfiguration;

  @Mock
  CorsValidator validator1;

  @Mock
  CorsValidator validator2;

  @Mock
  ResponseDecorator decorator1;

  @Mock
  ResponseDecorator decorator2;

  @Mock
  RequestContext requestContext;

  @Mock
  ResponseHandler responseHandler;

  private DefaultCorsProcessor processor;

  @Before
  public void setUp() {
    processor = new DefaultCorsProcessor(corsConfiguration, Arrays.asList(validator1, validator2),
      Arrays.asList(decorator1, decorator2));
  }

  @Test
  public void validateResponseHeadersNotAddedWhenValidationFails() {
    context.checking(new Expectations() { {
      oneOf(validator1).shouldAddHeaders(with(requestContextMatcher()), with(corsConfiguration));
      will(returnValue(true));
      oneOf(validator2).shouldAddHeaders(with(requestContextMatcher()), with(corsConfiguration));
      will(returnValue(false));
    } });
    assertThat(processor.processRequest(requestContext, responseHandler), is(false));
  }

  @Test
  public void validateResponseHeadersAddedWhenValidationPasses() {
    context.checking(new Expectations() { {
      oneOf(validator1).shouldAddHeaders(with(requestContextMatcher()), with(corsConfiguration));
      will(returnValue(true));
      oneOf(validator2).shouldAddHeaders(with(requestContextMatcher()), with(corsConfiguration));
      will(returnValue(true));

      oneOf(decorator1).decorateResponse(with(responseHandler), with(requestContextMatcher()), with(corsConfiguration));
      oneOf(decorator2).decorateResponse(with(responseHandler), with(requestContextMatcher()), with(corsConfiguration));
    } });
    assertThat(processor.processRequest(requestContext, responseHandler), is(true));
  }

  private Matcher<CorsRequestContext> requestContextMatcher() {
    return new CustomTypeSafeMatcher<CorsRequestContext>("corsRequestContext") {
      @Override
      protected boolean matchesSafely(CorsRequestContext corsRequestContext) {
        return corsRequestContext instanceof DelegatingCorsRequestContext &&
          ((DelegatingCorsRequestContext) corsRequestContext).getRequestContext() == requestContext;
      }
    };
  }
}
