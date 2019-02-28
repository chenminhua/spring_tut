package com.chenminhua.redis;

import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

@Log
@EnableRedisHttpSession
@SpringBootApplication
public class DemoApp {

    public static void main(String[] args) {
        SpringApplication.run(DemoApp.class);
    }
}

class ShoppingCart implements Serializable {
    private final Collection<Order> orders = new ArrayList<>();

    public void addOrder(Order order) {
        this.orders.add(order);
    }

    public Collection<Order> getOrders() {
        return this.orders;
    }
}


@Log
@Controller
@SessionAttributes("cart")
class CartSessionController {

    private final AtomicLong ids = new AtomicLong();

    @ModelAttribute("cart")
    ShoppingCart cart () {
        log.info("creating new cart");
        return new ShoppingCart();
    }

    @GetMapping("/orders")
    String orders (@ModelAttribute ("cart") ShoppingCart cart, Model model) {
        cart.addOrder(new Order(ids.incrementAndGet(), new Date(), Collections.emptyList()));
        model.addAttribute("orders", cart.getOrders());
        return "orders";

    }

}