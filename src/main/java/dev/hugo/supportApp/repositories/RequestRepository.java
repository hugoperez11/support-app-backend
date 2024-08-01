package dev.hugo.supportApp.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import dev.hugo.supportApp.models.Request;

public interface RequestRepository extends JpaRepository<Request, Long> {

    
} 
    
//hereda varios métodos útiles para realizar operaciones sobre la entidad Request.Como métodos CRUD.
