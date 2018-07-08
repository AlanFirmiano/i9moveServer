package ufc.br.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufc.br.model.Level;
import ufc.br.service.LevelService;

import java.util.List;

@RestController
@RequestMapping("/levels")
@CrossOrigin(origins = "*")
public class LevelController {

    @Autowired
    private LevelService levelService;

    @PostMapping
    public ResponseEntity<String> save(@RequestBody Level level){ return ResponseEntity.ok(levelService.save(level)); }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id){
        return ResponseEntity.ok(levelService.delete(id));
    }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody Level level){
        return ResponseEntity.ok(levelService.update(level));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Level> get(@PathVariable Integer id){
        return ResponseEntity.ok(levelService.get(id));
    }

    @GetMapping
    public ResponseEntity<List<Level>> get(){
        return ResponseEntity.ok(levelService.get());
    }

}