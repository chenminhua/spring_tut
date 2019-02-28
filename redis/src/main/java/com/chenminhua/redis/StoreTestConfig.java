package com.chenminhua.redis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.*;


@Repository
interface OrderRepository extends CrudRepository<Order, Long> {
    Collection<Order> findByWhen(Date d);
}

@Repository
interface LineItemRepository extends CrudRepository<LineItem, Long> { }

@Log
//@Configuration
public class StoreTestConfig {

    @Autowired private LineItemRepository lineItemRepository;

    @Autowired private OrderRepository orderRepository;

    private ApplicationRunner titleRunner (String title, ApplicationRunner rr) {
        return args -> {
            log.info(title.toUpperCase() + ":");
            rr.run(args);
        };
    }

    private Long generateId() {
        Long tmp = new Random().nextLong();
        return Math.abs(tmp);
    }

    @Bean
    ApplicationRunner repositories() {
        return titleRunner("repositories", args -> {
            Long orderId = generateId();
            List<LineItem> itemList = Arrays.asList(
                    new LineItem(orderId, generateId(), "plunger"),
                    new LineItem(orderId, generateId(), "soup"),
                    new LineItem(orderId, generateId(), "coffee mug"));
            itemList.stream()
                    .map(lineItemRepository::save)
                    .forEach(li -> log.info(li.toString()));

            Order order = new Order(orderId, new Date(), itemList);
            orderRepository.save(order);

            Collection<Order> found = orderRepository.findByWhen(order.getWhen());
            found.forEach(o -> log.info("found" + o.toString()));

        });
    }
}


@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("orders")
class Order implements Serializable {
    @Id
    private Long id;

    @Indexed
    private Date when;

    @Reference
    private List<LineItem> lineItems;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("lineItems")
class LineItem implements Serializable {
    @Indexed
    private Long orderId;

    @Id
    private Long id;

    private String description;
}

