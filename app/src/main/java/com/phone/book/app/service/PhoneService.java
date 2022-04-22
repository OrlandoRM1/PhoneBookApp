package com.phone.book.app.service;

import com.phone.book.app.entity.Phone;
import com.phone.book.app.repository.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PhoneService {

    @Autowired
    PhoneRepository phoneRepository;

    public List<Phone> list(){
        return  phoneRepository.findAll();
    }

    public Optional<Phone> getOne(int id){
        return  phoneRepository.findById(id);
    }

    public Optional<Phone> getByNombre(String nombre) {
        return phoneRepository.findByNombre(nombre);
    }

    public void save(Phone phone){
        phoneRepository.save(phone);
    }

    public void deletec(int id){
        phoneRepository.deleteById(id);
    }

    public  boolean existsById(int id){
        return  phoneRepository.existsById(id);
    }

    public  boolean existsByNombre(String nombre){
        return  phoneRepository.existsByNombre(nombre);
    }
}
