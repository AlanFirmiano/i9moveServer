package ufc.br.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ufc.br.model.Object;
import ufc.br.repository.ObjectRepository;

import java.util.List;

@Service
public class ObjectService {

    @Autowired
    ObjectRepository objectRepository;

    public String save(Object object){
        objectRepository.save(object);
        return object.getName()+" adicionado!";

    }

    public String delete(int id){
        objectRepository.deleteById(id);
        return "removido";
    }

    public String update(Object object){
        objectRepository.save(object);
        return "atualizado";
    }

    public Object get(int id){
        return this.objectRepository.findById(id);
    }
    public List<Object> get(){
        return this.objectRepository.findAll();
    }
}