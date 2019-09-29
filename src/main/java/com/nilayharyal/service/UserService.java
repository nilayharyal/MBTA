package com.nilayharyal.service;

import com.nilayharyal.entity.User;
import com.nilayharyal.user.CrmUser;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User searchUsingUserName(String username);

    void save(CrmUser crmUser);
}
