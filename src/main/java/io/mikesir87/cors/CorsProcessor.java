package io.mikesir87.cors;

/**
 * A single processor that is capable of processing requests for CORS.
 *
 * @author Michael Irwin
 */
public interface CorsProcessor {

  /**
   * Process the provided request.
   * @param requestContext Context providing the request to process
   * @param responseHandler A handler to modify the outgoing response
   * @return True if CORS headers were added. Otherwise, false.
   */
  boolean processRequest(RequestContext requestContext, ResponseHandler responseHandler);
}
