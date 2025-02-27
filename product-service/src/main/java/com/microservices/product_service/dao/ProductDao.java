package com.microservices.product_service.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.microservices.product_service.entity.Product;

import jakarta.transaction.Transactional;

@Repository
public class ProductDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	public List<Product> getAllProducts() {
		
		String hql = "from Product";
		
		return sessionFactory.getCurrentSession()
				.createQuery(hql, Product.class)
				.list();
	}
	
	@Transactional
	public Product getProductById(int id) {
		
		String hql = "from Product p where p.id = :id";
		
		return sessionFactory.getCurrentSession().createQuery(hql, Product.class)
				.setParameter("id", id)
				.uniqueResult();
	}
	
	@Transactional
	public void createProduct(Product product) {
		sessionFactory.getCurrentSession().persist(product);
	}
	
	@Transactional
	public void updateProduct(Product updatedProduct, int id) {
		
		Product existingProduct = this.getProductById(id);
		
		existingProduct.setName(updatedProduct.getName());
		existingProduct.setMemory(updatedProduct.getMemory());
		
		sessionFactory.getCurrentSession().merge(existingProduct);
	}
	
	@Transactional
	public void deleteProductById(int id) {
		
		String hql = "delete from Product p where p.id = :id";
		
		sessionFactory.getCurrentSession()
					 .createQuery(hql)
					 .setParameter("id", id)
					 .executeUpdate();
					
	}

}
