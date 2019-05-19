package com.person.shop.repository;

import com.person.shop.bean.User;
import org.apache.ibatis.annotations.Select;

/**
 * UserMapper 数据访问类
 * @author xlei @qq 251425887 @tel 13352818008
 * @Email dlei0009@163.com
 * @date 2019-05-09 11:03:02
 * @version 1.0
 */
public interface UserMapper {


    @Select("select * from ec_user where LOGIN_NAME = #{name}")
    User login(String name);
}