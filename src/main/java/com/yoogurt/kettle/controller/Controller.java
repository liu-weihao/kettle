package com.yoogurt.kettle.controller;

import com.yoogurt.kettle.config.EnvConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    private EnvConfig envConfig;

    @RequestMapping(value = "/index")
    public String index(ModelMap modelMap) {
        modelMap.addAttribute("databases", envConfig.getDatabases());
        modelMap.addAttribute("fromDbs", envConfig.getFromDbs());
        modelMap.addAttribute("toDbs", envConfig.getToDbs());
        return "index";
    }
}
