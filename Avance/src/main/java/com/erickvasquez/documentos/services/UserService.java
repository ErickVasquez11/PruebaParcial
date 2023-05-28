package com.erickvasquez.documentos.services;

import java.util.List;

import com.erickvasquez.documentos.models.dtos.users.RegisterUserDTO;
import com.erickvasquez.documentos.models.dtos.users.UpdateUserDTO;
import com.erickvasquez.documentos.models.entities.User;

public interface UserService {
	
	  void save(RegisterUserDTO userInfo) throws Exception;
	  void update(UpdateUserDTO userInfo) throws Exception;
	  void deleteById(String code) throws Exception;
	  User findOneById(String code);
	  List<User> findAll();
}
