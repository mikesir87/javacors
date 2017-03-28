package io.mikesir87.wildfly.undertow.cors;

import io.mikesir87.cors.DefaultCorsConfiguration;
import io.mikesir87.cors.CorsProcessor;
import io.mikesir87.cors.DefaultCorsProcessor;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;

import java.util.Arrays;
import java.util.List;

/**
 * An Undertow handler that delegates CORS processing to a {@link CorsProcessor}.
 *
 * @author Michael Irwin
 */
public class CorsHandler implements HttpHandler {

  private final HttpHandler next;
  private final CorsProcessor corsProcessor;
  private final DefaultCorsConfiguration corsConfiguration;

  @SuppressWarnings("unused")
  public CorsHandler(HttpHandler next) {
    this.next = next;
    this.corsConfiguration = new DefaultCorsConfiguration();
    this.corsProcessor = new DefaultCorsProcessor(corsConfiguration);
  }

  CorsHandler(HttpHandler next, CorsProcessor corsProcessor) {
    this.next = next;
    this.corsConfiguration = new DefaultCorsConfiguration();
    this.corsProcessor = corsProcessor;
  }

  @Override
  public void handleRequest(HttpServerExchange httpServerExchange) throws Exception {
    UndertowRequestContext requestContext = new UndertowRequestContext(httpServerExchange);
    corsProcessor.processRequest(requestContext, requestContext);
    next.handleRequest(httpServerExchange);
  }

  public void setOrigins(String origins) {
    corsConfiguration.setAuthorizedOrigins(Arrays.asList(origins.split("\\s*(,|\\s)\\s*")));
  }

  public void setMethodsAllowed(String methodsAllowed) {
    corsConfiguration.setAuthorizedMethods(splitString(methodsAllowed));
  }

  public void setHeadersAllowed(String headersAllowed) {
    corsConfiguration.setAuthorizedHeaders(splitString(headersAllowed));
  }

  public void setExposedHeaders(String exposedHeaders) {
    corsConfiguration.setExposedHeaders(splitString(exposedHeaders));
  }

  public void setAllowCredentials(String allowCredentials) {
    corsConfiguration.setAllowCredentials(Boolean.parseBoolean(allowCredentials));
  }

  public void setMaxAge(String maxAge) {
    corsConfiguration.setMaxAgeCacheValue(Integer.parseInt(maxAge));
  }

  private List<String> splitString(String valueToSplit) {
    return Arrays.asList(valueToSplit.split("\\s*(,|\\s)\\s*"));
  }

}
