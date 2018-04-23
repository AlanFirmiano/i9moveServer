package ufc.br.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ufc.br.model.Papel;

public interface PapelRepository extends JpaRepository<Papel,Integer>{
	Papel findById(int id); 
}
