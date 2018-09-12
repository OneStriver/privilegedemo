package com.sunkaisens.privilegedemo.service.impl;

import com.sunkaisens.privilegedemo.dao.firstDataSource.CityDao;
import com.sunkaisens.privilegedemo.dao.secondDataSource.UserDao;
import com.sunkaisens.privilegedemo.domain.City;
import com.sunkaisens.privilegedemo.domain.User;
import com.sunkaisens.privilegedemo.service.CityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author HeYin
 * @date 2018/9/11
 */
@Service
public class CityServiceImpl implements CityService {

    @Resource
    private CityDao cityDao;

    @Resource
    private UserDao userDao;

    @Override
    @Transactional(value = "transactionManager",rollbackFor = Exception.class)
    public City findCityByName(String cityName) {
        City city = new City();
        city.setCityName("成都");
        city.setId(2L);
        city.setProvinceId(2L);
        city.setDescription("省会");
        cityDao.insertCityInfo(city);
//        if (true) {
//          throw new RuntimeException("insert failure");
//        }
        User user = new User();
        user.setId(1L);
        user.setUserName("重庆");
        user.setDescription("直辖市");
        userDao.insertUserInfo(user);
//        if (true) {
//            throw new RuntimeException("insert failure");
//        }
        return null;
    }

}
