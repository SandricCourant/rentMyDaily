package com.example.demo.repositories;

import com.example.demo.domain.Owner;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OwnerRepository extends CrudRepository<Owner, Integer> {
    Optional<Owner> findByLogin(String username);
}
