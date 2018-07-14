package ufc.br.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ufc.br.model.Exercise;
import ufc.br.model.Grasp;

import java.util.List;
import java.util.Optional;
@Repository
public interface GraspRepository extends JpaRepository<Grasp,Integer> {
	
	Grasp findById(int id);

    List<Grasp> findByExercise(Exercise exercise);

    @Query("from Grasp order by sequence ")
    List<Grasp> findAll();
}
