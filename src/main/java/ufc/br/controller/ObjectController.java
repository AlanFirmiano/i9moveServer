package ufc.br.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufc.br.model.Object;
import ufc.br.service.ObjectService;

import java.util.List;

@RestController
@RequestMapping("/objects")
@CrossOrigin(origins = "*")
public class ObjectController {

    @Autowired
    private ObjectService objectService;

    @PostMapping
    public ResponseEntity<String> save(@RequestBody Object object){
        return ResponseEntity.ok(objectService.save(object));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id){
        return ResponseEntity.ok(objectService.delete(id));
    }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody Object object){ return ResponseEntity.ok(objectService.update(object)); }

    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable Integer id){
        return ResponseEntity.ok(objectService.get(id));
    }

    @GetMapping
    public ResponseEntity<List<Object>> get(){
        return ResponseEntity.ok(objectService.get());
    }
}
