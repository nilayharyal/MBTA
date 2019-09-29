package com.nilayharyal.dao;

import com.nilayharyal.entity.User;

public interface UserDao {

    User findByUserName(String userName);
    
    void save(User user);
    
    User getUser(long userId);
    
}
