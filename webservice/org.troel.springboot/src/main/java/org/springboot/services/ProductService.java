package org.springboot.services;

import java.util.List;
import java.util.Optional;

import org.springboot.entities.Product;
import org.springboot.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	public List<Product> findAll() {
		return repository.findAll();
	}
	
	public Product findById(Long userId) {
		Optional<Product> obj = repository.findById(userId);
		return obj.get();
	}
}
