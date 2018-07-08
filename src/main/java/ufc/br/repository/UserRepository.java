package ufc.br.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ufc.br.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	User findById(int id);
	
	User findByEmail(String email);
	
}
