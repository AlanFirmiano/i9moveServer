package ufc.br.controller;

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
import org.springframework.web.bind.annotation.RestController;

import ufc.br.model.Report;
import ufc.br.service.ReportService;

@RestController
@RequestMapping("/report")
@CrossOrigin(origins = "*")
public class ReportController {

	@Autowired
	private ReportService reportService;

	@PostMapping
	public ResponseEntity<String> save(@RequestBody Report report){
		return ResponseEntity.ok(reportService.save(report));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Integer id){
		return ResponseEntity.ok(reportService.delete(id));
	}

	@PutMapping
	public ResponseEntity<String> update(@RequestBody Report report){
		return ResponseEntity.ok(reportService.update(report));
	}

	@GetMapping("byPatient/{id}")
	public ResponseEntity<List<Report>> get(@PathVariable Integer id){
		return ResponseEntity.ok(reportService.getByPermition(id));
	}

	@GetMapping("byExercise/{id}")
	public ResponseEntity<Double> getByExercise(@PathVariable Integer id){
		return ResponseEntity.ok(reportService.getByExercise(id));
	}

	@GetMapping
	public ResponseEntity<List<Report>> get(){
		return ResponseEntity.ok(reportService.get());
	}
}