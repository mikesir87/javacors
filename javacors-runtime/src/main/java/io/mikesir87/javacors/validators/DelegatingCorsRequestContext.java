package io.mikesir87.javacors.validators;

import io.mikesir87.javacors.RequestContext;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A {@link CorsRequestContext} that delegates what it can to a provided {@link RequestContext}.
 *
 * @author Michael Irwin
 */
public class DelegatingCorsRequestContext implements CorsRequestContext {

  private final RequestContext requestContext;

  public DelegatingCorsRequestContext(RequestContext requestContext) {
    this.requestContext = requestContext;
  }

  @Override
  public String getOriginHeader() {
    return requestContext.getOriginHeader();
  }

  @Override
  public String getRequestMethod() {
    return requestContext.getRequestMethod();
  }

  @Override
  public boolean isPreFlightRequest() {
    return getRequestMethod().toUpperCase().equals("OPTIONS");
  }

  @Override
  public String getRequestedMethod() {
    return requestContext.getRequestedMethod();
  }

  @Override
  public List<String> getRequestedHeadersAsList() {
    if (getRequestedHeaders() == null || getRequestedHeaders().equals(""))
      return Collections.emptyList();

    return Stream.of(getRequestedHeaders().split(","))
      .map(String::trim)
      .collect(Collectors.toList());
  }

  @Override
  public String getRequestedHeaders() {
    return requestContext.getRequestedHeaders();
  }

  public RequestContext getRequestContext() {
    return requestContext;
  }
}
