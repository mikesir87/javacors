package io.mikesir87.javacors.validators;

import io.mikesir87.javacors.CorsConfiguration;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Unit test for the {@link OriginValidator} class.
 *
 * @author Michael Irwin
 */
public class OriginValidatorTest {

  private static final String AUTHORIZED_ORIGIN = "http://localhost";

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  @Mock
  private CorsRequestContext requestContext;

  @Mock
  private CorsConfiguration corsConfiguration;

  private OriginValidator validator = new OriginValidator();

  @Test
  public void validateWhenOriginNotSet() {
    context.checking(createExpectations(null));
    assertThat(validator.shouldAddHeaders(requestContext, corsConfiguration), is(false));
  }

  @Test
  public void validateWhenOriginIsAuthorized() {
    context.checking(createExpectations(AUTHORIZED_ORIGIN));
    assertThat(validator.shouldAddHeaders(requestContext, corsConfiguration), is(true));
  }

  @Test
  public void validateWhenOriginIsNotAuthorized() {
    context.checking(createExpectations("http://somewhere.else"));
    assertThat(validator.shouldAddHeaders(requestContext, corsConfiguration), is(false));
  }

  @Test
  public void validateWhenUsingWildcardOrigin() {
    context.checking(new Expectations() { {
      allowing(requestContext).getOriginHeader();
      will(returnValue("http://something.localhost"));
      allowing(corsConfiguration).getAuthorizedOrigins();
      will(returnValue(Collections.singletonList("*")));
    } });
    assertThat(validator.shouldAddHeaders(requestContext, corsConfiguration), is(true));
  }

  @Test
  public void validateWhenWildcardNotOnlyOrigin() {
    context.checking(new Expectations() { {
      allowing(requestContext).getOriginHeader();
      will(returnValue("http://something.localhost"));
      allowing(corsConfiguration).getAuthorizedOrigins();
      will(returnValue(Arrays.asList("http://somewhereelse.localhost/", "*")));
    } });
    assertThat(validator.shouldAddHeaders(requestContext, corsConfiguration), is(false));
  }

  @Test
  public void validateScopedWildcardWhenValid() {
    context.checking(new Expectations() { {
      allowing(requestContext).getOriginHeader();
      will(returnValue("http://test.localhost"));
      allowing(corsConfiguration).getAuthorizedOrigins();
      will(returnValue(Collections.singletonList("*.localhost")));
    } });

    assertThat(validator.shouldAddHeaders(requestContext, corsConfiguration), is(true));
  }

  @Test
  public void validateScopedWildcardWhenMultiLevelValid() {
    context.checking(new Expectations() { {
      allowing(requestContext).getOriginHeader();
      will(returnValue("http://another.test.localhost"));
      allowing(corsConfiguration).getAuthorizedOrigins();
      will(returnValue(Collections.singletonList("*.localhost")));
    } });

    assertThat(validator.shouldAddHeaders(requestContext, corsConfiguration), is(true));
  }

  @Test
  public void validateScopedWildcardWhenNotValid() {
    context.checking(new Expectations() { {
      allowing(requestContext).getOriginHeader();
      will(returnValue("http://test.not-localhost"));
      allowing(corsConfiguration).getAuthorizedOrigins();
      will(returnValue(Collections.singletonList("*.localhost")));
    } });

    assertThat(validator.shouldAddHeaders(requestContext, corsConfiguration), is(false));
  }

  private Expectations createExpectations(final String requestOrigin) {
    return new Expectations() { {
      allowing(requestContext).getOriginHeader();
      will(returnValue(requestOrigin));
      allowing(corsConfiguration).getAuthorizedOrigins();
      will(returnValue(Collections.singletonList(AUTHORIZED_ORIGIN)));
    } };
  }
}
