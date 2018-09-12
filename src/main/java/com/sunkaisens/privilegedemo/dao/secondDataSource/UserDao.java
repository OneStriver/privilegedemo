package com.sunkaisens.privilegedemo.dao.secondDataSource;

import com.sunkaisens.privilegedemo.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author HeYin
 * @date 2018/9/11
 */
@Mapper
public interface UserDao {

    User findUserByName(@Param(value = "userName") String userName);
    int insertUserInfo(User user);
}
