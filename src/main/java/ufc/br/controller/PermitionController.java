package ufc.br.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ufc.br.model.Patient;
import ufc.br.model.Permition;
import ufc.br.service.PatientService;
import ufc.br.service.PermitionService;

import java.util.List;

@RestController
@RequestMapping("/permitions")
@CrossOrigin(origins = "*")
public class PermitionController {

    @Autowired
    private PermitionService service;

    @Autowired
    private PatientService pservice;
    
    @PostMapping
    public ResponseEntity<String> save(@RequestBody Permition permition){
        return service.save(permition);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id){
        return service.delete(id);
    }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody Permition permition){
        return service.update(permition);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Permition> get(@PathVariable Integer id){
        return service.get(id);
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<List<Permition>> getAll(@PathVariable Integer id){
        Patient aux = pservice.get(id).getBody();
    	return service.get(aux);
    }
    
    @GetMapping("/unlocked/{id}")
    public ResponseEntity<List<Permition>> getUnlocked(@PathVariable Integer id){
        Patient aux = pservice.get(id).getBody();
    	return service.getUnlocked(true, aux);
    }

    @GetMapping("/locked/{id}")
    public ResponseEntity<List<Permition>> getLocked(@PathVariable Integer id){
        Patient aux = pservice.get(id).getBody();
    	return service.getUnlocked(false, aux);
    }
    @GetMapping
    public ResponseEntity<List<Permition>> get(){
        return service.get();
    }
}
