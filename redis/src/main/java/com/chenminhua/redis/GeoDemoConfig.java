package com.chenminhua.redis;

import lombok.extern.java.Log;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.RedisTemplate;

@Log
@Configuration
public class GeoDemoConfig {

    private ApplicationRunner titleRunner (String title, ApplicationRunner rr) {
        return args -> {
            log.info(title.toUpperCase() + ":");
            rr.run(args);
        };
    }

    @Bean
    ApplicationRunner geography (RedisTemplate<String, String> rt) {
        return titleRunner("geography", args -> {
            GeoOperations<String, String> geo = rt.opsForGeo();
            geo.add("Sicily", new Point(13.361389, 38.1155556), "Arigento");
            geo.add("Sicily", new Point(15.087269, 37.502669), "Catania");
            geo.add("Sicily", new Point(13.583333, 37.316667), "Palermo");

            Circle circle = new Circle(new Point(13.583333, 37.316667),
                    new Distance(100, RedisGeoCommands.DistanceUnit.KILOMETERS));

            GeoResults<RedisGeoCommands.GeoLocation<String>> geoResults = geo.radius("Sicily", circle);
            geoResults.getContent().forEach(c -> log.info(c.toString()));

        });
    }

}
