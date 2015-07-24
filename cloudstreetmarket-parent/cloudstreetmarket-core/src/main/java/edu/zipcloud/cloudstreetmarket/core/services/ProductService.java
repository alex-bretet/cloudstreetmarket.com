package edu.zipcloud.cloudstreetmarket.core.services;

import edu.zipcloud.cloudstreetmarket.core.entities.Product;

public interface ProductService<T extends Product> {
	T get(String ticker);
}
