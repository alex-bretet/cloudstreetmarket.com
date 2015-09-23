package edu.zipcloud.cloudstreetmarket.shared.services;

import java.util.List;

import edu.zipcloud.cloudstreetmarket.core.dtos.WalletItemDTO;

public interface WalletServiceOffline {

	List<WalletItemDTO> findBy(String forUser, String userName);

}
