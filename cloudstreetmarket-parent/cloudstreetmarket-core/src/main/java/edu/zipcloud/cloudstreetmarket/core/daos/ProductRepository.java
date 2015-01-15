package edu.zipcloud.cloudstreetmarket.core.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.zipcloud.cloudstreetmarket.core.entities.Product;

public interface ProductRepository extends JpaRepository<Product, String>{
	

}
