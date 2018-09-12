package com.sunkaisens.privilegedemo;

import com.sunkaisens.privilegedemo.service.CityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PrivilegedemoApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private CityService cityService;

    @Test
    public void testInsertData() throws Exception {
        cityService.findCityByName("111");
    }

}
