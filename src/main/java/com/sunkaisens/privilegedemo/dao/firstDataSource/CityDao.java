package com.sunkaisens.privilegedemo.dao.firstDataSource;

import com.sunkaisens.privilegedemo.domain.City;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author HeYin
 * @date 2018/9/11
 */
@Mapper
public interface CityDao {

    City findCityByName(@Param("cityName") String cityName);
    int insertCityInfo(City city);
}
