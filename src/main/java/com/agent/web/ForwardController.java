package com.agent.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Slf4j
public class ForwardController {

    @RequestMapping(value = "/home",method = RequestMethod.GET)
    public String toHome(){
        return "layout/default";
    }
}
