package ufc.br.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ufc.br.model.Patient;
import ufc.br.model.Permition;
import ufc.br.repository.GraspRepository;
import ufc.br.repository.PatientRepository;
import ufc.br.repository.PermitionRepository;

import java.util.List;

@Service
public class PermitionService {
    @Autowired
    PermitionRepository repository;
    
    public ResponseEntity<String> save(Permition permition){
    	if(repository.findByPatientAndGrasp(permition.getPatient(), permition.getGrasp())==null) {
    		repository.save(permition);
        	return new ResponseEntity<String>(permition.getGrasp().getExercise().getTitle()+" permitido!", HttpStatus.OK);
    	}else {
    		return new ResponseEntity<String>(permition.getGrasp().getExercise().getTitle()+" já está permitido", HttpStatus.OK);
    	}
    }

    public ResponseEntity<String> delete(Integer id){
        repository.deleteById(id);
        return new ResponseEntity<String>("Removido", HttpStatus.OK);
    }

    public ResponseEntity<String> update(Permition permition){
        repository.save(permition);
        return new ResponseEntity<String>(permition.getGrasp().getExercise().getTitle()+" foi atualizado!", HttpStatus.OK);
    }
    
    public ResponseEntity<Permition> get(int id){
        return new ResponseEntity<Permition>(this.repository.findById(id), HttpStatus.OK);
    }

    public ResponseEntity<List<Permition>> get(Patient patient){
        return new ResponseEntity<List<Permition>>(this.repository.findByPatient(patient), HttpStatus.OK);
    }
    public ResponseEntity<List<Permition>> getUnlocked(boolean locked, Patient patient){
        return new ResponseEntity<List<Permition>>(this.repository.findByLockedAndPatient(locked, patient), HttpStatus.OK);
    }
    
    public ResponseEntity<List<Permition>> get(){
        return new ResponseEntity<List<Permition>>(this.repository.findAll(), HttpStatus.OK);
    }
}
