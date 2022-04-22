package com.phone.book.app.repository;

import com.phone.book.app.entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Integer> {

    Optional<Phone> findByNombre(String nombre);
    boolean existsByNombre(String nombre);
}
