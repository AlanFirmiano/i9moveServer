package ufc.br.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ufc.br.model.Exercise;
import ufc.br.model.Grasp;
import ufc.br.model.Level;
import ufc.br.repository.*;

import java.util.Date;
import java.util.List;

@Service
public class GraspService {

    @Autowired
    GraspRepository graspRepository;
    @Autowired
    ExerciseRepository exerciseRepository;
    @Autowired
    ExerciseService exerciseService;
    @Autowired
    LevelRepository levelRepository;
    @Autowired
    RecommendationRepository recommendationRepository;
    @Autowired
    SerieRepository serieRepository;

    public String save(Grasp grasp){
        if(null == exerciseRepository.findByTitle(grasp.getExercise().getTitle())) {
            exerciseService.save(grasp.getExercise());
        }
        Exercise x = exerciseRepository.findByTitle(grasp.getExercise().getTitle());
        grasp.setExercise(x);
        Level aux = levelRepository.findByLevel(grasp.getLevel().getLevel());
        if(aux!=null){
            grasp.setLevel(aux);
        }else {
            grasp.setLevel(levelRepository.save(grasp.getLevel()));
        }
        grasp.getRecommendation().setSerie(serieRepository.saveAll(grasp.getRecommendation().getSerie()));
        grasp.setRecommendation(recommendationRepository.save(grasp.getRecommendation()));

        grasp.setSequence((int) new Date().getTime());

        graspRepository.save(grasp);
        return "sucesso";
    }

    public String delete(Integer id){
        graspRepository.deleteById(id);
        return "sucesso";
    }

    public String update(Grasp grasp){
        graspRepository.save(grasp);
        return "sucesso";
    }

    public Grasp get(int id){
        return this.graspRepository.findById(id);
    }

    public List<Grasp> get(){
        return this.graspRepository.findAll();
    }

    public List<Grasp> getByLevel(int level){
        return this.graspRepository.findByLevel_LevelOrderBySequence(level);
    }

    public List<Grasp> getByExercise(Exercise exercise){
        return this.graspRepository.findByExercise(exercise);
    }
}
