package edu.zipcloud.cloudstreetmarket.core.daos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.zipcloud.cloudstreetmarket.core.entities.Action;
import edu.zipcloud.cloudstreetmarket.core.entities.LikeAction;

@Repository
public interface LikeActionRepository extends JpaRepository<LikeAction, Long>{
	Page<LikeAction> findByTargetAction(Pageable pageable, Action action);
}
