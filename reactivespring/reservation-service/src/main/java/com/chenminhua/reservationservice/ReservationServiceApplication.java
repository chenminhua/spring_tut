package com.chenminhua.reservationservice;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@SpringBootApplication
public class ReservationServiceApplication {

    @Bean
    ReactiveUserDetailsService authentication() {
        return new MapReactiveUserDetailsService(User.withDefaultPasswordEncoder()
                .username("user").password("password").roles("USER").build());
    }

    // curl -v localhost:8080/reservations    failed
    // curl -vu user:password localhost:8080/reservations    success

    @Bean
    SecurityWebFilterChain config (ServerHttpSecurity security) {
        return security
                .csrf().disable()
                .httpBasic()
                .and()
                .authorizeExchange()
                .pathMatchers("/reservations")
                .authenticated()
                .anyExchange().permitAll().and().build();
    }

    @Bean
    RouterFunction<?> routes(ReservationRepository reservationRepository) {
        return route(GET("/reservations"), req -> ServerResponse.ok().body(reservationRepository.findAll(), Reservation.class));
//		return RouterFunctions.route(RequestPredicates.GET("/reservations"), new HandlerFunction<ServerResponse>() {
//			@Override
//			public Mono<ServerResponse> handle(ServerRequest serverRequest) {
//				return ServerResponse.ok().body(reservationRepository.findAll(), Reservation.class);
//			}
//		});
    }

    public static void main(String[] args) {
        SpringApplication.run(ReservationServiceApplication.class, args);
    }

}

@Component
class Initializer implements ApplicationRunner {

    @Autowired ReservationRepository reservationRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.reservationRepository.deleteAll()
                .thenMany(Flux.just("aaa", "bbb", "ccc", "ddd", "eee", "fff")
                        .map(name -> new Reservation(null, name))
                        .flatMap(this.reservationRepository::save))
                .thenMany(this.reservationRepository.findAll())
                .subscribe(System.out::println);


    }
}

@Component
interface ReservationRepository extends ReactiveMongoRepository<Reservation, String> {}
//
//@RestController
//class ReservationController {
//
//	@Autowired ReservationRepository reservationRepository;
//
//	// it's netty !!! reactive web !!!
//	@GetMapping("/reservations")
//	Publisher<Reservation> reservationPublisher() {
//		return this.reservationRepository.findAll();
//	}
//}

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document
class Reservation {
    @Id
    private String id;

    private String reservationName;
}
