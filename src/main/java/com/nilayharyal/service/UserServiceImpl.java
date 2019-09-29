package com.nilayharyal.service;

import com.nilayharyal.dao.RoleDao;
import com.nilayharyal.dao.UserDao;
import com.nilayharyal.entity.Role;
import com.nilayharyal.entity.User;
import com.nilayharyal.user.CrmUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

	// need to inject user dao
	@Autowired
	private UserDao userDao;

	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public User searchUsingUserName(String userName) {
		return userDao.findByUserName(userName);
	}

	@Override
	@Transactional
	public void save(CrmUser cUser) {
		User user = new User();
		user.setusername(cUser.getUserName());
		user.setPassword(passwordEncoder.encode(cUser.getPassword()));
		user.setFirstName(cUser.getFirstName());
		user.setLastName(cUser.getLastName());
		user.setEmail(cUser.getEmail());
		user.setRoles(Arrays.asList(roleDao.findRoleByName("ROLE_PASSENGER")));
		userDao.save(user);
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userDao.findByUserName(userName);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getusername(), user.getPassword(),
				mapRolesToAuthorities(user.getRoles()));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}
}
