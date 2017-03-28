# JavaCors-Runtime

This module provides the API and a default implementation to process CORS requests.  The API is vendor-agnostic, allowing it to be integrated into any framework.

## Core Concepts

- The entrypoint is a `CorsProcessor`, which is responsible for determining if a provided request should get CORS headers. It should perform all validations and then add headers as needed. It uses a `RequestContext` and `ResponseHandler`, outlined below.
- The `RequestContext` encapsulates the details needed from a request in order to determine if CORS headers should be applied.
- The `ResponseHandler` simply provides a mechanism to add response headers in a vendor-agnostic fashion.
- The `CorsConfiguration` interface defines the "rules" that a request must follow in order to be validated (what methods/headers/origins are allowed, are credentials allowed, etc.). The rules are most likely set at construction time for a processor, as most environments aren't going to be changing rules on the fly (maybe??).

## Using the Runtime

To help you get started, you can use the `DefaultCorsProcessor`, which hooks in all the validation and addition of response headers.  You can use the `DefaultCorsConfiguration` object as config, which provides the defaults outlined below, but allows you to easily change them.

```java
CorsConfiguration config = new DefaultCorsConfiguration();
CorsProcessor processor = new DefaultCorsProcessor(config);
```

Then, when you want to process a request, simply use the processor!

```java
processor.processRequest(requestContext, responseHandler);
```

If you're using the runtime directly, you may need to provide your own implementations of the `RequestContext` and/or `ResponseHandler`.  Otherwise, feel free to look at the other submodules.

