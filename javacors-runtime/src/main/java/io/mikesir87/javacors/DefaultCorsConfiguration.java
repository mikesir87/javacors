package io.mikesir87.javacors;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A basic {@link CorsConfiguration} that has basic defaults set, but allows overriding using setters.
 *
 * @author Michael Irwin
 */
public class DefaultCorsConfiguration implements CorsConfiguration {

  private static final List<String> EMPTY_LIST = Collections.emptyList();
  private static final List<String> DEFAULT_AUTHORIZED_METHODS =
    Arrays.asList("GET", "POST", "HEAD", "PUT", "DELETE", "OPTIONS", "PATCH");
  private static final List<String> DEFAULT_AUTHORIZED_HEADERS =
    Arrays.asList("Origin", "Accept", "X-Requested-With", "Content-Type", "Access-Control-Request-Method",
      "Access-Control-Request-Headers");
  private static final boolean DEFAULT_ALLOW_CREDENTIALS = false;
  private static final Integer DEFAULT_MAX_AGE = 600;

  private List<String> authorizedOrigins = EMPTY_LIST;
  private List<String> authorizedMethods = DEFAULT_AUTHORIZED_METHODS;
  private List<String> authorizedHeaders = DEFAULT_AUTHORIZED_HEADERS;
  private List<String> exposedHeaders = EMPTY_LIST;
  private boolean allowCredentials = DEFAULT_ALLOW_CREDENTIALS;
  private Integer maxAgeCacheValue = DEFAULT_MAX_AGE;

  @Override
  public List<String> getAuthorizedOrigins() {
    return authorizedOrigins;
  }

  public void setAuthorizedOrigins(List<String> authorizedOrigins) {
    this.authorizedOrigins = authorizedOrigins;
  }

  @Override
  public List<String> getAuthorizedMethods() {
    return authorizedMethods;
  }

  public void setAuthorizedMethods(List<String> authorizedMethods) {
    this.authorizedMethods = authorizedMethods;
  }

  @Override
  public List<String> getAuthorizedHeaders() {
    return authorizedHeaders;
  }

  public void setAuthorizedHeaders(List<String> authorizedHeaders) {
    this.authorizedHeaders = authorizedHeaders;
  }

  @Override
  public List<String> getExposedHeaders() {
    return exposedHeaders;
  }

  public void setExposedHeaders(List<String> exposedHeaders) {
    this.exposedHeaders = exposedHeaders;
  }

  @Override
  public boolean allowCredentials() {
    return allowCredentials;
  }

  public void setAllowCredentials(boolean allowCredentials) {
    this.allowCredentials = allowCredentials;
  }

  @Override
  public Integer getMaxAgeCacheValue() {
    return maxAgeCacheValue;
  }

  public void setMaxAgeCacheValue(Integer maxAgeCacheValue) {
    this.maxAgeCacheValue = maxAgeCacheValue;
  }

}
