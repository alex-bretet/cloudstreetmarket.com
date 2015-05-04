package edu.zipcloud.cloudstreetmarket.core.daos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.zipcloud.cloudstreetmarket.core.entities.Action;

@Repository
public interface ActionRepository  extends JpaRepository<Action, Integer>{
	Page<Action> findAll(Pageable pageable);
}
