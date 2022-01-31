package org.springboot.services;

import java.util.List;
import java.util.Optional;

import org.springboot.entities.Order;
import org.springboot.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository repository;
	
	public List<Order> findAll() {
		return repository.findAll();
	}
	
	public Order findById(Long userId) {
		Optional<Order> obj = repository.findById(userId);
		return obj.get();
	}
}
