package ufc.br.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
    ReportRepository reportRepository;
	@Autowired
	PatientRepository patientRepository;
	@Autowired
	ExerciseRepository exerciseRepository;
	@Autowired
	PermitionRepository permitionRepository;

	public String save(Report report){
		Patient aux = patientRepository.findById(report.getPermition().getPatient().getId()).get();
		aux.setProgress(aux.getProgress()+1);
		//BUSCAR COUNT POR GRASP_NIVEL E STATUS -> if == 0 -> ADICIONAR PROX NIVEL
		List<Permition> permitions = permitionRepository.findByGraspLevelLevelAndLocked(aux.getLevel(), true);
		//SE CONCLUIU TODAS AS ATIVIDADES
		if(permitions.size() == 0) {
			if(aux.getLevel() < 3){ //CASO NÃO ESTEJA NO ULTIMO NIVEL
				//ADICIONAR TODAS AS ATIVIDADES DO PROX NIVEL
			}
		}
		patientRepository.save(aux);
		reportRepository.save(report);
		return "sucesso";
	}

	public String delete(int id){
		reportRepository.deleteById(id);
		return "sucesso";
	}

	public String update(Report report){
		reportRepository.save(report);
		return "sucesso";
	}

	public Report get(int id){
		return this.reportRepository.findById(id);
	}

	public List<Report> getByPermition(int id){
		Patient patient = this.patientRepository.findById(id);
		return this.reportRepository.findByPermition_Patient(patient);
	}

	public Double getByExercise(int id){
		Exercise exercise = this.exerciseRepository.findById(id);
		ArrayList<Report> report = new ArrayList<Report>();
		double num = 0;
		report = (ArrayList)this.reportRepository.findByPermition_Grasp_Exercise(exercise);
		for (Report r: report) {
			num += r.getTime();
		}
		double media = num/report.size();
		return media;
	}
	
	public List<Report> get(){
		return this.reportRepository.findAll();
	}
}
