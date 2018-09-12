package com.sunkaisens.privilegedemo.controller;

import com.sunkaisens.privilegedemo.domain.City;
import com.sunkaisens.privilegedemo.service.CityService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author HeYin
 * @date 2018/9/11
 */
@RestController
public class CityRestController {

    @Resource
    private CityService cityService;

    @RequestMapping(value = "/api/city", method = RequestMethod.GET)
    public City findOneCity(@RequestParam(value = "cityName", required = true) String cityName) {
        return cityService.findCityByName(cityName);
    }

}
