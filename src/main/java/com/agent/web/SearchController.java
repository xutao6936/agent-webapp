package com.agent.web;

import com.agent.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @RequestMapping("/agent.do")
    public ModelAndView find(@RequestParam(value = "session.sce.tel", required = false, defaultValue = "") String tel){
        ModelAndView mv = new ModelAndView("getAgentid");
        mv.addObject("status", searchService.find(tel));

        return mv;
    }

}
