package com.example.demo.repositories;

import com.example.demo.domain.Owner;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends CrudRepository<Owner, Integer> {

    Owner findByLoginOrEmailOrPhoneNumber(String login, String email, String phoneNumber);
    Owner findByLogin(String login);

}
