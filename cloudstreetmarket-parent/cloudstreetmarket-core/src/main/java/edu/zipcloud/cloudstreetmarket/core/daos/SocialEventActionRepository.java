package edu.zipcloud.cloudstreetmarket.core.daos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import edu.zipcloud.cloudstreetmarket.core.entities.Action;
import edu.zipcloud.cloudstreetmarket.core.entities.LikeAction;
import edu.zipcloud.cloudstreetmarket.core.entities.SocialEventAction;

public interface SocialEventActionRepository extends JpaRepository<SocialEventAction, Long>{
}
