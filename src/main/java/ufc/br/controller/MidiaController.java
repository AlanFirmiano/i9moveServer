package ufc.br.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufc.br.model.Midia;
import ufc.br.service.MidiaService;

import java.util.List;

@RestController
@RequestMapping("/midias")
@CrossOrigin(origins = "*")
public class MidiaController {

    @Autowired
    private MidiaService midiaService;

    @PostMapping
    public ResponseEntity<String> save(@RequestBody Midia midia){
        return ResponseEntity.ok(midiaService.save(midia));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id){
        return ResponseEntity.ok(midiaService.delete(id));
    }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody Midia midia){
        return ResponseEntity.ok(midiaService.update(midia));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Midia> get(@PathVariable Integer id){
        return ResponseEntity.ok(midiaService.get(id));
    }

    @GetMapping
    public ResponseEntity<List<Midia>> get(){
        return ResponseEntity.ok(midiaService.get());
    }
}