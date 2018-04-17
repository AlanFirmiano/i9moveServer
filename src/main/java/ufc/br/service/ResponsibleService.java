package ufc.br.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ufc.br.model.Responsible;
import ufc.br.repository.ResponsibleRepository;

@Service
public class ResponsibleService {
	@Autowired
	ResponsibleRepository repository;

	public ResponseEntity<String> save(Responsible responsible){
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		responsible.setPassword(encoder.encode(responsible.getPassword()));
		repository.save(responsible);
		return new ResponseEntity<String>("sucesso", HttpStatus.OK);		
	}

	public ResponseEntity<String> delete(int id){
		repository.deleteById(id);
		return new ResponseEntity<String>("sucesso", HttpStatus.OK);
	}

	public ResponseEntity<String> update(Responsible responsible){
		repository.save(responsible);
		return new ResponseEntity<String>("sucesso", HttpStatus.OK);
	}

	public ResponseEntity<Responsible> get(int id){
		return new ResponseEntity<Responsible>(this.repository.findById(id), HttpStatus.OK);
	}

	public ResponseEntity<List<Responsible>> get(){
		return new ResponseEntity<List<Responsible>>(this.repository.findAll(), HttpStatus.OK);
	}
}
