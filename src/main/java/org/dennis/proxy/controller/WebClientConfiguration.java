package org.dennis.proxy.controller;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;


import java.net.http.HttpResponse;
import java.util.concurrent.TimeUnit;

@Configuration
public class WebClientConfiguration {

    @Bean
    public WebClient customWebClient() {
        final int connectionTimeOut = 5000;
        final int responseTimeOut = 5000;
        final int sendTimeOut = 5000;
        final HttpClient httpClient = HttpClient.create().option(ChannelOption.CONNECT_TIMEOUT_MILLIS,connectionTimeOut)
                .doOnConnected(connection -> connection.addHandlerLast(new ReadTimeoutHandler(responseTimeOut, TimeUnit.MILLISECONDS)).addHandlerLast(new WriteTimeoutHandler(sendTimeOut, TimeUnit.MILLISECONDS)));
        return WebClient.builder().clientConnector(new ReactorClientHttpConnector(httpClient)).build();
    }

}
