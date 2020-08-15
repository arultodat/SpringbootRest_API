package com.user.controller;

import java.util.List;

import javax.management.relation.RelationTypeNotFoundException;
import javax.websocket.server.PathParam;

import org.hibernate.ResourceClosedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.model.User;
import com.user.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/user")
	public List<User> getAllList()
	{
		return userRepository.findAll();
	}
	
	@PostMapping("/user")
	public User createUser(@Validated @RequestBody User user)
	{
		return userRepository.save(user);
	}
	
	@GetMapping("/user/{id}")
	public User getUserId(@PathParam(value = "id")Long id)throws RelationTypeNotFoundException
	{
		return userRepository.findById(id).orElseThrow(()-> new RelationTypeNotFoundException());
	}
	
	@PutMapping("/user/{id}")
	public User udateUser(@PathVariable(value = "id")Long id,  @RequestBody User usershow) throws RelationTypeNotFoundException
	{
		User user = userRepository.findById(id).orElseThrow(()-> new RelationTypeNotFoundException("User"));
		user.setFirstname(usershow.getFirstname());
		user.setLastname(usershow.getLastname());
		
		User updateUser = userRepository.save(user);
		return updateUser;
	}
	
	@DeleteMapping("/user/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable(value = "id")Long id)
	{
		User user = userRepository.findById(id).orElseThrow(()-> new ResourceClosedException("User"));
		userRepository.delete(user);			
		return ResponseEntity.ok().build();
	}
}
