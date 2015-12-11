package com.github.liuxboy.mini.web.demo.controller;
import com.github.liuxboy.mini.web.demo.service.CalcService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wyliuchundong
 * @version 1.0.0
 * @date 2015/3/21 16:44
 * @comment CalcCtrl
 */
@Controller
public class CalcCtrl {
    @Resource
    private CalcService calcService;
    @RequestMapping(value = "/calc", method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("/work/vihicle/list");
    }

    //@RequestMapping(value = "/calcVehicle", method = RequestMethod.POST)
    public ModelAndView calcVehicle(HttpServletRequest request, HttpServletResponse response) {
        boolean result = calcService.calcVehicleTravelTimeAndDelay();
        if (result)
            return new ModelAndView("/work/vihicle/success");
        else
            return new ModelAndView("/work/vihicle/fail");
    }

    @RequestMapping(value = "/calcSegment", method = RequestMethod.POST)
    public ModelAndView calcSegment(HttpServletRequest request, HttpServletResponse response) {
        boolean result = calcService.calcSegmentTravelTimeAndDelay();

        if (result)
            return new ModelAndView("/work/vihicle/success");
        else
            return new ModelAndView("/work/vihicle/fail");
    }
    //@RequestMapping(value = "/updateVehicle", method = RequestMethod.POST)
    public ModelAndView updateVehicle(HttpServletRequest request, HttpServletResponse response) {
        boolean result = calcService.updateVehicle();

        if (result)
            return new ModelAndView("/work/vihicle/success");
        else
            return new ModelAndView("/work/vihicle/fail");
    }

}