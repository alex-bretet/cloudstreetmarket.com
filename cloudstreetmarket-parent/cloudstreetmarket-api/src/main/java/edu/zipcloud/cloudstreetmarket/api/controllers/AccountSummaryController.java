package edu.zipcloud.cloudstreetmarket.api.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

import edu.zipcloud.cloudstreetmarket.api.services.WalletServiceOnline;
import edu.zipcloud.cloudstreetmarket.core.dtos.WalletItemDTO;

@Api(value = "Account summary", description = "Account summary") // Swagger annotation
@RestController
@RequestMapping(value="/accounts", produces={"application/xml", "application/json"})
public class AccountSummaryController extends CloudstreetApiWCI{

	@Autowired
	private WalletServiceOnline walletService;
	
	@RequestMapping(value="/wallets/{userName}", method=GET)
	@ApiOperation(value = "Gets all wallet-items per user", notes = "Return a page of user-activities")
	public List<WalletItemDTO> getWallet(
			@PathVariable String userName,
			@ApiIgnore @PageableDefault(size=10, page=0, direction=Direction.DESC) Pageable pageable){
		return walletService.findBy(userName);
	}
}