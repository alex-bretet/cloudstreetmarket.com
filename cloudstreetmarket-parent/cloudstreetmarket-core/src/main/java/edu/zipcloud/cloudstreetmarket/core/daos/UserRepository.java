package edu.zipcloud.cloudstreetmarket.core.daos;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.zipcloud.cloudstreetmarket.core.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
	Set<User> findByEmail(String email);
}
