package ufc.br.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;
import ufc.br.model.Exercise;
import ufc.br.model.Grasp;
import ufc.br.model.Midia;
import ufc.br.repository.ExerciseRepository;
import ufc.br.repository.GraspRepository;
import ufc.br.repository.ObjectRepository;

@Service
public class ExerciseService {

	@Autowired
	ExerciseRepository exerciseRepository;
	@Autowired
	GraspService graspService;
	@Autowired
	GraspRepository graspRepository;
	@Autowired
	ObjectRepository objectRepository;
    @Autowired
    MidiaService midiaService;

	public String save(Exercise exercise){
		if(null== exerciseRepository.findByTitle(exercise.getTitle())){
			if(!exercise.getTitle().equals("")) {
				objectRepository.saveAll(exercise.getObjects());
				exerciseRepository.save(exercise);
				return "Exercicio : " + exercise.getTitle() + " cadastrado!";
			}else{
				return "Dados invalidos!";
			}
		}else{
			return "Exercicio : "+ exercise.getTitle()+" já cadastrado!";
		}
	}

	public String upload(int id, MultipartFile file){
        try {
            File convertFile = new File(this.getClass().getClassLoader().getResource("public/video/").getPath()
                    + file.getOriginalFilename());
            convertFile.createNewFile();
            FileOutputStream fout = new FileOutputStream(convertFile);
            fout.write(file.getBytes());
            fout.close();

            Exercise aux = this.get(id);
            Midia midia = new Midia();
            midia.setPathVideo("http://localhost:8080/video/" + file.getOriginalFilename());
            midia.setPathTitle(convertFile.getName());
            if (aux.getMidia() != null) {
                aux.getMidia().setPathVideo(midia.getPathVideo());
                aux.getMidia().setPathTitle(midia.getPathTitle());
                midiaService.update(aux.getMidia());
                System.err.println("Midia atualizada!");
            } else {
                aux.setMidia(midia);
                System.err.println("Midia " + aux.getMidia().getPathTitle() + " criada!");
            }
            this.update(aux);
            return "Video enviado com sucesso";
        } catch (IOException e) {
            return "Erro";
        }
    }

	public String delete(int id){

		List<Grasp> lista = graspService.getByExercise(exerciseRepository.findById(id));
		for(Grasp aux: lista) {
			graspRepository.deleteById(aux.getId());
		}
		exerciseRepository.deleteById(id);
		return "Exercicio removido!";
	}

	public String update(Exercise exercise){
		exerciseRepository.save(exercise);
		return "Exercicio : "+ exercise.getTitle()+" atualizado!";
	}

	public Exercise get(int id){
		return this.exerciseRepository.findById(id);
	}

	public Exercise get(String title){
		return this.exerciseRepository.findByTitle(title);
	}

	public List<Exercise> get(){
		return this.exerciseRepository.findAll();
	}
}