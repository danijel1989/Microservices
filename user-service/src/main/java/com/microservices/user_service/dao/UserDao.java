package com.microservices.user_service.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.microservices.user_service.dto.UserDto;
import com.microservices.user_service.entity.Role;
import com.microservices.user_service.entity.User;

import jakarta.transaction.Transactional;

@Repository
public class UserDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	public List<User> getAllUsers() {
		
		String hql = "From User";
		
		List<User> list = sessionFactory.getCurrentSession()
				.createQuery(hql, User.class)
				.list();
		
		return list;
	}
	
	@Transactional
	public User getUserById(int id) {
		
		String hql = "from User u where u.id = :id";
		
		return sessionFactory.getCurrentSession()
				.createQuery(hql, User.class)
				.setParameter("id", id)
				.uniqueResult();
	}
	
	@Transactional
	public void createUser(User user) {
		sessionFactory.getCurrentSession().persist(user);
	}
	
	@Transactional
	public void updateUser(User updateUser, int id) {
		
		User existingUser = this.getUserById(id);
		existingUser.setFirstName(updateUser.getFirstName());
		existingUser.setLastName(updateUser.getLastName());
		existingUser.setAge(updateUser.getAge());
		existingUser.setProductId(updateUser.getProductId());
		
		sessionFactory.getCurrentSession().merge(existingUser);	
	}
	
	@Transactional
	public void deleteUserById(int id) {
		
		String hql = "Delete from User u where u.id = :id";
		
		sessionFactory.getCurrentSession()
			.createQuery(hql)
			.setParameter("id", id)
			.executeUpdate();
	}
	
	@Transactional
	public UserDto getUserByUsername(String username) {
		String hql = "from User u where u.username = :username";
		User user = sessionFactory.getCurrentSession().createQuery(hql, User.class)
				.setParameter("username", username)
				.uniqueResult();
		
		List<String> roles = user.getRoles().stream().map(Role::getRoleName).collect(Collectors.toList());
		
		UserDto userDto = new UserDto();
		userDto.setUsername(user.getUsername());
		userDto.setPassword(user.getPassword());
		userDto.setRoles(roles);
		
		return userDto;
	}

}
