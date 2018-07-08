package ufc.br.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ufc.br.model.User;
import ufc.br.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	public String save(User user){
		userRepository.save(user);
		return "User adicionado!";
	}

	public String delete(int id){
		userRepository.deleteById(id);
		return "removido";
	}

	public String update(User user){
		userRepository.save(user);
		return "autualizado";
	}

	public User get(int id){
		return this.userRepository.findById(id);
	}

	public User getByEmail(String email){
		return this.userRepository.findByEmail(email);
	}

	public List<User> get(){
		return this.userRepository.findAll();
	}
}