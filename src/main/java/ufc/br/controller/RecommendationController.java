package ufc.br.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufc.br.model.Recommendation;
import ufc.br.service.RecommendationService;

import java.util.List;

@RestController
@RequestMapping("/recommendation")
@CrossOrigin(origins = "*")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    @PostMapping
    public ResponseEntity<String> save(@RequestBody Recommendation recommendation){
        return ResponseEntity.ok(recommendationService.save(recommendation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id){
        return ResponseEntity.ok(recommendationService.delete(id));
    }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody Recommendation recommendation){
        return ResponseEntity.ok(recommendationService.update(recommendation));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recommendation> get(@PathVariable Integer id){
        return ResponseEntity.ok(recommendationService.get(id));
    }

    @GetMapping
    public ResponseEntity<List<Recommendation>> get(){
        return ResponseEntity.ok(recommendationService.get());
    }
}