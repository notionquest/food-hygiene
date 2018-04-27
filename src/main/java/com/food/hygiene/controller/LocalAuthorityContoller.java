package com.food.hygiene.controller;

import com.food.hygiene.model.Authority;
import com.food.hygiene.service.LocalAuthorityService;
import com.food.hygiene.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LocalAuthorityContoller {

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("localAuthorityService")
    private LocalAuthorityService localAuthorityService;

    @GetMapping(Constants.LOCAL_AUTHORITY_URL)
    public ModelAndView getLocalAuthorities(Model model) {
        LOGGER.info("Get local authorities");
        ModelAndView mv = new ModelAndView("localauthorities");
        mv.addObject("authorities", localAuthorityService.getLocalAuthorities());
        mv.addObject("authority", new Authority());
        return mv;
    }
}
