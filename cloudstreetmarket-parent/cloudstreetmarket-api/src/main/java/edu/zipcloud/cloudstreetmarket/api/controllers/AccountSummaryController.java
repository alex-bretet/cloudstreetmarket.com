/***
 *  Cloudstreetmarket.com is a Spring MVC showcase application developed 
 *  with the book Spring MVC Cookbook [PACKT] (2015). 
 * 	Copyright (C) 2015  Alex Bretet
 *  
 *  This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 **/
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