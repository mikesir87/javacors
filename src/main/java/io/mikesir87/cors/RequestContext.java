package io.mikesir87.cors;

/**
 * Context provider definition related to a specific request.
 *
 * @author Michael Irwin
 */
public interface RequestContext {

  /**
   * Get the value for the origin header.
   * @return The origin, if one is set. Otherwise, null.
   */
  String getOriginHeader();
}
