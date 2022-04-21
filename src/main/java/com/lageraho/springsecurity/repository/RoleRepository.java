package com.lageraho.springsecurity.repository;

import com.lageraho.springsecurity.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends MongoRepository<Role, Long> {


    Role findByName (String name);
}
