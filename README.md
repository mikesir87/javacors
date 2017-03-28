# JavaCors

This project provides CORS support and integrates it into various Java frameworks.

## Why this project?

Few reasons...

- In some cases, the desire to use a servlet filter-based approach may not be available. For example, consider an endpoint that requires authentication and is using servlet/container-based authentication. App-provided filters don't have the ability to add CORS headers if the user isn't authenticated (which is always the case for pre-flight checks).
- While the spec isn't _too_ complicated, it's more than just simply adding headers, as most examples show. And honestly, most of the examples are terribly insecure (do you really want to allow all origins or allow credentials by default?).


## Modules

This project contains a few submodules that allow you to pick and choose what you need.

- **`javacors-runtime`** - provides the API and runtime for CORS processing. The default implementation validates a request and then adds all the necessary/configured headers.
- **`javacors-undertow`** - provides a `HttpHandler` that can be plugged into Undertow to add CORS support
- **`javacors-wildfly`** - provides a module installation (simply wrapping the `javacors-undertow` module) that can be dropped in and configured to run in Wildfly


## Contributing

All are welcome to contribute! If you notice a bug, an issue, or want to add another framework integration, feel free to open an issue or submit a PR!
