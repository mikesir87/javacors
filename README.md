# Wildfly CORS Support

This project _currently_ provides only Wildfly (Undertow) support for CORS.  However, the underlying CORS processor is vendor agnostic, so other integrations can be built fairly easily.

## Installing

Complete the following steps to install the Wildfly support.

1. Download the modules ZIP file (link coming shortly)
2. Place the ZIP file at the root of the Wildfly directory
3. Unzip the directory (`unzip wildfly-cors-modules.zip`). You should see now see the following files:
   - `modules/io/mikesir87/undertow/cors/main/module.xml`
   - `modules/io/mikesir87/undertow/cors/main/wildfly-cors.xml`
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
        <filter name="cors-filter" class-name="io.mikesir87.undertow.cors.CorsHandler" module="io.mikesir87.undertow.cors">
          <param name="origins" value="http://api.example.com" />
          <!-- Other parameters here -->
        </filter>
      </filters>
    </subsystem>
    ```
  - CLI configuration
    ```bash
    /subsystem=undertow/configuration=filter/custom-filter=cors-filter:add(class-name=io.mikesir87.wildfly.undertow.cors.CorsHandler, module=io.mikesir87.undertow.cors)
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
