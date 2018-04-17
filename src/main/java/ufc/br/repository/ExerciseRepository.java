package ufc.br.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import ufc.br.model.Exercise;
@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Integer>{
	
	Exercise findById(int id);

	Exercise findByTitle(String title);
}
