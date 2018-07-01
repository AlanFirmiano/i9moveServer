package ufc.br.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ufc.br.model.Exercise;
import ufc.br.model.Patient;
import ufc.br.model.Permition;
import ufc.br.model.Report;
import ufc.br.repository.ExerciseRepository;
import ufc.br.repository.ReportRepository;
import ufc.br.repository.PatientRepository;
import ufc.br.repository.PermitionRepository;

@Service
public class ReportService {
	@Autowired
    ReportRepository repository;
	@Autowired
	PatientRepository prep;
	@Autowired
	ExerciseRepository exeRepo;
	@Autowired
	PermitionRepository permitionRepository;
	public ResponseEntity<String> save(Report report){
		Patient aux = prep.findById(report.getPermition().getPatient().getId()).get();
		aux.setProgress(aux.getProgress()+1);
		//BUSCAR COUNT POR GRASP_NIVEL E STATUS -> if == 0 -> ADICIONAR PROX NIVEL
		List<Permition> permitions = permitionRepository.findByGraspLevelLevelAndLocked(aux.getLevel(), true);
		//SE CONCLUIU TODAS AS ATIVIDADES
		if(permitions.size() == 0) {
			
		}
		prep.save(aux);
		repository.save(report);
		return new ResponseEntity<String>("sucesso", HttpStatus.OK);		
	}

	public ResponseEntity<String> delete(int id){
		repository.deleteById(id);
		return new ResponseEntity<String>("sucesso", HttpStatus.OK);
	}

	public ResponseEntity<String> update(Report report){
		repository.save(report);
		return new ResponseEntity<String>("sucesso", HttpStatus.OK);
	}

	public ResponseEntity<Report> get(int id){
		return new ResponseEntity<Report>(this.repository.findById(id), HttpStatus.OK);
	}

	public ResponseEntity<List<Report>> getByPermition(int id){
		Patient patient = this.prep.findById(id);
		return new ResponseEntity<List<Report>>(this.repository.findByPermition_Patient(patient), HttpStatus.OK);
	}

	public ResponseEntity<Double> getByExercise(int id){
		Exercise exercise = this.exeRepo.findById(id);
		ArrayList<Report> report = new ArrayList<Report>();
		double num = 0;
		report = (ArrayList)this.repository.findByPermition_Grasp_Exercise(exercise);
		for (Report r: report) {
			num += r.getTime();
		}
		double media=num/report.size();
		return new ResponseEntity<Double>(media, HttpStatus.OK);
	}
	
	public ResponseEntity<List<Report>> get(){
		return new ResponseEntity<List<Report>>(this.repository.findAll(), HttpStatus.OK);
	}
}
