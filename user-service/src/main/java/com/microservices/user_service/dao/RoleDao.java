package com.microservices.user_service.dao;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.microservices.user_service.entity.Role;

@Repository
public class RoleDao {
	
	private final SessionFactory sessionFactory;
	
	public RoleDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public Role findByName(String roleName) {
		
		String hql = "from Role r where r.name = :roleName";
		
		return sessionFactory.getCurrentSession().createQuery(hql, Role.class)
				.setParameter("roleName", roleName)
				.uniqueResult();
	}

}
