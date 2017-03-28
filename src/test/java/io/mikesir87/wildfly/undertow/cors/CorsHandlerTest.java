package io.mikesir87.wildfly.undertow.cors;

import io.mikesir87.cors.CorsProcessor;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import org.hamcrest.CustomTypeSafeMatcher;
import org.hamcrest.Matcher;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Unit test for the {@link CorsHandler} class.
 *
 * @author Michael Irwin
 */
public class CorsHandlerTest {

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  @Mock
  HttpHandler next;

  // Can't mock because it's a final class
  HttpServerExchange httpServerExchange = new HttpServerExchange(null);

  @Mock
  CorsProcessor corsProcessor;

  private CorsHandler handler;

  @Before
  public void setUp() {
    handler = new CorsHandler(next, corsProcessor);
  }

  @Test
  public void validateRequestHandling() throws Exception {
    context.checking(new Expectations() { {
      oneOf(corsProcessor).processRequest(with(requestContextMatcher()), with(requestContextMatcher()));
      oneOf(next).handleRequest(httpServerExchange);
    } });
    handler.handleRequest(httpServerExchange);
  }

  private Matcher<UndertowRequestContext> requestContextMatcher() {
    return new CustomTypeSafeMatcher<UndertowRequestContext>("requestContextMatcher") {
      @Override
      protected boolean matchesSafely(UndertowRequestContext undertowRequestContext) {
        return undertowRequestContext.getServerExchange() == httpServerExchange;
      }
    };
  }
}
