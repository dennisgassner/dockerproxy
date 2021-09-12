package org.dennis.proxy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.lang.reflect.InvocationTargetException;

@RestController
public class DockerProxyController {

    private final WebClient webClient;
    private final Handlers handlers;

    @Autowired
    public DockerProxyController(WebClient webClient, Handlers handlers) {
        this.webClient = webClient;
        this.handlers = handlers;
    }

    @PostMapping("/proxy/call")
    public String proxyCall(@RequestBody CallArguments arguments) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String response = webClient.method(arguments.getMethod())
                .uri(arguments.getUrl())
                .headers(httpHeaders -> arguments.getHeaders().forEach((k, v) -> httpHeaders.add(k,v)))
                .bodyValue(arguments.getBody())
                .retrieve()
                .bodyToMono(String.class)
                .block();
        if (arguments.getHandler() != null) {
            return handlers.getHandler(arguments.getHandler()).apply(response);
        }
        return response;
    }

}
