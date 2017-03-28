# Wildfly CORS Support

This module packages up the Undertow support as a Wildfly module. It has been tested in Wildfly 10, but _should_ work in other versions as well.

## Installation

Complete the following steps to install the Wildfly support.

1. Download the modules ZIP file (link coming shortly)
2. Place the ZIP file at the root of the Wildfly directory
3. Unzip the directory (`unzip javacors-wildfly-modules.zip`). You should see now see the following files:
   - `modules/io/mikesir87/javacors/undertow/main/module.xml`
   - `modules/io/mikesir87/javacors/undertow/javacors-undertow.jar`
   - `modules/io/mikesir87/javacors/undertow/javacors-runtime.jar`
4. Configure the Undertow subsystem
  - Direct XML configuration (i.e., `standalone.xml`)
    ```xml
    <subsystem xmlns="urn:jboss:domain:undertow:3.1">
      ...
      <server name="default-server">
        ...
        <host name="default-host" alias="localhost">
          ...
          <filter-ref name="cors-filter" />
        </host>
      </server>
      <filters>
        ...
        <filter name="cors-filter" class-name="io.mikesir87.javacors.undertow.CorsHandler" module="io.mikesir87.javacors.undertow">
          <param name="origins" value="http://api.example.com" />
          <!-- Other parameters here -->
        </filter>
      </filters>
    </subsystem>
    ```
  - CLI configuration
    ```bash
    /subsystem=undertow/configuration=filter/custom-filter=cors-filter:add(class-name=io.mikesir87.javacors.undertow.CorsHandler, module=io.mikesir87.javacors.undertow)
    /subsystem=undertow/server=default-server/host=default-host/filter-ref=cors-filter:add()
    ```


## Configuration

Configuring the handler can be done either through direct XML manipulation or via the JBoss cli.  Below are the various parameter names and accepted values.

| Parameter Name   | Type           | Default Value                                                                                                     | Description                                                                                                 |
|------------------|----------------|-------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------|
| origins          | CSV String     | `""`                                                                                                              | The origins allowed to make requests. Wildcard support not YET provided                                     |
| methodsAllowed   | CSV String     | `"GET, POST, HEAD, PUT, DELETE, OPTIONS"`                                                                         | HTTP methods allowed for CORS requests. Used to validate access to client's `Access-Control-Request-Method` |
| headersAllowed   | CSV String     | `"Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers"` | HTTP headers that are allowed when validating the `Access-Control-Request-Headers` header                   |
| exposedHeaders   | CSV String     | `""`                                                                                                              | List of headers that will be placed in the `Access-Control-Expose-Headers` response header                  |
| allowCredentials | Boolean String | `"false"`                                                                                                         | Should credentials be submitted by clients when making CORS requests?                                       |
| maxAge           | Integer String | `"600"`                                                                                                           |                                                                                                             |
