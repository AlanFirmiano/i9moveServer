package ufc.br.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ufc.br.model.Usuario;
import ufc.br.repository.UsuarioRepository;

@Service
public class UsuarioService {
	@Autowired
	UsuarioRepository repository;
	
	 public ResponseEntity<String> save(Usuario user){
	        repository.save(user);
	        return new ResponseEntity<String>("Usuario adicionado!", HttpStatus.OK);

	    }

	    public ResponseEntity<String> delete(int id){
	        repository.deleteById(id);
	        return new ResponseEntity<String>("removido", HttpStatus.OK);
	    }

	    public ResponseEntity<String> update(Usuario user){
	        repository.save(user);
	        return new ResponseEntity<String>("autualizado", HttpStatus.OK);
	    }

	    public ResponseEntity<Usuario> get(int id){
	        return new ResponseEntity<Usuario>(this.repository.findById(id), HttpStatus.OK);
	    }
	    
	    public Usuario getByEmail(String email){
	        return this.repository.findByEmail(email);
	    }
	    public ResponseEntity<List<Usuario>> get(){
	        return new ResponseEntity<List<Usuario>>(this.repository.findAll(), HttpStatus.OK);
	    }
}
