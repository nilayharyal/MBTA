package com.nilayharyal.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nilayharyal.entity.Role;

@Repository
public class RoleDaoImpl implements RoleDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Role findRoleByName(String role) {

		Session currentSession = sessionFactory.getCurrentSession();
		Query<Role> query = currentSession.createQuery("from Role where name=:roleName", Role.class);
		query.setParameter("roleName", role);
		Role r = null;
		try {
			r = query.getSingleResult();
		} catch (Exception e) {
			r = null;
		}
		
		return r;
	}
}
