package org.dennis.proxy;

import com.google.gson.Gson;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.dennis.proxy.controller.CallArguments;
import org.dennis.proxy.controller.DockerProxyController;
import org.dennis.proxy.controller.Handlers;
import org.dennis.proxy.controller.WebClientConfiguration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("dev")
class ProxyApplicationTests {

	@Autowired
	MockMvc mvc;

	static MockWebServer server;

	@BeforeAll
	static void setUpServer() throws IOException {
		server = new MockWebServer();
		server.enqueue(new MockResponse().setBody("{\"access_token\":\"ABC\"}"));
		server.start();
	}

	@AfterAll
	static void shutDownServer() throws IOException {
		server.shutdown();
	}

	@Test
	void testProxyCallWeb() throws Exception {
		HttpUrl mockUrl = server.url("auth/realms/deliveryBackend/protocol/openid-connect/token");
		CallArguments callArguments = new CallArguments();
		callArguments.setBody("username=pizza-client&password=pepperonipizza&grant_type=password&client_id=delivery-backend");
		callArguments.setHandler("oauthtoken");
		callArguments.setHeaders(new HashMap<String, String>() {{put("Content-Type","application/x-www-form-urlencoded");}});
		callArguments.setMethod(HttpMethod.POST);
		callArguments.setUrl(mockUrl.url().toString());
		MvcResult result = mvc.perform(post("/proxy/call").content(new Gson().toJson(callArguments))).andReturn();
		assertThat(result.getResponse().getContentAsString().contains("access_token"));
		assertThat(result.getResponse().getStatus()==200);
	}

	@Test
	void testProxyCall() throws Exception {
		HttpUrl mockUrl = server.url("auth/realms/deliveryBackend/protocol/openid-connect/token");
		CallArguments callArguments = new CallArguments();
		callArguments.setBody("username=pizza-client&password=pepperonipizza&grant_type=password&client_id=delivery-backend");
		callArguments.setHandler("oauthtoken");
		callArguments.setHeaders(new HashMap<String, String>() {{put("Content-Type","application/x-www-form-urlencoded");}});
		callArguments.setMethod(HttpMethod.POST);
		callArguments.setUrl(mockUrl.url().toString());
		assertThat(new DockerProxyController(new WebClientConfiguration().customWebClient(), new Handlers()).proxyCall(callArguments).length()>0);
	}

	@Test
	void testHandlerOAuthToken() {
		String response="{\"access_token\":\"ABC\",\"expires_in\":300,\"refresh_expires_in\":1800,\"refresh_token\":\"ABC\",\"token_type\":\"Bearer\",\"not-before-policy\":0,\"session_state\":\"ABC\",\"scope\":\"profile email\"}\n";
		Handlers handlers = new Handlers();
		String token = handlers.getHandler("oauthtoken").apply(response);
		assertThat(token!=null && token.length()>1);
	}







}
