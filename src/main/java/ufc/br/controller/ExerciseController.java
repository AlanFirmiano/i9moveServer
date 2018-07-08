package ufc.br.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ufc.br.model.Exercise;
import ufc.br.service.ExerciseService;

@RestController
@RequestMapping("/exercises")
@CrossOrigin(origins = "*")
public class ExerciseController {

	@Autowired
	private ExerciseService exerciseService;

	@PostMapping
	public ResponseEntity<String> save(@RequestBody Exercise exercise) {
		return ResponseEntity.ok(exerciseService.save(exercise));
	}

	@PostMapping(value = "/{id}/upload", headers = "content-type=multipart/form-data")
	public ResponseEntity<Object> uploadFile(@PathVariable Integer id, @RequestParam("file") MultipartFile file)
			throws IOException {
		return ResponseEntity.ok(exerciseService.upload(id, file));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Integer id) {
		return ResponseEntity.ok(exerciseService.delete(id));
	}

	@PutMapping
	public ResponseEntity<String> update(@RequestBody Exercise exercise) {
		return ResponseEntity.ok(exerciseService.update(exercise));
	}

	@GetMapping("by/{id}")
	public ResponseEntity<Exercise> get(@PathVariable Integer id) {
		return ResponseEntity.ok(exerciseService.get(id));
	}

	@GetMapping("/{title}")
	public ResponseEntity<Exercise> get(@PathVariable String title) {
		return ResponseEntity.ok(exerciseService.get(title));
	}

	@GetMapping
	public ResponseEntity<List<Exercise>> get() {
		return ResponseEntity.ok(exerciseService.get());
	}
}