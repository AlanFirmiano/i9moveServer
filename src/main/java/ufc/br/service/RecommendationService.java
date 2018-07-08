package ufc.br.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ufc.br.model.Recommendation;
import ufc.br.repository.RecommendationRepository;

import java.util.List;

@Service
public class RecommendationService {

    @Autowired
    RecommendationRepository recommendationRepository;

    public String save(Recommendation recommendation){
        recommendationRepository.save(recommendation);
        return "sucesso";
    }

    public String delete(Integer id){
        recommendationRepository.deleteById(id);
        return "sucesso";
    }

    public String update(Recommendation recommendation){
        recommendationRepository.save(recommendation);
        return "sucesso";
    }

    public Recommendation get(int id){
        return this.recommendationRepository.findById(id);
    }

    public List<Recommendation> get(){
        return this.recommendationRepository.findAll();
    }
}