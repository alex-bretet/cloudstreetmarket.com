package edu.zipcloud.cloudstreetmarket.core.daos;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import edu.zipcloud.cloudstreetmarket.core.entities.Action;
import edu.zipcloud.cloudstreetmarket.core.entities.SocialEventAction;

@Transactional
public interface ActionRepository  extends JpaRepository<Action, Long>{
	Page<Action> findAll(Pageable pageable);
	List<Action> findByDateBetween(Date start, Date end);
}
