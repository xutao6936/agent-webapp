package com.agent.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/received")
@Slf4j
public class ReceivedController {

    @RequestMapping(method = RequestMethod.GET)
    public String forword(){
        return "received/list";
    }
}
