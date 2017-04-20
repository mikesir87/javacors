package io.mikesir87.javacors.validators;



import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.jmock.Expectations;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.mikesir87.javacors.RequestContext;

/**
 * Unit test for the {@link DelegatingCorsRequestContext} class.
 *
 * @author Jeff Mitchell
 */
public class DelegatingCorsRequestContextTest {

  private static final String HEADER = "POST";
  private static final List<String> HEADERS_LIST = Arrays.asList(HEADER);

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  @Mock
  RequestContext requestContext;

  private CorsRequestContext delegatingContext;

  @Before
  public void setUp() {
    delegatingContext = new DelegatingCorsRequestContext(requestContext);
  }

  @Test
  public void testGetRequestedHeadersAsList() {
    context.checking(new Expectations() {
      {
        allowing(requestContext).getRequestedHeaders();
        will(returnValue(HEADER));
      }
    });
    assertThat(delegatingContext.getRequestedHeadersAsList(), is(HEADERS_LIST));
  }

  @Test
  public void testGetRequestedHeadersAsListWithEmptyAccessControlRequestHeader() {
    context.checking(new Expectations() {
      {
        allowing(requestContext).getRequestedHeaders();
        will(returnValue(""));
      }
    });
    assertThat(delegatingContext.getRequestedHeadersAsList(), is(Collections.emptyList()));
  }
}
