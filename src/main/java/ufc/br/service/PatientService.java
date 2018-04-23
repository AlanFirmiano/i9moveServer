package ufc.br.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ufc.br.model.Papel;
import ufc.br.model.Patient;
import ufc.br.model.Responsible;
import ufc.br.repository.PatientRepository;
import ufc.br.repository.ResponsibleRepository;
import ufc.br.repository.UsuarioRepository;

@Service
public class PatientService {
	@Autowired
	UsuarioRepository usuario;
	@Autowired
	PatientRepository repository;
	@Autowired
	ResponsibleRepository responsible;

	private String gerarMatricula(){
		Date data = new Date(); 
		return "20"+(data.getYear()-100)+(data.getMonth()+1)+data.getDate()+data.getHours()+data.getMinutes()+data.getSeconds();
	}
	
	public ResponseEntity<String> save(Patient patient){
		
		if(null == repository.findByName(patient.getName())){
			patient.setRegistration(gerarMatricula());
			patient.setEmail(patient.getRegistration());
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			patient.setPassword(encoder.encode(patient.getRegistration()));
			
			Responsible res = this.responsible.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
			System.err.println(SecurityContextHolder.getContext().getAuthentication().getName());
			patient.setResponsible(res);
			//patient.addPapel(new Papel("PAC"));
			repository.save(patient);
			return new ResponseEntity<String>("Paciente : "+ patient.getName()+" cadastrado!", HttpStatus.OK);
		}else{
			return new ResponseEntity<String>("Paciente : "+ patient.getName()+" já cadastrado!", HttpStatus.IM_USED);
		}
	}

	public ResponseEntity<String> delete(Integer id){
		repository.deleteById(id);
		return new ResponseEntity<String>("Paciente removido", HttpStatus.OK);
	}

	public ResponseEntity<String> update(Patient patient){
		Patient aux = repository.findByName(patient.getName());
		if(null==aux){
			//atualizo
			repository.save(patient);
			return new ResponseEntity<String>("Paciente : "+ patient.getName()+" cadastrado!", HttpStatus.OK);
		}else{
			if(aux.getId().equals(patient.getId())){
				//atualizo
				repository.save(patient);
				return new ResponseEntity<String>("Paciente : "+ patient.getName()+" cadastrado!", HttpStatus.OK);
			
			}else{
				//nao atualizo
				return new ResponseEntity<String>("Paciente : "+ patient.getName()+" já cadastrado!", HttpStatus.IM_USED);
			}
		}
	}

	public ResponseEntity<Patient> get(int id){
		return new ResponseEntity<Patient>(this.repository.findById(id), HttpStatus.OK);
	}

	public ResponseEntity<List<Patient>> get(){
		return new ResponseEntity<List<Patient>>(this.repository.findAll(), HttpStatus.OK);
	}
}
