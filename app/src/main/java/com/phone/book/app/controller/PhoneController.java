package com.phone.book.app.controller;

import com.phone.book.app.dto.Mensaje;
import com.phone.book.app.dto.PhoneDto;
import com.phone.book.app.entity.Phone;
import com.phone.book.app.service.PhoneService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/phone")
@CrossOrigin(origins = "http://localhost:4200")
public class PhoneController {

    @Autowired
    PhoneService phoneService;

    @GetMapping("/lista")
    public ResponseEntity<List<Phone>> List(){
        List<Phone> list = phoneService.list();
        return  new ResponseEntity<List<Phone>>(list, HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public  ResponseEntity<Phone> getById(@PathVariable("id") int id){
        if(!phoneService.existsById(id)){
            return  new ResponseEntity(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
        }else {
            Phone phone = phoneService.getOne(id).get();
            return new ResponseEntity<>(phone, HttpStatus.OK);
        }
    }

    @GetMapping("/detailname/{nombre}")
    public  ResponseEntity<Phone> getByNombre(@PathVariable("nombre") String nombre){
        if(!phoneService.existsByNombre(nombre)){
            return  new ResponseEntity(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
        }else {
            Phone phone = phoneService.getByNombre(nombre).get();
            return new ResponseEntity<>(phone, HttpStatus.OK);
        }
    }

    @PostMapping("create")
    public ResponseEntity<?> create(@RequestBody PhoneDto phoneDto){
        if (StringUtils.isBlank(phoneDto.getNombre())){
            return  new ResponseEntity(new Mensaje("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        }
        //| phoneDto.getNumero() > 10
        if (phoneDto.getNumero()<0 ){
            return new ResponseEntity(new Mensaje("el numero no corresponde con un telefono"), HttpStatus.BAD_REQUEST);
        }
        if (phoneService.existsByNombre(phoneDto.getNombre())){
            return new ResponseEntity(new Mensaje("El nombe ya existe"), HttpStatus.BAD_REQUEST);
        }
        Phone phone = new Phone(phoneDto.getNombre(), phoneDto.getNumero());
        phoneService.save(phone);
        return new ResponseEntity<>(new Mensaje("Phone creado"), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody PhoneDto phoneDto){
        if(!phoneService.existsById(id)){
            return  new ResponseEntity(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
        }
        if (phoneService.existsByNombre(phoneDto.getNombre()) && phoneService.getByNombre(phoneDto.getNombre()).get().getId() != id){
            return new ResponseEntity(new Mensaje("El nombe ya existe"), HttpStatus.BAD_REQUEST);
        }
        if (StringUtils.isBlank(phoneDto.getNombre())){
            return  new ResponseEntity(new Mensaje("El nombe es obligatorio"), HttpStatus.BAD_REQUEST);
        }
        if (phoneDto.getNumero()<0){
            return new ResponseEntity(new Mensaje("el numero no corresponde con un telefono"), HttpStatus.BAD_REQUEST);
        }

        Phone phone = phoneService.getOne(id).get();
        phone.setNombre(phoneDto.getNombre());
        phone.setNumero(phoneDto.getNumero());
        phoneService.save(phone);
        return new ResponseEntity<>(new Mensaje("Phone creado"), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleted(@PathVariable("id") int id){
        if(!phoneService.existsById(id)){
            return  new ResponseEntity(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
        }
        phoneService.deletec(id);
        return  new ResponseEntity<>(new Mensaje("Phone Eliminado"), HttpStatus.OK);
    }

}
