package ufc.br.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ufc.br.model.Serie;
import ufc.br.repository.SerieRepository;

import java.util.List;

@Service
public class SerieService {

    @Autowired
    SerieRepository serieRepository;

    public String save(Serie serie){
        serieRepository.save(serie);
        return "sucesso";
    }

    public String delete(int id){
        serieRepository.deleteById(id);
        return "sucesso";
    }

    public String update(Serie serie){
        serieRepository.save(serie);
        return "sucesso";
    }

    public Serie get(int id){
        return this.serieRepository.findById(id);
    }
    public List<Serie> get(){
        return this.serieRepository.findAll();
    }
}
