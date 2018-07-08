package ufc.br.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufc.br.model.Serie;
import ufc.br.service.SerieService;

import java.util.List;

@RestController
@RequestMapping("/series")
@CrossOrigin(origins = "*")
public class SerieController {

    @Autowired
    private SerieService serieService;

    @PostMapping
    public ResponseEntity<String> save(@RequestBody Serie serie){
        return ResponseEntity.ok(serieService.save(serie));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id){return ResponseEntity.ok(serieService.delete(id)); }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody Serie serie){
        return ResponseEntity.ok(serieService.update(serie));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Serie> get(@PathVariable Integer id){
        return ResponseEntity.ok(serieService.get(id));
    }

    @GetMapping
    public ResponseEntity<List<Serie>> get(){
        return ResponseEntity.ok(serieService.get());
    }
}
