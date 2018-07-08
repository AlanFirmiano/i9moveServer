package ufc.br.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ufc.br.model.Midia;
import ufc.br.repository.MidiaRepository;

import java.util.List;

@Service
public class MidiaService {

    @Autowired
    MidiaRepository midiaRepository;

    public String save(Midia midia){
        midiaRepository.save(midia);
        return "sucesso";
    }

    public String delete(Integer id){
        midiaRepository.deleteById(id);
        return "sucesso";
    }

    public String update(Midia midia){
        midiaRepository.save(midia);
        return "sucesso";
    }

    public Midia get(int id){
        return this.midiaRepository.findById(id);
    }

    public List<Midia> get(){
        return this.midiaRepository.findAll();
    }
}