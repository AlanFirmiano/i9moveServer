package ufc.br.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ufc.br.model.Patient;
import ufc.br.model.Permition;
import ufc.br.model.User;
import ufc.br.repository.PatientRepository;
import ufc.br.repository.PermitionRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class PermitionService {

    @Autowired
    PermitionRepository permitionRepository;
    @Autowired
    PatientRepository patientRepository;
    
    public String save(Permition permition){
    	if(permitionRepository.findByPatientAndGrasp(permition.getPatient(), permition.getGrasp())==null) {
    		if(permition.getPatient().getLevel()<permition.getGrasp().getLevel().getLevel()) {
    			Patient patient = patientRepository.findById(permition.getPatient().getId()).get();
    			patient.setLevel(permition.getGrasp().getLevel().getLevel());
    			patientRepository.save(patient);
    			System.err.println("atualizou level");
    		}
    		permitionRepository.save(permition);
        	return permition.getGrasp().getExercise().getTitle()+" permitido!";
    	}else {
    		return permition.getGrasp().getExercise().getTitle()+" já está permitido";
    	}
    }

    public String delete(Integer id){
        permitionRepository.deleteById(id);
        return "Removido";
    }

    public String update(Permition permition){
        permitionRepository.save(permition);
        return permition.getGrasp().getExercise().getTitle()+" foi atualizado!";
    }
    
    public Permition get(int id){
        return this.permitionRepository.findById(id);
    }

    public List<Permition> get(Patient patient){
        return this.permitionRepository.findByPatient(patient);
    }

    public List<Permition> getUnlockedX(boolean locked) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            Patient patient = new PatientService().get(new UserService().getByEmail(currentUserName).getId());
            System.err.println("Usuario : "+patient.getName());
            return this.permitionRepository.findByLockedAndPatient(locked, patient);
        }
        return new ArrayList<>();
    }
    public List<Permition> getUnlocked(boolean locked, Patient patient){
        return this.permitionRepository.findByLockedAndPatient(locked, patient);

    }
    
    public List<Permition> get(){
        return this.permitionRepository.findAll();
    }
}