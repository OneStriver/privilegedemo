package com.sunkaisens.privilegedemo.service;

import com.sunkaisens.privilegedemo.domain.City;
import com.sunkaisens.privilegedemo.domain.User;

/**
 * @author HeYin
 * @date 2018/9/11
 */
public interface CityService {

   City findCityByName(String cityName);
}
