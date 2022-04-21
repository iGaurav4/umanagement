package com.lageraho.springsecurity.repository;

import com.lageraho.springsecurity.model.Permission;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends MongoRepository<Permission, Long> {

    List<Permission> findAll ();
}
