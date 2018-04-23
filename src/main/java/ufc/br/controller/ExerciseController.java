package ufc.br.controller;

import static ufc.br.util.ConfigurationConstants.ADMINISTRADOR;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ufc.br.model.Exercise;
import ufc.br.model.Midia;
import ufc.br.service.ExerciseService;
import ufc.br.service.MidiaService;


@RestController
@RequestMapping("/exercises")
@CrossOrigin(origins = "*")
public class ExerciseController {

	@Autowired
	private ExerciseService service;
	@Autowired
	private MidiaService mService;
	@PostMapping
	public ResponseEntity<String> save(@RequestBody Exercise exercise){
		return service.save(exercise);
	}
	
	@PostMapping(value="/{id}/upload", headers = "content-type=multipart/form-data")
	public ResponseEntity<Object> uploadFile(@PathVariable Integer id, @RequestParam("file") MultipartFile file) throws IOException{
		
		File convertFile = new File(this.getClass().getClassLoader().getResource("public/video/").getPath()+file.getOriginalFilename());
		convertFile.createNewFile();
		FileOutputStream fout = new FileOutputStream(convertFile);
		fout.write(file.getBytes());
		fout.close();
		
		Exercise aux = service.get(id).getBody();
		Midia midia = new Midia();
		midia.setPathVideo("http://localhost:8080/video/"+file.getOriginalFilename());
		midia.setPathTitle(convertFile.getName());
		if(aux.getMidia()!=null) {
			aux.getMidia().setPathVideo(midia.getPathVideo());
			aux.getMidia().setPathTitle(midia.getPathTitle());
			mService.update(aux.getMidia());
			System.err.println("Midia atualizada!");
		}else {
			aux.setMidia(midia);
			System.err.println("Midia "+ aux.getMidia().getPathTitle()+" criada!");
		}
		service.update(aux);
		return new ResponseEntity<>("Video enviado com sucesso", HttpStatus.OK);
	}

	@PostMapping(value="/upload", headers = "content-type=multipart/form-data")
	public ResponseEntity<Object> uploadFile(@RequestHeader(value="Content-Type") String userAgent,@RequestParam("file") MultipartFile file) throws IOException{
		System.err.println(userAgent);
		File convertFile = new File(this.getClass().getClassLoader().getResource("public/video/").getPath()+file.getOriginalFilename());
		convertFile.createNewFile();
		FileOutputStream fout = new FileOutputStream(convertFile);
		fout.write(file.getBytes());
		fout.close();
		return new ResponseEntity<>("Video enviado com sucesso", HttpStatus.OK);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Integer id){
		return service.delete(id);
	}

	@PutMapping
	public ResponseEntity<String> update(@RequestBody Exercise exercise){
		return service.update(exercise);
	}

	@GetMapping("by/{id}")
	public ResponseEntity<Exercise> get(@PathVariable Integer id){
		return service.get(id);
	}

	@GetMapping("/{title}")
	public ResponseEntity<Exercise> get(@PathVariable String title){
		return service.get(title);
	}

	@GetMapping
	public ResponseEntity<List<Exercise>> get(){
		return service.get();
	}
}
