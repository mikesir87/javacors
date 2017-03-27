package io.mikesir87.wildfly.undertow.cors;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HeaderMap;
import io.undertow.util.HttpString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Simple handler for Undertow that adds the CORS headers if the origin exactly matches a list of configured origins.
 *
 * @author Michael Irwin
 */
public class CorsHandler implements HttpHandler {

  private static final String DEFAULT_METHODS_ALLOWED = "GET POST PUT DELETE OPTIONS";
  private static final String DEFAULT_HEADERS_ALLOWED = "accept, authorization, content-type, cookie";
  private static final String DEFAULT_ALLOW_CREDENTIALS = "false";
  private static final String DEFAULT_MAX_AGE = "600";   // 10 minutes

  private final HttpHandler next;

  private List<String> hosts = new ArrayList<>();
  private String methodsAllowed = DEFAULT_METHODS_ALLOWED;
  private String headersAllowed = DEFAULT_HEADERS_ALLOWED;
  private String allowCredentials = DEFAULT_ALLOW_CREDENTIALS;
  private String maxAge = DEFAULT_MAX_AGE;

  @SuppressWarnings("unused")
  public CorsHandler(HttpHandler next) {
    this.next = next;
  }

  public void handleRequest(HttpServerExchange httpServerExchange) throws Exception {
    HeaderMap requestHeaders = httpServerExchange.getRequestHeaders();
    if (requestHeaders.contains("Origin")) {
      String origin = requestHeaders.get("Origin").getFirst();
      for (String host : hosts) {
        if (origin.equals(host)) {
          addCorsHeaders(httpServerExchange, origin);
        }
      }
    }
    next.handleRequest(httpServerExchange);
  }

  public void setHosts(String hosts) {
    this.hosts = Arrays.asList(hosts.split("\\s*(,|\\s)\\s*"));
  }

  public void setMethodsAllowed(String methodsAllowed) {
    this.methodsAllowed = methodsAllowed;
  }

  public void setHeadersAllowed(String headersAllowed) {
    this.headersAllowed = headersAllowed;
  }

  public void setAllowCredentials(String allowCredentials) {
    this.allowCredentials = allowCredentials;
  }

  public void setMaxAge(String maxAge) {
    this.maxAge = maxAge;
  }

  private void addCorsHeaders(HttpServerExchange httpServerExchange, String origin) {
    HeaderMap responseHeaders = httpServerExchange.getResponseHeaders();

    responseHeaders.add(new HttpString("Access-Control-Allow-Origin"), origin);
    responseHeaders.add(new HttpString("Access-Control-Allow-Methods"), methodsAllowed);
    responseHeaders.add(new HttpString("Access-Control-Allow-Headers"), headersAllowed);
    responseHeaders.add(new HttpString("Access-Control-Allow-Credentials"), allowCredentials);
    responseHeaders.add(new HttpString("Access-Control-Max-Age"), maxAge);
  }

}
