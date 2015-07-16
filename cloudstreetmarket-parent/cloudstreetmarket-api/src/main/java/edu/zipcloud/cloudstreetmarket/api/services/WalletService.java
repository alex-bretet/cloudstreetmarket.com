package edu.zipcloud.cloudstreetmarket.api.services;

import java.util.List;

import edu.zipcloud.cloudstreetmarket.core.dtos.WalletItemDTO;

public interface WalletService {

	List<WalletItemDTO> findBy(String userName);

}
