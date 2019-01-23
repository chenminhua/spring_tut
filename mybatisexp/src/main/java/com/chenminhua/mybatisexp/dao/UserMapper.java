package com.chenminhua.mybatisexp.dao;

import com.chenminhua.mybatisexp.UserDO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

    public List<UserDO> listUser();

    public void insert(UserDO user);
}
