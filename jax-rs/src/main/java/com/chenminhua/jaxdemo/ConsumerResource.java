package com.chenminhua.jaxdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.Collection;
import java.util.Collections;

@Path("/consumers")
@Produces(MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ConsumerResource {

    private ConsumerRepository repository;

    ConsumerResource(ConsumerRepository repository) {
        this.repository = repository;
    }

    @GET
    public Collection<Consumer> consumers() {
        return this.repository.findAll();
    }


    @GET
    @Path("/{id}")
    public Consumer byId(@PathParam("id") Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Could't find #" + id + "!"));
    }
}
