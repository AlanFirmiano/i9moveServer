package ufc.br.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ufc.br.model.Level;
import ufc.br.repository.LevelRepository;

import java.util.List;

@Service
public class LevelService {

    @Autowired
    LevelRepository levelRepository;

    public String save(Level level){
        levelRepository.save(level);
        return "sucesso";
    }

    public String delete(Integer id){
        levelRepository.deleteById(id);
        return "sucesso";
    }

    public String update(Level level){
        levelRepository.save(level);
        return "sucesso";
    }

    public Level get(int id){
        return this.levelRepository.findById(id);
    }

    public List<Level> get(){
        return this.levelRepository.findAll();
    }
}