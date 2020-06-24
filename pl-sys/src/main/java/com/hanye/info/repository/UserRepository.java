package com.hanye.info.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hanye.info.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

}
