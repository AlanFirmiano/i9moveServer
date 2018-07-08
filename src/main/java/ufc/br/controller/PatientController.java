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

import ufc.br.model.Patient;
import ufc.br.service.PatientService;

@RestController
@RequestMapping("/patients")
@CrossOrigin(origins = "*")
public class PatientController {

	@Autowired
	private PatientService patientService;

	@PostMapping
	public ResponseEntity<String> save(@RequestBody Patient patient){
		return ResponseEntity.ok(patientService.save(patient));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Integer id){ return ResponseEntity.ok(patientService.delete(id)); }

	@PutMapping
	public ResponseEntity<String> update(@RequestBody Patient patient){ return ResponseEntity.ok(patientService.update(patient)); }

	@GetMapping("/{id}")
	public ResponseEntity<Patient> get(@PathVariable Integer id){ return ResponseEntity.ok(patientService.get(id)); }

	@GetMapping
	public ResponseEntity<List<Patient>> get(){
		return ResponseEntity.ok(patientService.get());
	}
}
