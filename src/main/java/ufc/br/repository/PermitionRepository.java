package ufc.br.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ufc.br.model.Grasp;
import ufc.br.model.Patient;
import ufc.br.model.Permition;

import java.util.List;

@Repository
public interface PermitionRepository extends JpaRepository<Permition, Integer>{
	
    List<Permition> findByPatient(Patient patient);
    
    Permition findByPatientAndGrasp(Patient patient, Grasp grasp);
    
    List<Permition> findByLockedAndPatient(boolean locked,Patient patient);
    
    Permition findById(int id);
    
    List<Permition> findByGraspLevelLevelAndLocked(int level, boolean locked);
}
