package io.erben.reactiveflow;

import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.JsonBody;
import org.mockserver.model.MediaType;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;

@Component
public class MockServer {

    record WeatherMeasurement(Integer temperature, Integer humidity, String city) {
    }

    @Bean(name = "baseUrl")
    String getBaseUrl(ClientAndServer clientAndServer) {
        return "http://localhost:" + clientAndServer.getLocalPort() + "/weather";
    }

    @Bean
    ClientAndServer setupServer() {
        ClientAndServer clientAndServer = startClientAndServer();
        clientAndServer.when(
                HttpRequest.request()
                        .withMethod("GET")
                        .withPath("/weather")
        ).respond(
                HttpResponse.response()
                        .withStatusCode(200)
                        .withBody(JsonBody.json(
                                        List.of(
                                                new WeatherMeasurement(14, 45, "Köln"),
                                                new WeatherMeasurement(22, 40, "Mainz"))
                                )
                        )
                        .withContentType(MediaType.APPLICATION_JSON)
        );
        return clientAndServer;
    }
}
