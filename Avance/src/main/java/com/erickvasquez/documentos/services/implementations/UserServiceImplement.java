package com.erickvasquez.documentos.services.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.erickvasquez.documentos.models.dtos.users.RegisterUserDTO;
import com.erickvasquez.documentos.models.dtos.users.UpdateUserDTO;
import com.erickvasquez.documentos.models.entities.User;
import com.erickvasquez.documentos.repositories.UserRepository;
import com.erickvasquez.documentos.services.UserService;

@Service
public class UserServiceImplement implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public void save(RegisterUserDTO userInfo) throws Exception {
		User user = new User(
				userInfo.getUsername(),
				userInfo.getEmail(),
				userInfo.getPassword() // TODO: encrypt password
				);
		
		userRepository.save(user);
		
	}

	@Override
	public void update(UpdateUserDTO userInfo) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(String code) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User findOneById(String code) {
		return userRepository.findOneByUsernameOrEmail(code, code);
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}
}
