package com.chenminhua.reservationclient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@SpringBootApplication
public class ReservationClientApplication {

    @Bean
    WebClient webClient() {
        return WebClient.builder()
                .filter(ExchangeFilterFunctions
                        .basicAuthentication("user", "password"))
                .build();
    }

    @Bean
    RouterFunction<?> routes(WebClient webClient) {

        return route(GET("/names"), new HandlerFunction<ServerResponse>() {
            @Override
            public Mono<ServerResponse> handle(ServerRequest serverRequest) {
                Flux<String> namesPublisher = webClient
                    .get()
                    .uri("http://localhost:8080/reservations")
                    .retrieve()
                    .bodyToFlux(Reservation.class)
                    .map(Reservation::getReservationName);
                return ServerResponse.ok().body(namesPublisher, String.class);
            }
        });
    }

    public static void main(String[] args) {
        SpringApplication.run(ReservationClientApplication.class);
    }
}


@AllArgsConstructor
@NoArgsConstructor
@Data
class Reservation {
    private String id;

    private String reservationName;
}
