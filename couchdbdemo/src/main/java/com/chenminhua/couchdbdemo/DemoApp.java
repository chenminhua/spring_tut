package com.chenminhua.couchdbdemo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import rx.Completable;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@SpringBootApplication
public class DemoApp {

    public static void main(String[] args) {
        SpringApplication.run(DemoApp.class);
    }
}


@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
class Movie {

    @Id
    private String id;
    private String title;
}

@Repository
interface MovieRepository extends CouchbaseRepository<Movie, String> {
    CompletableFuture<Movie> findByTitle(String title);
}

@Component
class SampleMovieCLR implements CommandLineRunner {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public void run(String... args) throws Exception {
        this.movieRepository.deleteAll();
        Stream.of("Gone with the wind", "the Wizard of Oz", "One Flew over the Cuckoo's Nest")
                .forEach(title -> this.movieRepository.save(new Movie(UUID.randomUUID().toString(), title)));
        this.movieRepository.findByTitle("the Wizard of Oz").thenAccept(System.out::println);

    }
}