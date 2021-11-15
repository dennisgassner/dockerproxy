# dockerproxy

This Spring Boot Rest application receives http requests and forwards it to an other server.
There's one endpoint POST `/proxy/call`  which is called with all necessery definitions about how to forward and how to deal with the response.

## Call Arguments
The request body consists of the object "CallArguments", containing an optional http message body to forward, the targeting url and corresponding http method, a list of http headers used while forwarding and an optional handler to deal with the response. If this handler is missing the response is just getting back as it is.

### Example
Send following http message body via post to the endpoint `/proxy/call`
```
{
  body:"username=dummyuser&password=dummypassword&grant_type=password&client_id=dummyclientid",
  url:"http://deliverykeycloak:8080/auth/realms/deliveryBackend/protocol/openid-connect/token",
  method:"POST",
  headers:{"Content-Type":"application/x-www-form-urlencoded"},
  handler:"oauthtoken"
}
```        

### Handler
By default the application comes with a handler to handle token-request responses of an oauth-authentification server, so that only the generated oauth-token is responded by this application.
Handlers can be added to a HashMap of functional interfaces `org.dennis.proxy.controller.Handlers`. The handler must be a function expecting and responding a String Object.


#
To create a docker image usable in the [Pizza Scenario](https://github.com/dennisgassner/pizza-delivery) just build it via the simple way described by [Spring Boot](https://spring.io/guides/topicals/spring-boot-docker/), so docker build `--build-arg JAR_FILE=target/*.jar -t orgdennis/dockerproxy .`
