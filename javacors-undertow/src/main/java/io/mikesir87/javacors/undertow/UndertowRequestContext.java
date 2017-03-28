package io.mikesir87.javacors.undertow;

import io.mikesir87.javacors.RequestContext;
import io.mikesir87.javacors.ResponseHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HeaderMap;
import io.undertow.util.HttpString;

/**
 * Created by mikesir87 on 3/28/17.
 */
public class UndertowRequestContext implements RequestContext, ResponseHandler {

  private final HttpServerExchange serverExchange;

  public UndertowRequestContext(HttpServerExchange serverExchange) {
    this.serverExchange = serverExchange;
  }

  @Override
  public String getOriginHeader() {
    return getHeaderValue("Origin");
  }

  @Override
  public String getRequestMethod() {
    return serverExchange.getRequestMethod().toString();
  }

  @Override
  public String getRequestedMethod() {
    return getHeaderValue("Access-Control-Request-Method");
  }

  @Override
  public String getRequestedHeaders() {
    return getHeaderValue("Access-Control-Request-Headers");
  }

  @Override
  public void addHeader(String name, String value) {
    serverExchange.getResponseHeaders().add(new HttpString(name), value);
  }

  private String getHeaderValue(String headerName) {
    HeaderMap headerMap = serverExchange.getRequestHeaders();
    return (headerMap.contains(headerName)) ? headerMap.getFirst(headerName) : null;
  }

  public HttpServerExchange getServerExchange() {
    return serverExchange;
  }
}
