# jetty-websockets-demo

### About

Eclipse project to build Jetty WebSocket server. Jetty servlet bridge implemented using Jetty 9 `WebSocketServlet` and `WebSocketCreator`. Includes a single page HTML/Javascript test harness.

### Install

Install Java.

Install Jetty (tested with `jetty-distribution-9.2.13.v20150730`).

In Eclipse add the following external jars to build path:
```
${jetty.home}/lib/servlet-api-3.1.jar
${jetty.home}/lib/websocket/websocket-servlet-9.2.13.v20150730.jar
${jetty.home}/lib/websocket/websocket-api-9.2.13.v20150730.jar
```
Export `root.war` from eclipse, copy to `${jetty.home}/webapps/root.war`

### Running

Start Jetty:
```
cd ${jetty.home}
java -jar start.jar
```
Load WebSockets test harness [http://localhost:8080/](http://localhost:8080/) . Connect using protocols `clock`, `reverse` or `reflect`. WebSockets servlet address is [http://localhost:8080/ws](http://localhost:8080/ws).

### Extending

Validate `ServletUpgradeRequest` in `WebSocketDispatcher` e.g. check for mandatory HTTP headers or known user with `req.getUserPrincipal().getName()`. Returning `null` will close connection before WebSocket is established.

Add `WebSocket*` using `WebSocketReflect` class to service specific protocols.



