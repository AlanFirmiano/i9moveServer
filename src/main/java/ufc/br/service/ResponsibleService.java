package ufc.br.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ufc.br.model.Responsible;
import ufc.br.repository.ResponsibleRepository;

@Service
public class ResponsibleService {

	@Autowired
	ResponsibleRepository responsibleRepository;

	public String save(Responsible responsible){
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		responsible.setPassword(encoder.encode(responsible.getPassword()));
		responsibleRepository.save(responsible);
		return "Fisioterapeuta cadastrado com sucesso!";
	}

	public String delete(int id){
		responsibleRepository.deleteById(id);
		return "Fisioterapeuta removido!";
	}

	public String update(Responsible responsible){
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		responsible.setPassword(encoder.encode(responsible.getPassword()));
		responsibleRepository.save(responsible);
		return "Fisioterapeuta atualizado!";
	}

	public Responsible get(int id){
		return this.responsibleRepository.findById(id);
	}

	public List<Responsible> get(){
		return this.responsibleRepository.findAll();
	}
}