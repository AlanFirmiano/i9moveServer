package ufc.br.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ufc.br.model.Patient;
import ufc.br.model.Responsible;
import ufc.br.repository.PatientRepository;
import ufc.br.repository.ResponsibleRepository;
import ufc.br.repository.UserRepository;

@Service
public class PatientService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	PatientRepository patientRepository;
	@Autowired
	ResponsibleRepository responsibleRepository;

	private String generateCode(){
		Date data = new Date(); 
		return "20"+(data.getYear()-100)+(data.getMonth()+1)+data.getDate()+data.getHours()+data.getMinutes()+data.getSeconds();
	}
	
	public String save(Patient patient){
		
		if(null == patientRepository.findByName(patient.getName())){
			patient.setRegistration(generateCode());
			patient.setEmail(patient.getRegistration());
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			patient.setPassword(encoder.encode(patient.getRegistration()));
			
			Responsible res = this.responsibleRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
			System.err.println(SecurityContextHolder.getContext().getAuthentication().getName());
			patient.setResponsible(res);
			
			patientRepository.save(patient);
			return "Paciente : "+ patient.getName()+" cadastrado!";
		}else{
			return "Paciente : "+ patient.getName()+" já cadastrado!";
		}
	}

	public String delete(Integer id){
		patientRepository.deleteById(id);
		return "Paciente removido";
	}

	public String update(Patient patient){
		Patient aux = patientRepository.findByName(patient.getName());
		if(null==aux){
			//atualizo
			patientRepository.save(patient);
			return "Paciente : "+ patient.getName()+" cadastrado!";
		}else{
			if(aux.getId().equals(patient.getId())){
				//atualizo
				patientRepository.save(patient);
				return "Paciente : "+ patient.getName()+" cadastrado!";
			
			}else{
				//nao atualizo
				return "Paciente : "+ patient.getName()+" já cadastrado!";
			}
		}
	}

	public Patient get(int id){
		return this.patientRepository.findById(id);
	}

	public List<Patient> get(){
		return this.patientRepository.findAll();
	}
}