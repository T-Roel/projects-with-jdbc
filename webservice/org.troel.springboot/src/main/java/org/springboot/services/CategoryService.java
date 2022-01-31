package org.springboot.services;

import java.util.List;
import java.util.Optional;

import org.springboot.entities.Category;
import org.springboot.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	
	public List<Category> findAll() {
		return repository.findAll();
	}
	
	public Category findById(Long userId) {
		Optional<Category> obj = repository.findById(userId);
		return obj.get();
	}
}
