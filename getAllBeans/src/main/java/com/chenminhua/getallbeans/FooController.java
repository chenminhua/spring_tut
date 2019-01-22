package com.chenminhua.getallbeans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class FooController {
    @Autowired
    private FooService fooService;

    @RequestMapping(value="/displayallbeans")
    public String getHeaderAndBody(Map model){
        model.put("header", fooService.getHeader());
        model.put("message", fooService.getBody());
        return "displayallbeans";
    }
}
