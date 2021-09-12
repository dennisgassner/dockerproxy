package org.dennis.proxy.controller;

import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class Handlers {

    private Map<String, Function<String, String>> handlers;

    public Handlers() {
        handlers = new HashMap<String, Function<String, String>>();
        handlers.put("oauthtoken", response -> {
            OAuthToken oauthToken = new GsonBuilder().create().fromJson(response, OAuthToken.class);
            return oauthToken.getAccess_token();
        });
    }

    public Function<String, String> getHandler(String handlerName) {
        return handlers.get(handlerName);
    }
}
