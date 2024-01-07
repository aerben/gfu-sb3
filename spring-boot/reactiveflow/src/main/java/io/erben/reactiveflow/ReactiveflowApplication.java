package io.erben.reactiveflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Flow;

@SpringBootApplication
public class ReactiveflowApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(ReactiveflowApplication.class, args);
		String baseUrl = ctx.getBean("baseUrl", String.class);
        try (HttpClient httpClient = HttpClient.newHttpClient()) {
			CompletableFuture<HttpResponse<String>> httpResponseCompletableFuture = httpClient.sendAsync(HttpRequest.newBuilder(URI.create(baseUrl)).build(), HttpResponse.BodyHandlers.ofString());
		}
    }

}
